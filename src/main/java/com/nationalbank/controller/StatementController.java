package com.nationalbank.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nationalbank.model.Transaction;
import com.nationalbank.service.PdfService;
import com.nationalbank.service.StatementService;

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf/{accountNumber}")
    public ResponseEntity<byte[]> getAccountStatementPdf(
            @PathVariable String accountNumber,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year) {

        try {
            Optional<LocalDate> start = Optional.ofNullable(startDate);
            Optional<LocalDate> end = Optional.ofNullable(endDate);

            if (month != null && year != null) {
                YearMonth yearMonth = YearMonth.of(year, month);
                start = Optional.of(yearMonth.atDay(1));
                end = Optional.of(yearMonth.atEndOfMonth());
            }

            System.out.println("Start Date: " + start);
            System.out.println("End Date: " + end);

            List<Transaction> statement = statementService.getAccountStatement(accountNumber, start, end);

            byte[] pdfBytes = pdfService.generateStatementPdf(statement);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "statement.pdf");
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/json/{accountNumber}")
    public ResponseEntity<String> getAccountStatementJson(
            @PathVariable String accountNumber,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year) {

        try {
            Optional<LocalDate> start = Optional.ofNullable(startDate);
            Optional<LocalDate> end = Optional.ofNullable(endDate);

            if (month != null && year != null) {
                YearMonth yearMonth = YearMonth.of(year, month);
                start = Optional.of(yearMonth.atDay(1));
                end = Optional.of(yearMonth.atEndOfMonth());
            }

            System.out.println("Start Date: " + start);
            System.out.println("End Date: " + end);

            List<Transaction> statement = statementService.getAccountStatement(accountNumber, start, end);

            String json = pdfService.generateStatementJson(statement);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setCacheControl("no-cache, no-store, must-revalidate");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
