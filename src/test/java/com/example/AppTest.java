import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class AppTest {
    public static void main(String[] args) {
        // Ensure that a file path is provided as a command-line argument
        if (args.length != 1) {
            System.out.println("Usage: java AppText <path_to_pdf_file>");
            return;
        }

        // Path to the PDF file (passed as command-line argument)
        String pdfFilePath = args[0];

        // Create a Tika instance
        Tika tika = new Tika();

        try {
            // Extract text from PDF file
            String extractedText = tika.parseToString(new File(pdfFilePath));

            // Print the extracted text to console
            System.out.println("Extracted Text: ");
            System.out.println(extractedText);
        } catch (IOException | TikaException e) {
            System.err.println("Error reading the PDF file: " + e.getMessage());
        }
    }
}
