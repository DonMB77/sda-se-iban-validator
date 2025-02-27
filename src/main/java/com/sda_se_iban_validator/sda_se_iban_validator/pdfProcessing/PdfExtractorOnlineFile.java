package com.sda_se_iban_validator.sda_se_iban_validator.pdfProcessing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.net.*;

/**
 * Class used for processing and extracting IBAN's of the given PDF files via local file path.
 */
public class PdfExtractorOnlineFile {
    private String text;
    private File file;

    /**
     * @param fileUrl Simple String using Unix-like local file paths.
     * @throws IOException In case no file is found.
     */
    public PdfExtractorOnlineFile(String fileUrl) throws IOException {

        // here a BufferedInputStream is used to receive a PDF using a URL
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             // the file output stream write to a file called Extracted_PDF.pdf, which is then further used to extract the IBAN's
             FileOutputStream fileOutputStream = new FileOutputStream("Extracted_PDF.pdf")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.getCause();
        }

        file = new File("Extracted_PDF.pdf");
        // PDDocument is the in-memory representation of the PDF document loaded
        PDDocument document = PDDocument.load(file);

        // this will receive a PDF document and strip out the text
        PDFTextStripper pdfStripper = new PDFTextStripper();
        this.text = pdfStripper.getText(document);

        // closing here is mandatory
        document.close();
    }

    /**
     * Simple method returning the entire text from inputted PDF file.
     */
    public String getTextFromPDF() {
        return text;
    }

    /**
     * Goes through the PDF file line by line and scans for IBAN's. They then get stored within a List.
     * @return Returns list with all found IBAN's.
     * @throws IOException In case no file is found.
     */
    public List<String> getIbansFromPDF() throws IOException {
        List<String> outListWithIbans = new ArrayList<>();
        // identifier or the IBAN:
        String matchIbanString = "IBAN";
        String tempStringHolder;
        String tempStringLine;

        Scanner scanner = new Scanner(text);

        /*
        In this loop we go through every line and scan every line for the aforementioned identifier.
        Then the IBAN itself gets extracted and all the blank spaces are stripped.
        We then save the extracted String in a List, which then gets returned after all lines are scanned.
         */
        while (scanner.hasNextLine())
        {
            tempStringLine = scanner.nextLine();
            if(tempStringLine.contains(matchIbanString)) {
                int indexStart = tempStringLine.indexOf("IBAN") + 5;
                int indexEnd = tempStringLine.indexOf("IBAN")+34;
                tempStringHolder = tempStringLine.substring(indexStart, indexEnd);
                tempStringHolder = tempStringHolder.strip();
                outListWithIbans.add(tempStringHolder);
            }
        }
        scanner.close();
        return outListWithIbans;
    }
}
