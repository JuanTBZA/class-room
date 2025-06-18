package com.juantirado.virtual_classroom.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqlFileExecutor implements ApplicationRunner {

    private final JdbcTemplate template;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource resourceSql = new ClassPathResource("db/sql/data-inserts-custom.sql");
        String sql = new String(resourceSql.getInputStream().readAllBytes());

        for (String statement : sql.split(";")) {
            if (!statement.trim().isBlank()) {
                template.execute(statement);
            }
        }

        log.info("### SQL customizado ejecutado correctamente.");
    }
}