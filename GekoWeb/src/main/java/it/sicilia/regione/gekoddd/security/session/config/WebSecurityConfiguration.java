package it.sicilia.regione.gekoddd.security.session.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


//import javax.sql.DataSource;
/*
 * AuthenticationManager è solo una interfaccia
 * Provider 
 */

@EnableWebSecurity
	public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		//.p  .passwordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/menu") // .failureUrl("/login-error")
			.and()
			.logout()
			.deleteCookies("remove")
            .invalidateHttpSession(true)
			.logoutSuccessUrl("/login")
			.and()
			.authorizeRequests()
			.antMatchers("/resources/**","/login","/logout").permitAll()
			.anyRequest().authenticated();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			private ShaPasswordEncoder shap= new ShaPasswordEncoder(1);
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				if (encodedPassword == null || encodedPassword.length() == 0) {
					return false;
				}
				return shap.isPasswordValid(encodedPassword, rawPassword.toString(), null);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return shap.encodePassword(rawPassword.toString(), null);
			}
		};
	}
/*
 import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
  }
  
  @Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			private ShaPasswordEncoder shap= new ShaPasswordEncoder(256);
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				if (encodedPassword == null || encodedPassword.length() == 0) {
					return false;
				}
				return shap.isPasswordValid(encodedPassword, rawPassword.toString(), null);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return shap.encodePassword(rawPassword.toString(), null);
			}
		};
	}
}
 */
}
