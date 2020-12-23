package io.oenomel.tree.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(
                        String.format(
                                "jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Seoul&verifyServerCertificate=false&useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8",
                                "127.0.0.1",
                                3306,
                                "eva_pay"
                        )
                )
                .username("dservice")
                .password("tjqltm!!@")
                .build();
    }
}
