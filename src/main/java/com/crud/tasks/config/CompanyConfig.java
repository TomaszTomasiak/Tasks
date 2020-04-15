package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {

    @Value("${company.name}")
    private String companyName;

    @Value("${company.street}")
    private String companyStreet;

    @Value("${company.city}")
    private String companyCity;

    @Value("${company.zipcode}")
    private String companyZipcode;

    @Value("${company.website}")
    private String companyWebsite;

    @Value("${company.mail}")
    private String companyMail;
}
