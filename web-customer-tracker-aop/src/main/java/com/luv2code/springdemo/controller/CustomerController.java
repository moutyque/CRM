package com.luv2code.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model model) {
		model.addAttribute("customers", customerService.getCustomers());
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormFroAdd(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(
			@ModelAttribute(name = "customer") Customer customer) {

		customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id,
			Model model) {
		model.addAttribute("customer", customerService.getCustomer(id));

		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id,
			Model model) {

		customerService.deleteCustomer(id);

		return "redirect:/customer/list";

	}

	@GetMapping("/searchCustomer")
	public String searchCustomer(
			@RequestParam("searchCriteria") String lastName, Model model) {

		model.addAttribute("customers",
				customerService.getCustomerByLastName(lastName));

		return "list-customers";

	}
}
