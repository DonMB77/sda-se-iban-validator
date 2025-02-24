package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.bootstrap.BootstrapData;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BootstrapData.class)
class IbanRepositoryTest {

    @Autowired
    IbanRepository ibanRepository;

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