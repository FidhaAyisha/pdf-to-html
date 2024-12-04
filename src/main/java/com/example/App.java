package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // Load the PDF document
            String pdfPath = "src/main/resources/sample2.pdf";
            PDDocument document = PDDocument.load(new File(pdfPath));

            // Extract text with positions
            ExtractTextWithCoordinates textExtractor = new ExtractTextWithCoordinates();
            textExtractor.setSortByPosition(true);
            textExtractor.getText(document);
            List<ExtractTextWithCoordinates.TextElement> textElements = textExtractor.getTextElements();

            // Extract page dimensions
            float pageWidth = document.getPage(0).getMediaBox().getWidth();
            float pageHeight = document.getPage(0).getMediaBox().getHeight();

            // Generate positioned HTML
            GeneratePositionedHTML.generateHTML(textElements, pageWidth, pageHeight);

            // Close the document
            document.close();
            System.out.println("HTML generated successfully: output/output.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
