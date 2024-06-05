package com.nationalbank.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nationalbank.model.Transaction;

@Service
public class PdfService {
	
    @Autowired
    private ObjectMapper objectMapper;

    public String generateStatementJson(List<Transaction> transactions) {
        try {
            return objectMapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating JSON", e);
        }
    }

    public byte[] generateStatementPdf(List<Transaction> transactions) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Account Statement"));
            document.add(new Paragraph("---------------------------------------------------------"));

            PdfPTable table = new PdfPTable(5); // 5 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set column widths
            float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            // Add table header
            addTableHeader(table);

            // Add table rows and calculate remaining balance
            double balance = 0;
            for (Transaction transaction : transactions) {
                balance = updateBalance(balance, transaction);
                addRows(table, transaction, balance);
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph("Date"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Amount"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Type"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Debit/Credit"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Balance"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void addRows(PdfPTable table, Transaction transaction, double balance) {
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph(transaction.getTransaction_date().toString()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(String.valueOf(transaction.getAmount())));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(transaction.getTransactionType().toString()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        String debitCredit = determineDebitCredit(transaction);
        cell = new PdfPCell(new Paragraph(debitCredit));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(String.format("%.2f", Math.abs(balance))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private String determineDebitCredit(Transaction transaction) {
        switch (transaction.getTransactionType()) {
            case DEPOSIT:
                return "Credit";
            case WITHDRAW:
                return "Debit";
            case TRANSFER:
                // Assuming source account is always a debit, and destination account is always a credit
                return transaction.getSourceAccount() != null ? "Debit" : "Credit";
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transaction.getTransactionType());
        }
    }

    private double updateBalance(double currentBalance, Transaction transaction) {
        switch (transaction.getTransactionType()) {
            case DEPOSIT:
            	return currentBalance + transaction.getAmount();
            case TRANSFER: 
                return currentBalance - transaction.getAmount();
            case WITHDRAW:
                return currentBalance - transaction.getAmount();
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transaction.getTransactionType());
        }
    }
    

}
