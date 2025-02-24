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

public class PdfExtractorOnlineFile {
    private String text;
    private File file;

    public PdfExtractorOnlineFile(String fileUrl) throws IOException {

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("Extracted_PDF.pdf")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.getCause();
        }

        file = new File("Extracted_PDF.pdf");
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
        System.out.println(outListWithIbans);
        scanner.close();
        return outListWithIbans;
    }
}
