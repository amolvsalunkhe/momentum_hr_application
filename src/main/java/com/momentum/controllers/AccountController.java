package com.momentum.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.momentum.models.User;
import com.momentum.repositories.UserRepo;
import com.momentum.services.UserDetailService;
import com.momentum.util.JwtUtil;
import com.momentum.validatingforminput.LoginForm;

@Controller
public class AccountController {

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserRepo userRepo;

	@PostMapping(path = "/login")
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginForm.getUserName(), loginForm.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid username and password");
		}
		final UserDetails userDetails = userDetailService.loadUserByUsername(loginForm.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		User user = userRepo.findByUserName(loginForm.getUserName());
		session.setAttribute("jwt", "Bearer " + jwt);
		if (user.getRole().getName().equalsIgnoreCase("admin")) {
			return "redirect:employee-creation-form";
		} else {
			return "redirect:employee-list";
		}
	}

	@GetMapping("/login")
	public String getLoginPage(LoginForm loginForm, HttpSession session) {
		if (session != null && session.getAttribute("jwt") != null) {
			session.removeAttribute("jwt");
		}
		return "login";
	}

	@GetMapping("/logout")
	public String getLogOut(HttpSession session) {
		session.removeAttribute("jwt");
		return "redirect:login";
	}
}
