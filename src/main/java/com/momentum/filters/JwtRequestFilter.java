package com.momentum.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.momentum.services.UserDetailService;
import com.momentum.util.JwtUtil;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
         
		final String authorizationHeader = session!=null && session.getAttribute("jwt")!=null?session.getAttribute("jwt").toString():"";
		String userName = null;
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")
				&& !authorizationHeader.contains("undefined")) {
			jwt = authorizationHeader.substring(7);
			userName = jwtUtil.extractUserName(jwt);
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailService.loadUserByUsername(userName);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken userNameePassAuthToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(userNameePassAuthToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
