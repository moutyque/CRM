package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.User;

public interface UserDAO {
	public List<User> getUsers();

	public void saveUser(User user);

	public User getUser(String username);

	public void deleteUser(User user);

}
