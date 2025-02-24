package com.sda_se_iban_validator.sda_se_iban_validator.pdfProcessing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PdfExtractorLocalFile {

    private String text;
    private File file;

    public PdfExtractorLocalFile(String fileUrl) throws IOException {
        file = new File(fileUrl);
        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        this.text = pdfStripper.getText(document);

        document.close();
    }

    public String getTextFromPDF() {
        return text;
    }

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
                int indexEnd = tempStringLine.indexOf("IBAN")+33;
                tempStringHolder = tempStringLine.substring(indexStart, indexEnd);
                System.out.println(tempStringHolder);
                tempStringHolder = tempStringHolder.strip();
                outListWithIbans.add(tempStringHolder);
            }
        }
        scanner.close();
        return outListWithIbans;
    }
}
