package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://trello.com/b/rRRpyPat/kodilla-application");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbay_message", "Have a nice day");
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_street", companyConfig.getCompanyStreet());
        context.setVariable("company_city", companyConfig.getCompanyCity());
        context.setVariable("company_zipcode", companyConfig.getCompanyZipcode());
        context.setVariable("company_mail", companyConfig.getCompanyMail());
        context.setVariable("company_website", companyConfig.getCompanyWebsite());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
