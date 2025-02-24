package com.sda_se_iban_validator.sda_se_iban_validator.repositories;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.BlacklistedIban;
import com.sda_se_iban_validator.sda_se_iban_validator.entities.Iban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlacklistedIbanRepository extends JpaRepository<BlacklistedIban, UUID> {
}
