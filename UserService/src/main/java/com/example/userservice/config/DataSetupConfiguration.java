package com.example.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Configuration
@RequiredArgsConstructor
public class DataSetupConfiguration implements CommandLineRunner {
	private final R2dbcEntityTemplate r2dbcEntityTemplate;

	@Value("classpath:h2/init.sql")
	private Resource initSql;

	@Override
	public void run(String... args) throws Exception {
		// 만약 내장 DB로 앱을 띄운다면 유용하게 사용 가능
//		String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
//		System.out.println(query);
//		r2dbcEntityTemplate.getDatabaseClient()
//		                   .sql(query)
//		                   .then()
//		                   .subscribe();
	}
}
