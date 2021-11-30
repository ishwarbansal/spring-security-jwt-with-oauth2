package com.bansal.springsecurityjwt.service;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestSecurityController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello !!";
	}

}
