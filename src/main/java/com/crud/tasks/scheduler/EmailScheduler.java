package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleMailService simpleMailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    void sendInformationEmail() {

        long size = taskRepository.count();
        String tasks = "tasks";
        if(size == 1) {
            tasks = "task";
        }
        simpleMailService.send(new Mail(adminConfig.getAdminMail(), "", SUBJECT,
                    "Currently in database you have got: " + size + " " + tasks));

    }

}
