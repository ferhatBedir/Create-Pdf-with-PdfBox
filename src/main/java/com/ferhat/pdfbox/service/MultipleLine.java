package com.ferhat.pdfbox.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultipleLine {

    public ByteArrayInputStream multipleLine() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box " +
                "Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box " +
                "Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box " +
                "Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box " +
                "Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box " +
                "Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box Hello World Pdf Box ");
        arrayList.add("Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box " +
                "Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box " +
                "Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box " +
                "Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box Welcome To Pdf Box ");
        PDFont font = PDType1Font.HELVETICA;
        int positionX = 30;
        int positionY = 750;

        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        for (int i = 0; i < arrayList.size(); i++) {
            String text = arrayList.get(i);
            String[] tmpText = splitString(text);
            for (int k = 0; k < tmpText.length; k++) {
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(positionX, positionY);
                contentStream.showText(tmpText[k]);
                contentStream.endText();
                positionY = positionY - 20;
            }
            contentStream.setLineWidth(0.25f);
        }


        contentStream.close();
        document.save("pdfBox.pdf");
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private String[] splitString(String text) {
        /* pdfBox doesnt support linebreaks. Therefore, following steps are requierd to automatically put linebreaks in the pdf
         * 1) split each word in string that has to be linefeded and put them into an array of string, e.g. String [] parts
         * 2) create an array of stringbuffer with (textlength/(number of characters in a line)), e.g. 280/70=5 >> we need 5 linebreaks!
         * 3) put the parts into the stringbuffer[i], until the limit of maximum number of characters in a line is allowed,
         * 4) loop until stringbuffer.length < linebreaks
         *
         */
        int linebreaks = text.length() / 80; //how many linebreaks do I need?
        String[] newText = new String[linebreaks + 1];
        String tmpText = text;
        String[] parts = tmpText.split(" "); //save each word into an array-element

        //split each word in String into a an array of String text.
        StringBuffer[] stringBuffer = new StringBuffer[linebreaks + 1]; //StringBuffer is necessary because of manipulating text
        int i = 0; //initialize counter
        int totalTextLength = 0;
        for (int k = 0; k < linebreaks + 1; k++) {
            stringBuffer[k] = new StringBuffer();
            while (true) {
                if (i >= parts.length) break; //avoid NullPointerException
                totalTextLength = totalTextLength + parts[i].length(); //count each word in String
                if (totalTextLength > 80) break; //put each word in a stringbuffer until string length is >80
                stringBuffer[k].append(parts[i]);
                stringBuffer[k].append(" ");
                i++;
            }
            //reset counter, save linebreaked text into the array, finally convert it to a string
            totalTextLength = 0;
            newText[k] = stringBuffer[k].toString();
        }
        return newText;
    }

}
