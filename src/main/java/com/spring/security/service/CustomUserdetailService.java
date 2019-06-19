package com.spring.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.model.CustomUserDetails;
import com.spring.security.model.User;
import com.spring.security.repository.UserRepository;

@Service
public class CustomUserdetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUsers = userRepository.findByName(username);

		optionalUsers.orElseThrow(() -> new UsernameNotFoundException("User Name not found"));

		CustomUserDetails customUserDetails = optionalUsers.map(user -> {
			return new CustomUserDetails(user);
		}).get();

		return customUserDetails;
	}

}
