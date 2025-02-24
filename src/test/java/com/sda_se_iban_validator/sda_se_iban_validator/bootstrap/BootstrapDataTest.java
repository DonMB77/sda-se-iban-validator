package com.sda_se_iban_validator.sda_se_iban_validator.bootstrap;

import com.sda_se_iban_validator.sda_se_iban_validator.repositories.BlacklistedIbanRepository;
import com.sda_se_iban_validator.sda_se_iban_validator.services.IbanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(IbanServiceImpl.class)
class BootstrapDataTest {
    @Autowired
    BlacklistedIbanRepository blacklistedIbanRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(blacklistedIbanRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run();

        assertThat(blacklistedIbanRepository.count()).isGreaterThan(0);
    }
}