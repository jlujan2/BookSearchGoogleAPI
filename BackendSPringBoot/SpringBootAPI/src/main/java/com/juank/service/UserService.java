package com.juank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.juank.entity.User;
import com.juank.jpa.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public void saveUser(User user) {
		repo.save(user);
	}
	
	public List<User> getUsers() {
		return repo.findAll();
	}
	
	public List<User> getUserPageList(Integer pageNumber, Integer pageSize, String sort) {
		Pageable pageable = null;
		if(sort != null) {
			pageable = PageRequest.of(pageNumber, pageSize,Sort.Direction.ASC, sort);
		}else {
	      pageable = PageRequest.of(pageNumber, pageSize);
	    }
		return repo.findAll(pageable).getContent();
	}
	
}
