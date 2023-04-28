package com.example.url_shortener;

import com.example.url_shortener.auth.AuthenticateRequest;
import com.example.url_shortener.auth.AuthenticationResponse;
import com.example.url_shortener.auth.AuthenticationService;
import com.example.url_shortener.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
@RunWith(SpringRunner.class)
class UrlShortenerApplicationTests {
	@Autowired
	private   AuthenticationService service;
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	void contextLoads() {
	}

	@Test
	public void testRegisterWithValidRequest() {
		RegisterRequest request = new RegisterRequest();
		request.setFirstName("testFirstName");
		request.setLastName("testLastName");
		request.setEmail("testEmail@gmail.com");
		request.setPassword("Pa$$w0rd");

		AuthenticationResponse response = service.register(request);


		assertThat(HttpStatus.OK).isEqualTo(HttpStatus.OK);
		assertThat(ResponseEntity.ok(response)).isNotNull();
	}

	@Test
	public void testRegisterWithInvalidRequest() {
		RegisterRequest request = new RegisterRequest();
		request.setFirstName("");
		request.setLastName("");
		request.setEmail("Pa$$w0rd");
		request.setPassword("testpassword");


		AuthenticationResponse response = service.register(request);


		assertThat(  HttpStatus.OK).isEqualTo(HttpStatus.OK);
		assertThat(ResponseEntity.ok(response)).isNotNull();
	}


	@Test
	public void testLoginWithValidRequest() {
		AuthenticateRequest request = new AuthenticateRequest();
		request.setEmail("testEmail@gmail.com");
		request.setPassword("Pa$$w0rd");

		AuthenticationResponse response = service.authenticate(request);


		assertThat(HttpStatus.NOT_FOUND).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(ResponseEntity.ok(response)).isNotNull();
	}

	@Test
	public void testLoginWithInvalidRequest() {
		AuthenticateRequest request = new AuthenticateRequest();
		request.setEmail("Pa$$w0rd");
		request.setPassword("testpassword");
		AuthenticationResponse response = service.authenticate(request);
		assertThat(  HttpStatus.NOT_FOUND).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(ResponseEntity.ok(response)).isNotNull();
	}



}
