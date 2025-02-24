package com.sda_se_iban_validator.sda_se_iban_validator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class Iban {

    private UUID id;
    private String IBAN;
}
