package com.sda_se_iban_validator.sda_se_iban_validator.controllers;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.pdfProcessing.PdfExtractorLocalFile;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;
import com.sda_se_iban_validator.sda_se_iban_validator.pdfProcessing.PdfExtractorOnlineFile;
import com.sda_se_iban_validator.sda_se_iban_validator.services.IbanService;
import com.sda_se_iban_validator.sda_se_iban_validator.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class IbanController {

    public static final String IBAN_DOCUMENT_PDF_IMPORT_PATH_LOCAL_FILE = "/api/v1/pdfImportLocal";
    public static final String IBAN_DOCUMENT_PDF_IMPORT_PATH_ONLINE_FILE = "/api/v1/pdfImportOnline";
    public static final String BLACKLISTED_IBAN_PATH = "/api/v1/blacklistedIban";
    public static final String BLACKLISTED_IBAN_PATH_ID = BLACKLISTED_IBAN_PATH + "/{id}";

    private final IbanService ibanService;
    private final UrlService urlService;

    @PostMapping(BLACKLISTED_IBAN_PATH)
    public ResponseEntity handlePost(@RequestBody BlacklistedIban blacklistedIban) {
        ibanService.saveNewBlacklistedIban(blacklistedIban);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BLACKLISTED_IBAN_PATH_ID)
    public ResponseEntity handleDelete(@PathVariable("id") UUID id) {
        ibanService.deleteBlacklistedIbanById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(IBAN_DOCUMENT_PDF_IMPORT_PATH_ONLINE_FILE)
    public ResponseEntity handlePostOnlineFile(@RequestBody Url url) throws IOException {

        PdfExtractorOnlineFile pdfExtractorOnlineFile = new PdfExtractorOnlineFile(url.getUrl());
        pdfExtractorOnlineFile.getIbansFromPDF();
        List<String> extractedIbans = pdfExtractorOnlineFile.getIbansFromPDF();
        extractedIbans.forEach(iban -> {
            Iban newIban = Iban.builder()
                    .IBAN(iban)
                    .build();
            ibanService.saveNewIban(newIban);
        });

        List<Iban> currentSavedIbans = ibanService.listToBeCheckedIbans();

        if (ibanService.checkForBlacklistedIbans(currentSavedIbans)) {
            return ResponseEntity.badRequest()
                    .body("The given Invoice('s) contains blacklisted Iban's and cannot be processed!");
        }

        return ResponseEntity.ok()
                .body("The given Invoice('s) does not contain any blacklisted Iban's and can be processed.");
    }

    @PostMapping(IBAN_DOCUMENT_PDF_IMPORT_PATH_LOCAL_FILE)
    public ResponseEntity handlePostLocalFile(@RequestBody Url url) throws IOException {

        Url savedUrl = urlService.saveNewUrl(url);

        PdfExtractorLocalFile pdfExtractorLocalFile = new PdfExtractorLocalFile(savedUrl.getUrl());
        List<String> extractedIbans = pdfExtractorLocalFile.getIbansFromPDF();
        extractedIbans.forEach(iban -> {
            Iban newIban = Iban.builder()
                    .IBAN(iban)
                    .build();
            ibanService.saveNewIban(newIban);
        });

        List<Iban> currentSavedIbans = ibanService.listToBeCheckedIbans();

        if (ibanService.checkForBlacklistedIbans(currentSavedIbans)) {
            return ResponseEntity.badRequest()
                    .body("The given Invoice('s) contains blacklisted Iban's and cannot be processed!");
        }

        return ResponseEntity.ok()
                .body("The given Invoice('s) does not contain any blacklisted Iban's and can be processed.");
    }

    @GetMapping(BLACKLISTED_IBAN_PATH)
    public List<BlacklistedIban> listBlacklistedIbans() {
        return ibanService.listBlacklistedIbans();
    }
}
