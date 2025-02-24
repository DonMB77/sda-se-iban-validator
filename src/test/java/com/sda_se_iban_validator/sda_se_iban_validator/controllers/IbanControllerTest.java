package com.sda_se_iban_validator.sda_se_iban_validator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.BlacklistedIbanRepository;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.IbanRepository;
import com.sda_se_iban_validator.sda_se_iban_validator.services.IbanService;
import com.sda_se_iban_validator.sda_se_iban_validator.services.IbanServiceImpl;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class IbanControllerTest {

    @Autowired
    IbanController ibanController;

    @Autowired
    IbanRepository ibanRepository;

    @Autowired
    BlacklistedIbanRepository blacklistedIbanRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListBlacklistedIbans() {
        List<BlacklistedIban> blacklistedIbanList = ibanController.listBlacklistedIbans();

        assertThat(blacklistedIbanList.size()).isGreaterThan(0);
    }

    @Disabled
    @Test
    void testCreateUrlAndValidateIbans() throws Exception {
        Url url = Url.builder()
                .url("C:/Users/dmarc/OneDrive/Dokumente/Testdata_Invoices.pdf")
                .build();
        mockMvc.perform(post(IbanController.IBAN_DOCUMENT_PDF_IMPORT_PATH_LOCAL_FILE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testCreateBlacklistedIban() throws Exception {
        BlacklistedIban newBlacklistedIban = BlacklistedIban.builder()
                .IBAN("DE15 3006 0601 0505 7807 83")
                .build();
        mockMvc.perform(post(IbanController.BLACKLISTED_IBAN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBlacklistedIban)))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}