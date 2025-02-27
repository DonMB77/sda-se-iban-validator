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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class IbanController {

    /**
     * Constants storing the API Endpoints URI's. These are declared here once,
     * then used throughout the application. DRY-principle.
     */
    public static final String IBAN_DOCUMENT_PDF_IMPORT_PATH_LOCAL_FILE = "/api/v1/pdfImportLocal";
    public static final String IBAN_DOCUMENT_PDF_IMPORT_PATH_ONLINE_FILE = "/api/v1/pdfImportOnline";
    public static final String BLACKLISTED_IBAN_PATH = "/api/v1/blacklistedIban";
    public static final String BLACKLISTED_IBAN_PATH_ID = BLACKLISTED_IBAN_PATH + "/{id}";
    public static final String CURRENTLY_STORED_IBANS_PATH = "/api/v1/storedIbans";

    private final IbanService ibanService;
    private final UrlService urlService;

    /**
     * Method used to receive and store new blacklisted IBAN's.
     * @param blacklistedIban Standard IBAN format (DE15 3006 0601 0505 7807 82)
     * @return We return a ResponseEntity with the correct status.
     */
    @PostMapping(BLACKLISTED_IBAN_PATH)
    public ResponseEntity handlePostOfBlacklistedIbans(@RequestBody BlacklistedIban blacklistedIban) {
        ibanService.saveNewBlacklistedIban(blacklistedIban);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Method used to delete stored blacklisted IBAN's by id.
     * @param id Id of the to be deleted blacklisted IBAN
     * @return We return a ResponseEntity with the correct status.
     */
    @DeleteMapping(BLACKLISTED_IBAN_PATH_ID)
    public ResponseEntity handleDeleteOfBlacklistedIbans(@PathVariable("id") UUID id) {
        ibanService.deleteBlacklistedIbanById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Given a URL this method processes the given PDF file and stores all found IBAN's.
     * It then is checked against all stored blacklisted IBAN's and returns a fitting response,
     * depending on whether blacklisted IBAN's are found or not.
     * @param url Standart URL
     * @return We return a ResponseEntity with the correct status.
     * @throws IOException If no file is found.
     */
    @PostMapping(IBAN_DOCUMENT_PDF_IMPORT_PATH_ONLINE_FILE)
    public ResponseEntity handlePostOnlineFile(@Validated @RequestBody Url url) throws IOException {

        PdfExtractorOnlineFile pdfExtractorOnlineFile = new PdfExtractorOnlineFile(url.getUrl());
        List<String> extractedIbans = pdfExtractorOnlineFile.getIbansFromPDF();
        // here we go through every extracted IBAN and save them within our IbanRepository.
        extractedIbans.forEach(iban -> {
            Iban newIban = Iban.builder()
                    .IBAN(iban)
                    .build();
            ibanService.saveNewIban(newIban);
        });

        List<Iban> currentSavedIbans = ibanService.listToBeCheckedIbans();

        // depending on if the check comes back true, we respond with an appropriate response. Otherwise, we respond with "ok".
        if (ibanService.checkForBlacklistedIbans(currentSavedIbans)) {
            return ResponseEntity.badRequest()
                    .body("The given Invoice('s) contains blacklisted Iban's and cannot be processed!");
        }

        return ResponseEntity.ok()
                .body("The given Invoice('s) does not contain any blacklisted Iban's and can be processed.");
    }

    /**
     * This method is used to import local files. The request must be in a Unix-like format.
     * An example would be C:/Users/user/documents/exampleInvoices.pdf. Backslashes are not permitted.
     * The given PDF is then processed and any found IBAN's are extracted. It then gets checked for blacklisted IBAN's.
     * @param localFilePath Unix-like file path (C:/Users/user/documents/exampleInvoices.pdf)
     * @return We return a ResponseEntity with the correct status.
     * @throws IOException If no file is found.
     */
    @PostMapping(IBAN_DOCUMENT_PDF_IMPORT_PATH_LOCAL_FILE)
    public ResponseEntity handlePostLocalFile(@Validated @RequestBody Url localFilePath) throws IOException {

        // we save the url here to be used later on
        Url savedUrl = urlService.saveNewUrl(localFilePath);

        PdfExtractorLocalFile pdfExtractorLocalFile = new PdfExtractorLocalFile(savedUrl.getUrl());
        List<String> extractedIbans = pdfExtractorLocalFile.getIbansFromPDF();

        // here we go through every extracted IBAN and save them within our IbanRepository.
        extractedIbans.forEach(iban -> {
            Iban newIban = Iban.builder()
                    .IBAN(iban)
                    .build();
            ibanService.saveNewIban(newIban);
        });

        List<Iban> currentSavedIbans = ibanService.listToBeCheckedIbans();

        // depending on if the check comes back true, we respond with an appropriate response. Otherwise, we respond with "ok".
        if (ibanService.checkForBlacklistedIbans(currentSavedIbans)) {
            return ResponseEntity.badRequest()
                    .body("The given Invoice('s) contains blacklisted Iban's and cannot be processed!");
        }

        return ResponseEntity.ok()
                .body("The given Invoice('s) does not contain any blacklisted Iban's and can be processed.");
    }

    /**
     * Produced a list of all blacklisted IBAN's currently stored.
     * @return List of blacklisted IBAN's.
     */
    @GetMapping(BLACKLISTED_IBAN_PATH)
    public List<BlacklistedIban> listBlacklistedIbans() {
        return ibanService.listBlacklistedIbans();
    }

    /**
     * Returns a List of all currently stored checked IBAN's currently stored.
     * @return List of checked IBAN's since the application has been run.
     */
    @GetMapping(CURRENTLY_STORED_IBANS_PATH)
    public List<Iban> listCurrentlyStoredIbans() {
        return ibanService.listToBeCheckedIbans();
    }

    /**
     * Simple ExceptionHandler in case no file has been found in the provided URL's.
     * @return
     */
    @ExceptionHandler({FileNotFoundException.class})
    public String fileNotFoundError() {
        return "No file has been found. Please provide a link containing a PDF file.";
    }
}
