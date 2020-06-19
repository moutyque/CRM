package com.luv2code.springdemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.luv2code.springdemo.components.UserForm;
import com.luv2code.springdemo.entity.Role;
import com.luv2code.springdemo.entity.User;
import com.luv2code.springdemo.service.UserService;

@Controller
public class UserCreationController {
	@Autowired
	private UserService userService;

	@GetMapping("/createUser")
	public String registerUser(Model model) {
		model.addAttribute("user", new UserForm());
		return "createUser";
	}

	@PostMapping("/createUser")
	public String createUser(@Valid @ModelAttribute("user") UserForm userform,
			BindingResult result) {
		if (result.hasErrors()) {
			return "createUser";
		} else {
			if (userService.findUser(userform.getUsername()) != null) {
				result.reject("existingUser", "This username is already used");
				return "createUser";
			} else {
				User user = new User();
				user.setUsername(userform.getUsername());
				user.setFirstName(userform.getFirstName());
				user.setLastName(userform.getLastName());
				user.setPassword(user.getPassword());
				user.setEmail(userform.getEmail());
				List<Role> roles = new ArrayList<>();
				roles.add(new Role("EMPLOYEE"));
				user.setRoles(roles);
				return "login";
			}

		}

	}

}
