package com.sda_se_iban_validator.sda_se_iban_validator.bootstrap;


import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.BlacklistedIbanRepository;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.IbanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class which implements CommandLineRunner runs as soon as the app is launched.
 * It loads some demo data of blacklisted Iban's used for checking against scanned Iban's inputted.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BlacklistedIbanRepository blacklistedIbanRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBlackListedIbanData();
    }

    /**
     * Here the aforementioned demo data gets loaded into the repository.
     */
    private void loadBlackListedIbanData() {
        if (blacklistedIbanRepository.count() == 0) {
            BlacklistedIban newIban1 = BlacklistedIban.builder()
                    .IBAN("DE15 3006 0601 0505 7807 82")
                    .build();

            BlacklistedIban newIban2 = BlacklistedIban.builder()
                    .IBAN("DE15 3006 0601 0505 7807 81")
                    .build();

            blacklistedIbanRepository.save(newIban1);
            blacklistedIbanRepository.save(newIban2);
        }
        System.out.println(blacklistedIbanRepository.count());
    }
}