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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class in charge of integration tests. Since this is a SpringBootTest, there is no mocking involved.
 */
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

    /**
     * We set up mockMvc before each test. Otherwise, mockMvc will equal null after each test.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Here we test the listing of all blacklisted IBAN's. We then check whether any blacklisted IBAN's are returned.
     */
    @Test
    void testListBlacklistedIbans() {
        List<BlacklistedIban> blacklistedIbanList = ibanController.listBlacklistedIbans();

        assertThat(blacklistedIbanList.size()).isGreaterThan(0);
    }

    /**
     * Here the post operation regarding new blacklisted IBAN's is tested.
     * MockMvc is used to perform post operations against the within the IbanController declared endpoint.
     * @throws Exception
     */
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

    /**
     * Here the delete operation regarding saved blacklisted IBAN's is tested.
     * MockMvc is used to perform delete operations against the within the IbanController declared endpoint.
     * @throws Exception
     */
    @Transactional
    @Rollback
    @Test
    void testDeleteBlacklistedIban() throws Exception {
        BlacklistedIban newBlacklistedIban = BlacklistedIban.builder()
                .IBAN("DE15 3006 0601 0505 7807 84")
                .build();
        blacklistedIbanRepository.save(newBlacklistedIban);
        mockMvc.perform(delete(IbanController.BLACKLISTED_IBAN_PATH_ID, newBlacklistedIban.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}