package sg.nus.iss.facialrecognition.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Deprecated
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired(required = true)
    public UserDetailsService uds;

	@Bean
public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
    return new MySimpleUrlAuthenticationSuccessHandler();
}

	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(uds);
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/registeraccount").permitAll()
				.antMatchers("/user/activate").permitAll()
				.antMatchers("/child/*").hasAuthority("child")
				.antMatchers("/parent/*").hasAuthority("parent")
				.antMatchers("/admin/*").hasAuthority("admin")
				.antMatchers("/css/**","/images/**","/js/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().ignoringAntMatchers("/android")



				//redirect to different URLs for different roles
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(myAuthenticationSuccessHandler())

				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")

				.and()
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");

	}


}
