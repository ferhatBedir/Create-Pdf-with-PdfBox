package com.ferhat.pdfbox.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {


    public ByteArrayInputStream create() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 725);

        contentStream.showText("Hello Pdf Box");
        contentStream.newLine();
        contentStream.showText("Hello Pdf Box");
        contentStream.newLine();
        contentStream.showText("Hello Pdf Box");
        contentStream.newLine();
        contentStream.showText("Hello Pdf Box");
        contentStream.newLine();
        contentStream.showText("Hello Pdf Box");
        contentStream.endText();
        System.out.println("Content added");

        contentStream.close();
        doc.save(baos);
        doc.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }
}
