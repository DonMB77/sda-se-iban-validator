package com.sda_se_iban_validator.sda_se_iban_validator.pdfProcessing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used for processing and extracting IBAN's of the given PDF files via URL.
 */
public class PdfExtractorLocalFile {

    private String text;
    private File file;

    /**
     *
     * @param fileUrl Simple String using Unix-like local file paths.
     * @throws IOException In case no file is found.
     */
    public PdfExtractorLocalFile(String fileUrl) throws IOException {
        file = new File(fileUrl);
        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        this.text = pdfStripper.getText(document);

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
        String matchIbanString = "IBAN";
        String tempStringHolder;
        String tempStringLine;

        Scanner scanner = new Scanner(text);

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
