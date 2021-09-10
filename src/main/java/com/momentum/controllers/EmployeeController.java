package com.momentum.controllers;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.momentum.models.Address;
import com.momentum.models.ContactDetail;
import com.momentum.models.Employee;
import com.momentum.models.User;
import com.momentum.repositories.AddressRepo;
import com.momentum.repositories.ContactDetailRepo;
import com.momentum.repositories.EmployeeRepo;
import com.momentum.repositories.UserRepo;
import com.momentum.util.JwtUtil;
import com.momentum.validatingforminput.EmployeeCreationForm;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepo empRepo;

	@Autowired
	AddressRepo addressRepo;

	@Autowired
	ContactDetailRepo contactDetailRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/employee-list")
	public String getEmployees(Model model, HttpSession session) {
		String jwt = session.getAttribute("jwt").toString().substring(7);
		String userName = jwtUtil.extractUserName(jwt);
		User user = userRepo.findByUserName(userName);
		model.addAttribute("navMap", getNavMap(user.getRole().getName()));
		model.addAttribute("employeeList", empRepo.findAll());
		return "employee-list";
	}

	private Map<String, String> getNavMap(String role) {
		Map<String, String> navMap = new LinkedHashMap<>();
		if (role.equals("Admin")) {
			navMap.put("Employee Creation Form", "/employee-creation-form");
			navMap.put("Employee List", "/employee-list");
		} else {
			navMap.put("Employee List", "/employee-list");
		}
		navMap.put("Logout", "/logout");
		return navMap;
	}

	@GetMapping("/employee-creation-form")
	public String getEmployeeCreationForm(@RequestParam(value = "message") Optional<String> message,
			EmployeeCreationForm employeeCreationForm, Model model, HttpSession session) {
		String jwt = session.getAttribute("jwt").toString().substring(7);
		String userName = jwtUtil.extractUserName(jwt);
		User user = userRepo.findByUserName(userName);
		model.addAttribute("navMap", getNavMap(user.getRole().getName()));
		if (message != null && !message.equals("") && message.isPresent()) {
			model.addAttribute("message", message.get());
		}
		return "employee-creation-form";
	}

	@PostMapping("/createEmployee")
	@Transactional
	public String createEmployee(@Valid EmployeeCreationForm employeeCreationForm, Model model) {
		Employee employee = new Employee();
		employee.setFirstName(employeeCreationForm.getFirstName());
		employee.setLastName(employeeCreationForm.getLastName());
		employee.setEmailAddress(employeeCreationForm.getEmailAddress());
		employee.setEmployeeNumber(employeeCreationForm.getEmployeeNumber());
		empRepo.save(employee);
		Address resAddress = new Address();
		resAddress.setAddress(employeeCreationForm.getResidentialAddress());
		resAddress.setAddressType("residentialAddress");
		resAddress.setEmployee(employee);
		addressRepo.save(resAddress);
		if (employeeCreationForm.getPostalAddress() != null && !employeeCreationForm.getPostalAddress().equals("")) {
			Address postalAddress = new Address();
			postalAddress.setAddress(employeeCreationForm.getResidentialAddress());
			postalAddress.setAddressType("postalAddress");
			postalAddress.setEmployee(employee);
			addressRepo.save(postalAddress);
		}
		ContactDetail mobileNumberDetail = new ContactDetail();
		mobileNumberDetail.setContactNumber(employeeCreationForm.getMobileNumber());
		mobileNumberDetail.setContactType("mobileNumber");
		mobileNumberDetail.setEmployee(employee);
		contactDetailRepo.save(mobileNumberDetail);
		if (employeeCreationForm.getLandLineNumber() != null && !employeeCreationForm.getLandLineNumber().equals("")) {
			ContactDetail landLineNumberDetail = new ContactDetail();
			landLineNumberDetail.setContactNumber(employeeCreationForm.getLandLineNumber());
			landLineNumberDetail.setContactType("landlineNumber");
			landLineNumberDetail.setEmployee(employee);
			contactDetailRepo.save(landLineNumberDetail);
		}
		model.addAttribute("message", "Employee created successfully.");
		return "employee-creation-form";
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Model model) {
		model.addAttribute("message", "Employee creation failed.");
		return "redirect:employee-creation-form";
	}
}
