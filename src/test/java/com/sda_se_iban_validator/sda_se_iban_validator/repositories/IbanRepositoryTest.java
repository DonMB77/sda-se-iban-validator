package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.bootstrap.BootstrapData;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure the correct interaction with the repository saving all to be checked Iban's.
 */
@DataJpaTest
class IbanRepositoryTest {

    @Autowired
    IbanRepository ibanRepository;

    /**
     * This test is in charge of ensuring that newly added to be checked IBAN's are correctly saved.
     */
    @Transactional
    @Rollback
    @Test
    void testSaveToBeCheckedIban() {
        Iban iban = ibanRepository.save(Iban.builder()
                        .IBAN("DE99 9999 9999 9999 9999 99")
                .build());

        ibanRepository.flush();

        assertThat(iban).isNotNull();
        assertThat(iban.getIBAN()).isNotNull();
    }
}