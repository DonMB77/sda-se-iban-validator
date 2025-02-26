package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.bootstrap.BootstrapData;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure the correct interaction with the repository saving all blacklisted Iban's.
 */
@DataJpaTest
class BlacklistedIbanRepositoryTest {

    @Autowired
    BlacklistedIbanRepository blacklistedIbanRepository;

    /**
     * This test is in charge of ensuring that newly added blacklisted IBAN's are correctly saved.
     */
    @Transactional
    @Rollback
    @Test
    void testSaveBeer() {
        BlacklistedIban blacklistedIban = blacklistedIbanRepository.save(BlacklistedIban.builder()
                        .IBAN("DE15 3006 0601 0505 7807 84")
                .build());

        blacklistedIbanRepository.flush();

        assertThat(blacklistedIban).isNotNull();
        assertThat(blacklistedIban.getIBAN()).isNotNull();
    }
}