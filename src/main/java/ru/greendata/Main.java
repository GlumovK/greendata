package ru.greendata;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.greendata.model.Bank;
import ru.greendata.model.Client;
import ru.greendata.model.Deposit;
import ru.greendata.model.LegalForm;
import ru.greendata.repository.JpaClientRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;


import static java.time.LocalDateTime.of;
import static ru.greendata.model.LegalForm.FORM_PERSON;

public class Main {
    public static void main(String[] args) {

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml",
                "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        }
    }
}
