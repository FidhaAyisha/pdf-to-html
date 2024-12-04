package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtractTextWithCoordinates extends PDFTextStripper {
    public static class TextElement {
        String text;
        float x, y, fontSize;

        TextElement(String text, float x, float y, float fontSize) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.fontSize = fontSize;
        }
    }

    private final List<TextElement> textElements = new ArrayList<>();

    public ExtractTextWithCoordinates() throws IOException {}

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) {
        for (TextPosition position : textPositions) {
            textElements.add(new TextElement(
                position.getUnicode(),
                position.getXDirAdj(),
                position.getYDirAdj(),
                position.getFontSizeInPt()
            ));
        }
    }

    public List<TextElement> getTextElements() {
        return textElements;
    }

    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("src/main/resources/sample2.pdf"))) {
            ExtractTextWithCoordinates extractor = new ExtractTextWithCoordinates();
            extractor.setSortByPosition(true);
            extractor.getText(document); // Extract text with positions

            List<TextElement> elements = extractor.getTextElements();
            for (TextElement element : elements) {
                System.out.printf("Text: %s, X: %.2f, Y: %.2f, Font Size: %.2f%n",
                    element.text, element.x, element.y, element.fontSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

