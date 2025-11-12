package com.sistema.cinema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class CinemasistemaApplicationTests {

	@Test
	void contextLoads() {
	}
	
	public static void main(String[] args) {
	   	BCryptPasswordEncoder b = new BCryptPasswordEncoder();
	   	System.out.println(b.encode("123"));
	}


}
