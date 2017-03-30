package com.basics.spring.config;

import org.springframework.beans.factory.annotation.Value;

public class DbConfig {

	@Value("${dbConfig.host}")
	private String host;
	@Value("${dbConfig.port}")
	private int port;
	@Value("${dbConfig.database}")
	private String database;
	@Value("${dbConfig.username}")
	private String username;
	@Value("${dbConfig.password}")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DbConfig(String host, int port, String database, String username, String password) {
		super();
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	public DbConfig() {
		
	}

	public String getDatabaseUrl() {
		StringBuffer sb = new StringBuffer("jdbc:mysql://");
		sb.append(this.host);
		sb.append(":");
		sb.append(this.port);
		sb.append("/");
		sb.append(this.database);
		return sb.toString();
	}
}
