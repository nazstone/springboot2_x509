package org.nazstone.x509;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.x509()
			.userDetailsService(userDetailsService())
			.and()
			.authorizeRequests().antMatchers("/admin").authenticated()
			.and()
			.authorizeRequests().antMatchers("/**").permitAll();
	}
	
	@Bean
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) username -> {
        	log.info("user logged:{}", username);
            return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        };
    }
}
