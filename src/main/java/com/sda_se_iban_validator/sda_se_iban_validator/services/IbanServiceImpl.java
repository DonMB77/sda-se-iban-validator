package com.sda_se_iban_validator.sda_se_iban_validator.services;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.BlacklistedIbanRepository;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.IbanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for saving, posting, processing and listing IBAN's
 */
@Service
@RequiredArgsConstructor
public class IbanServiceImpl implements IbanService {

    private final IbanRepository ibanRepository;
    private final BlacklistedIbanRepository blacklistedIbanRepository;

    /**
     * Method to save a new IBAN in the repository.
     * This is used for IBAN's to be checked, not blacklisted ones.
     * @param iban
     * @return Saved instance of Iban
     */
    @Override
    public Iban saveNewIban(Iban iban) {
        return ibanRepository.save(iban);
    }

    /**
     * Method used to list all currently saved blacklisted IBAN's.
     * @return List of all currently saved blacklisted IBAN's currently residing within the repository.
     */
    @Override
    public List<BlacklistedIban> listBlacklistedIbans() {
        return blacklistedIbanRepository.findAll();
    }

    /**
     * Method to check all blacklisted IBAN's against IBAN's imported into the repository.
     * @param ibansToBeChecked List of imported IBAN's to be checked.
     * @return Boolean depending on whether blacklisted IBAN's where found.
     */
    @Override
    public Boolean checkForBlacklistedIbans(List<Iban> ibansToBeChecked) {
        List<BlacklistedIban> blacklistedIbans = blacklistedIbanRepository.findAll();
        List<String> blacklistedIbansString = blacklistedIbans.stream().map(BlacklistedIban::getIBAN).toList();
        List<String> ibansToBeCheckedString = ibansToBeChecked.stream().map(Iban::getIBAN).toList();

        for (String iban : ibansToBeCheckedString) {
            for (String blacklistedIban : blacklistedIbansString) {
                if (iban.equals(blacklistedIban)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method used to list all currently saved to be checked IBAN's.
     * @return List of all currently saved to be checked IBAN's currently residing within the repository.
     */
    @Override
    public List<Iban> listToBeCheckedIbans() {
        return ibanRepository.findAll();
    }

    /**
     * Method to save a new blacklisted IBAN in the repository.
     * This is used for adding new blacklisted IBAN's, not to be checked ones.
     * @param blacklistedIban
     * @return Saved instance of Iban
     */
    @Override
    public BlacklistedIban saveNewBlacklistedIban(BlacklistedIban blacklistedIban) {
        return blacklistedIbanRepository.save(blacklistedIban);
    }

    /**
     * Method for deleting any currently saved blacklisted IBAN within the repository by id.
     * @param id ID of the to be deleted IBAN.
     * @return Boolean on whether the given ID has found a match and deleted given blacklisted IBAN.
     */
    @Override
    public Boolean deleteBlacklistedIbanById(UUID id) {
        if (blacklistedIbanRepository.existsById(id)) {
            blacklistedIbanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
