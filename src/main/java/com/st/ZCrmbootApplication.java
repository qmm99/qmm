package com.st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.st.dao")
public class ZCrmbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZCrmbootApplication.class, args);
	}

}
