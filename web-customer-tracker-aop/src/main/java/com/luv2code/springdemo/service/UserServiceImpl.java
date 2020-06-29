package com.luv2code.springdemo.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.UserDAO;
import com.luv2code.springdemo.entity.Role;
import com.luv2code.springdemo.entity.User;

@Service
@Transactional("securtiyTransactionManager")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	// @Transactional("securtiyTransactionManager")
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user = userDAO.getUser(userName);
		if (user == null) {
			throw new UsernameNotFoundException(
					"Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	// @Transactional("securtiyTransactionManager")
	public User findUser(String username) {
		return userDAO.getUser(username);
	}

	@Override
	// @Transactional("securtiyTransactionManager")
	public void saveUser(User user) {
		userDAO.saveUser(user);// TODO : modify to get the role from DB

	}

	@Override
	// @Transactional("securtiyTransactionManager")
	public void deleteUser(User user) {
		userDAO.deleteUser(user);
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(
			Collection<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}
