package com.hackathone.LMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathone.LMS.Entities.Users;
import com.hackathone.LMS.ErrorMessages.BaseResponse;
import com.hackathone.LMS.services.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/getUser/{userId}")
	public BaseResponse<?> getUserById(@PathVariable Long userId) {
		return userService.findByUserId(userId);
	}

	@PostMapping("/register")
	public BaseResponse<?> createUser(@RequestBody Users user) {
		return userService.registerUser(user);
	}

	@GetMapping("/all")
	private BaseResponse<?> findAllUSers(){
		return userService.findAllUSers();
	}
}
