package org.dbms.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JDBCConection {

	@Bean
	public Connection getConnection(JdbcTemplate jdbcTemplate) {
		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
