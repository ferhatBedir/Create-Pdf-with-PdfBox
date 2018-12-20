package com.ferhat.pdfbox.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PdfService {


    public ByteArrayInputStream create() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        /*
        tükçe karakter içermiyor bu sebepten external bir dil paketi kullandık.
        PDFont font = PDType1Font.HELVETICA;
         */
        Resource resource = new ClassPathResource("tff/language.TTF");
        File file = resource.getFile();
        PDType0Font font = PDType0Font.load(doc, file);

        contentStream.beginText();

        contentStream.newLineAtOffset(220, 700);
        contentStream.setFont(font, 16);
        contentStream.setLeading(15);
        contentStream.showText("İZİN MUTABAKAT FORMU");

        contentStream.newLineAtOffset(260, 50);
        contentStream.setFont(font, 14);
        contentStream.showText("..  /  ..  /  20 ..");

        contentStream.newLineAtOffset(-400, -130);
        contentStream.setFont(font, 14);
        contentStream.setLeading(15);
        contentStream.showText("19/12/2018" + " Tarihi itibari ile 10 gün olan yillik iznimi onayliyorum.");

        contentStream.newLineAtOffset(0, -100);
        contentStream.setFont(font, 14);
        contentStream.setLeading(15);
        contentStream.showText("İsim - Soyisim");

        contentStream.newLineAtOffset(360, 0);
        contentStream.setFont(font, 14);
        contentStream.setLeading(15);
        contentStream.showText("İmza");


        contentStream.endText();
        System.out.println("Content added");

        contentStream.close();
        doc.save(baos);
        doc.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }
}
