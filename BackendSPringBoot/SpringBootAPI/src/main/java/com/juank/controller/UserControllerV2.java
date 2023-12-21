package com.juank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v2/user")
public class UserControllerV2 {

	@GetMapping(value="/version")
	public String getVersion() {
		return "Version 2";
	}
}
