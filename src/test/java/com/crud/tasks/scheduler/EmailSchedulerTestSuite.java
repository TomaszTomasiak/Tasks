package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.EmailSelector;
import com.crud.tasks.service.SimpleMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleMailService simpleMailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;


    @Test
    public void shouldSendEmail() {
        //given
        when(adminConfig.getAdminMail()).thenReturn("test@test.test");
        when(taskRepository.count()).thenReturn(20L);

        //when
        emailScheduler.sendInformationEmail();
        //then
        verify(taskRepository, times(1)).count();
        verify(simpleMailService, times(1)).send(new Mail("test@test.test", null,
                "Tasks: Once a day email","Currently in database you have got: 20 tasks"), EmailSelector.DAILY_TASKS_NUMBER);
        verify(adminConfig, times(1)).getAdminMail();
    }
}