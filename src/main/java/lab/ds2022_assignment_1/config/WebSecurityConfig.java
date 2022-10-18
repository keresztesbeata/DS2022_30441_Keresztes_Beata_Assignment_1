package lab.ds2022_assignment_1.config;

import lab.ds2022_assignment_1.model.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static lab.ds2022_assignment_1.controllers.Constants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Set permissions on endpoints
                .authorizeRequests()
                .antMatchers(LOGIN_PATH).anonymous()
                .antMatchers(REGISTER_PATH).anonymous()
                .antMatchers(LOGOUT_PATH).authenticated()
                .antMatchers(API + "/**").hasAuthority(UserRole.ADMIN.name())
                .antMatchers(CLIENT + "/**").hasAuthority(UserRole.CLIENT.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl(LOGOUT_PATH)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Accept", "Content-Type"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
