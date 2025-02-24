package com.sda_se_iban_validator.sda_se_iban_validator.services;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.BlacklistedIbanRepository;

import java.util.List;
import java.util.UUID;

public interface IbanService {
    Iban saveNewIban(Iban iban);
    List<BlacklistedIban> listBlacklistedIbans();
    Boolean checkForBlacklistedIbans(List<Iban> ibansToBeChecked);
    List<Iban> listToBeCheckedIbans();
    BlacklistedIban saveNewBlacklistedIban(BlacklistedIban  blacklistedIban);
    Boolean deleteBlacklistedIbanById(UUID id);
    Iban saveNewIbanOnlineFile(Iban iban);
}
