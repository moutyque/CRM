package com.luv2code.springdemo.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.luv2code.springdemo.entity.User;
@Component
public class UserDAOImpl implements UserDAO {

	@Autowired
	@Qualifier("securitySessioFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from User", User.class);
		return query.getResultList();
	}

	@Override
	public void saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);

	}

	@Override
	public User getUser(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query<User> query = session
				.createQuery("from User where username=:userName", User.class);
		query.setParameter("userName", username);
		Optional<User> opt = query.uniqueResultOptional();
		if (opt.isPresent()) {
			return opt.get();
		} else {

			return null;
		}

	}

	@Override
	public void deleteCustomer(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}

}
