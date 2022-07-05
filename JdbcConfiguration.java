package library.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JdbcConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("admin");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setPassword("admin");

        return new JdbcTemplate(dataSource);
    }
}
