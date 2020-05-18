package cz.muni.fi.pa165.dmbk.machinerental;

import cz.muni.fi.pa165.dmbk.machinerental.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configures Spring Boot security to secure
 * destinations of web application. Defines URL
 * where client will be redirected in case of
 * unauthorized access. Also specifies how logout
 * should be handled.
 *
 * @author Norbert Dopjera
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/rest/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/rest/user/login").permitAll()
                .antMatchers("/rest/user/**").hasAnyAuthority("USER")
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/**").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login"))
                .and().exceptionHandling().accessDeniedPage("/login")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logedoff.xhtml")
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    /**
     * Provides custom User details service implemented
     * in pa165-dmbk-machine-rental-security module and custom password
     * encoder to be used when identity is authenticated.
     *
     * @param auth authentication manager builder to configure
     * @throws Exception should not occur
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
