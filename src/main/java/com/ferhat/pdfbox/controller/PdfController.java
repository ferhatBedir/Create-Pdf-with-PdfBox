package com.ferhat.pdfbox.controller;

import com.ferhat.pdfbox.service.MultipleLine;
import com.ferhat.pdfbox.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private PdfService pdfService;
    private MultipleLine multipleLine;

    @Autowired
    public PdfController(PdfService pdfService, MultipleLine multipleLine) {
        this.pdfService = pdfService;
        this.multipleLine = multipleLine;
    }

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> createPdf() throws IOException {
        ByteArrayInputStream bais = pdfService.create();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; pdfBox.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
    }

    @GetMapping(value = "/multipleLine", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> createMultipleLinePdf() throws IOException {
        ByteArrayInputStream bais = multipleLine.multipleLine();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; pdfBox.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
    }
}