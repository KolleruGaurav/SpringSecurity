package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.security.repository.UserRepository;
import com.spring.security.service.CustomUserdetailService;

@EnableGlobalMethodSecurity(prePostEnabled =true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserdetailService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				// TODO Auto-generated method stub
				return rawPassword.toString();
			}
		});
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("**/secure/**").authenticated()
		.anyRequest().permitAll()
		.and().formLogin().permitAll();
		
	}
}
