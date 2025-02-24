package com.sda_se_iban_validator.sda_se_iban_validator.services;

import com.sda_se_iban_validator.sda_se_iban_validator.entities.Url;
import com.sda_se_iban_validator.sda_se_iban_validator.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public Url saveNewUrl(Url url) {
        Url savedUrl = url;
        String processedUrl = savedUrl.getUrl().replaceAll("\\\\", "/");
        savedUrl.setUrl(processedUrl);

        System.out.println(savedUrl.getUrl());

        return urlRepository.save(savedUrl);
    }
}
