package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.bootstrap.BootstrapData;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure the correct interaction with the repository saving all to be scanned URL's.
 */
@DataJpaTest
@Import(BootstrapData.class)
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    /**
     * This test is in charge of ensuring that newly added URL's consisting the to be scanned PDF's are correctly saved.
     * This test is currently disabled due to the static nature of local filepaths. If so wished, a to be tested file path can be inserted in the test on any local setup.
     */
    @Disabled
    @Transactional
    @Rollback
    @Test
    void testSaveToBeCheckedIbanUrlLocalSource() {
        Url url = urlRepository.save(Url.builder()
                .url("URL")
                .build());

        urlRepository.flush();

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isNotNull();
    }

    /**
     * This test is in charge of ensuring that newly added URL's consisting the to be scanned PDF's are correctly saved.
     */
    @Transactional
    @Rollback
    @Test
    void testSaveToBeCheckedIbanUrlOnlineSource() {
        Url url = urlRepository.save(Url.builder()
                .url("https://www.wmaccess.com/downloads/sample-invoice.pdf")
                .build());

        urlRepository.flush();

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isNotNull();
    }
}