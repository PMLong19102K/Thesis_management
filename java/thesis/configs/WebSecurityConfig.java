package thesis.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import thesis.configs.services.AccountService;
import thesis.configs.services.LoginSuccessService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountService accountService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new LoginSuccessService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests().antMatchers("/", "/sign-in", "/sign-out").permitAll();
		http.authorizeRequests().antMatchers("/resources/**", "/static/**", "/contents/**").permitAll();
		http.authorizeRequests().antMatchers("/user/*").access("hasAnyRole('ROLE_USER')").antMatchers("/admin/*")
				.access("hasRole('ROLE_ADMIN')").antMatchers("/management/*", "/download", "/download-file")
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().anyRequest().authenticated();

		http.authorizeRequests().and().formLogin().loginProcessingUrl("/j_spring_security_check").loginPage("/sign-in")
				.defaultSuccessUrl("/home").successHandler(successHandler()).failureUrl("/sign-in?error=true")
				.usernameParameter("email").passwordParameter("password").and().logout().logoutUrl("/sign-out")
				.logoutSuccessUrl("/sign-in").deleteCookies("JSESSIONID");
	}
}
