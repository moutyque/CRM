package com.luv2code.springdemo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.springdemo.entity.User;

public interface UserService extends UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username);
	public User findUser(String username);
	public void saveUser(User user);
	public void deleteUser(User user);

}
