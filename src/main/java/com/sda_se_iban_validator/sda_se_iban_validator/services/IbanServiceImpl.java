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

@Service
@RequiredArgsConstructor
public class IbanServiceImpl implements IbanService {

    private final IbanRepository ibanRepository;
    private final BlacklistedIbanRepository blacklistedIbanRepository;

    @Override
    public Iban saveNewIban(Iban iban) {
        return ibanRepository.save(iban);
    }

    @Override
    public List<BlacklistedIban> listBlacklistedIbans() {
        return blacklistedIbanRepository.findAll();
    }

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

    @Override
    public List<Iban> listToBeCheckedIbans() {
        return ibanRepository.findAll();
    }

    @Override
    public BlacklistedIban saveNewBlacklistedIban(BlacklistedIban blacklistedIban) {
        return blacklistedIbanRepository.save(blacklistedIban);
    }

    @Override
    public Boolean deleteBlacklistedIbanById(UUID id) {
        if (blacklistedIbanRepository.existsById(id)) {
            blacklistedIbanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
