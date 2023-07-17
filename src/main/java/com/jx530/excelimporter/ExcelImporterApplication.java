package com.jx530.excelimporter;

import com.jx530.excelimporter.model.BaseModel;
import com.jx530.excelimporter.sequence.Sequence;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableJdbcAuditing
@EnableJdbcRepositories
public class ExcelImporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelImporterApplication.class, args);
    }

    @Bean
    public BeforeConvertCallback<BaseModel> idGenerator() {
        var sequence = new Sequence(null);
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return (model) -> {
            if (model.getId() == null) {
                String idStr = Long.toHexString(sequence.nextId()); // 16
                String time = LocalDateTime.now().format(formatter);// 12
                String rand = RandomStringUtils.randomAlphanumeric(4);//4
                // 32位字符串
                model.setId(idStr + time + rand);
            }
            return model;
        };
    }

    @Bean
    public NamingStrategy namingStrategy() {
        return new NamingStrategy() {
            @Override
            public String getColumnName(RelationalPersistentProperty property) {
                Assert.notNull(property, "Property must not be null.");
                return property.getName();
            }
        };
    }
}
