package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    Context createContext() {
        Context context = new Context();

        context.setVariable("tasks_url", "https://tomasztomasiak.github.io/");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_street", companyConfig.getCompanyStreet());
        context.setVariable("company_city", companyConfig.getCompanyCity());
        context.setVariable("company_zipcode", companyConfig.getCompanyZipcode());
        context.setVariable("company_mail", companyConfig.getCompanyMail());
        context.setVariable("company_website", companyConfig.getCompanyWebsite());
        return context;
    }

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello Acount");
        functionality.add("Application allows sending tasks to Trello");

        Context context = createContext();
        context.setVariable("message", message);
        context.setVariable("button", "Visit website");
        context.setVariable("goodbay_message", "Have a nice day");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("is_tasks", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyEmail(String message) {

     List<Task> tasks = taskRepository.findAll();

        Context context = createContext();
        context.setVariable("message", message);
        context.setVariable("button", "Visit website");
        context.setVariable("goodbay_message", "Have a nice day");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("is_tasks", true);
        context.setVariable("application_functionality", tasks);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
