package com.jx530.excelimporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class ExcelImporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelImporterApplication.class, args);
    }

}
