package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneratePositionedHTML {
    public static void generateHTML(List<ExtractTextWithCoordinates.TextElement> elements, float pageWidth, float pageHeight) {
        StringBuilder html = new StringBuilder();

        // Append the HTML header and styles
        html.append("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>PDF to HTML</title>
                <style>
                    body { position: relative; margin: 0; padding: 0; }
                    .page { position: relative; background: #fff; }
                </style>
            </head>
            <body>
        """);

        // Append the page container with dynamic width and height
        html.append(String.format("""
                <div class="page" style="width: %.2fpx; height: %.2fpx;">
            """, pageWidth, pageHeight));

        // Add text elements dynamically
        for (ExtractTextWithCoordinates.TextElement element : elements) {
            float normalizedY = pageHeight - element.y; // Flip Y coordinate
            html.append(String.format(
                "<div style='position: absolute; left: %.2fpx; top: %.2fpx; font-size: %.2fpx;'>%s</div>",
                element.x, normalizedY, element.fontSize, escapeHtml(element.text)
            ));
        }

        // Close the page container and body
        html.append("""
                </div>
            </body>
            </html>
        """);

        // Write the HTML to a file
        try (FileWriter writer = new FileWriter("output.html")) {
            writer.write(html.toString());
            System.out.println("HTML file generated: output.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to escape HTML special characters
    private static String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;");
    }
}
