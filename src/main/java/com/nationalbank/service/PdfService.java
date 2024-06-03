package com.nationalbank.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.nationalbank.model.Transaction;

@Service
public class PdfService {

	 public byte[] generateStatementPdf(List<Transaction> transactions) {
	        Document document = new Document();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();

	        try {
	            PdfWriter.getInstance(document, out);
	            document.open();
	            document.add(new Paragraph("Account Statement"));
	            document.add(new Paragraph("---------------------------------------------------------"));

	            for (Transaction transaction : transactions) {
	                document.add(new Paragraph("Date: " + transaction.getTransaction_date()));
	                document.add(new Paragraph("Amount: " + transaction.getAmount()));
	                document.add(new Paragraph("Type: " + transaction.getTransactionType()));
	                document.add(new Paragraph("---------------------------------------------------------"));
	            }

	            document.close();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }

	        return out.toByteArray();
	    }
}
