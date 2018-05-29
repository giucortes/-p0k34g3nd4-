package com.giucortes.ws.rest.services;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {
	private String test;

	public TestService() {
		test = "Alô mundão";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> helloWorld() {
		return new ResponseEntity<String>(test, HttpStatus.OK);
	}
}
