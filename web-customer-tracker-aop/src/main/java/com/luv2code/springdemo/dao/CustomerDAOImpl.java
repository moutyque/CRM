package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;
@Repository
public class CustomerDAOImpl implements CustomerDAO {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	@Override
	public List<Customer> getCustomers() {
		Session session = factory.getCurrentSession();

		Query<Customer> query = session
				.createQuery("from Customer order by lastName", Customer.class);
		return query.getResultList();

	}
	@Override
	public void saveCustomer(Customer customer) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(customer);
	}
	@Override
	public Customer getCustomer(int id) {
		Session session = factory.getCurrentSession();

		return session.get(Customer.class, id);
	}
	@Override
	public void deleteCustomer(int id) {
		Session session = factory.getCurrentSession();
		@SuppressWarnings("rawtypes") // Delete query no need of typing
		Query query = session
				.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}
	@Override
	public List<Customer> getCustomerByLastName(String lastName) {
		Session session = factory.getCurrentSession();

		Query<Customer> query = session.createQuery(
				"from Customer where lastName like :criteria", Customer.class);
		query.setParameter("criteria", "%" + lastName + "%");
		return query.getResultList();
	}

}
