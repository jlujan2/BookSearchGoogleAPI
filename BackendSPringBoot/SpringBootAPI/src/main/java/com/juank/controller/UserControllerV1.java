package com.juank.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.Value;
import com.juank.entity.User;
import com.juank.jpa.UserRepository;
import com.juank.repoFirebase.UserRepo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserControllerV1 {

	@Autowired
	UserRepo repo;
	
	@Autowired
	UserRepository repoJpa;
	
	@Value("${message}")
	String messageFromProp;
	
	@PostMapping
	public ResponseEntity<String> saveUser(@RequestBody @Valid User user, final BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return  ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
		}
		Date currentDate = new Date();
		if(currentDate.before(user.getDob()))
			return new ResponseEntity<>("Year of birth cannot be in future", HttpStatus.BAD_REQUEST);
		else {
			//repo.createUser(user);
			repoJpa.save(user);
			return new ResponseEntity<>("user created", HttpStatus.OK);
		}
	}
	
	@GetMapping()
	public List<User> getUsers() {
		return new ArrayList<User>();
	}
	
	@GetMapping(value = "/message")
	public String getMessage() {
		return messageFromProp;
	}
	
	@GetMapping(value="/version")
	public String getVersion() {
		return "Version 1";
	}
	
}
