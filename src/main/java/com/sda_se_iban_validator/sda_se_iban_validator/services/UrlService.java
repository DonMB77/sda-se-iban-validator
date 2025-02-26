package com.sda_se_iban_validator.sda_se_iban_validator.services;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;

/**
 * Interface used for implementing services working and processing URL's
 * Interfaces are used here for quick but robust implementations of those services.
 */

public interface UrlService {
    Url saveNewUrl(Url url);
}
