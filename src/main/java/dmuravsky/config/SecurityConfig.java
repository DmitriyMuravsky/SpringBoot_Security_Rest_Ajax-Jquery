package dmuravsky.config;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import dmuravsky.security.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("dmuravsky")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/*").anonymous()
                .antMatchers( "/user/*").hasAnyAuthority("user", "admin")
                .antMatchers("/admin/*").hasAuthority("admin")
                .and().csrf().disable()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/auth/signIn/process").successHandler(authenticationHandler())
                .usernameParameter("login")
                .and().logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationHandler() {
        return new AuthenticationHandler();
    }
}
