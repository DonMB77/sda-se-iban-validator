package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.bootstrap.BootstrapData;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BootstrapData.class)
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Disabled
    @Test
    void testSaveToBeCheckedIban() {
        Url url = urlRepository.save(Url.builder()
                .url("C:/Users/dmarc/OneDrive/Dokumente/Testdata_Invoices.pdf")
                .build());

        urlRepository.flush();

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isNotNull();
    }

    @Disabled
    @Test
    void testSaveToBeCheckedIbanOnlineSource() {
        Url url = urlRepository.save(Url.builder()
                .url("https://www.wmaccess.com/downloads/sample-invoice.pdf")
                .build());

        urlRepository.flush();

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isNotNull();
    }
}