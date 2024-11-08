package com.hackathone.LMS.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathone.LMS.Entities.UserRole;
import com.hackathone.LMS.Entities.Users;
import com.hackathone.LMS.ErrorMessages.BaseResponse;
import com.hackathone.LMS.repositories.RoleRepository;
import com.hackathone.LMS.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	public BaseResponse<?> findByUserId(Long userId) {
		Optional<Users> user = userRepo.findById(userId);
		if (!user.isEmpty()) {

			return new BaseResponse<>(List.of(user.get()), "User found successfully", 200, 1);
		} else {
			return new BaseResponse<>(null, "User not found", 400, 0);
		}

	}

	private String createUserName(String firstName, String lastName) {
		String firstPart = firstName.substring(0, Math.min(3, firstName.length()));
		String lastPart = lastName.substring(0, Math.min(3, lastName.length()));

		Random random = new Random();
		int randomNumber = 100000 + random.nextInt(900000);

		String combinedString = (firstPart + lastPart + randomNumber);
		if (combinedString.length() > 12) {
			combinedString = combinedString.substring(0, 12);
		}

		return combinedString;
	}

	public BaseResponse<?> registerUser(Users user) {

		Users tmp = userRepo.findByPanNo(user.getPanNo());
		if (tmp != null)
			return new BaseResponse<>(List.of(tmp), "User is already available", 401, 0);

		String username = createUserName(user.getFirstName(), user.getLastName());
		user.setUsername(username);
		user.setCreatedAt(LocalDateTime.now());
		user.setIsKYCCompleted(false);
		UserRole role = roleRepo.findByName("USER");

		user.setRole(role);
		return new BaseResponse<>(List.of(userRepo.save(user)), "User registered successfully", 200, 1);
	}

	public BaseResponse<?> findAllUSers() {
		List<?> usersList = userRepo.findAll();
		if (usersList.isEmpty())
			return new BaseResponse<>(null, "No users found", 400, 0);

		return new BaseResponse<>(userRepo.findAll(), "Found all users", 200, 1);
	}

}
