package ru.codecrafters.AuthenticationService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.codecrafters.AuthenticationService.security.AuthManagerImpl;
import ru.codecrafters.AuthenticationService.services.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity
public class SecurityConfig{

    private final JWTFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        "/auth/login",
                                        "/auth/registration",
                                        "/error"
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(fl ->
                        fl.loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
//                                .defaultSuccessUrl("/hello", true)
//                                .failureUrl("/auth/login?error")

                )
//                .logout(logout ->
//                        logout.logoutUrl("/logout")
//                                .logoutSuccessUrl("/auth/login")
//                )
                .sessionManagement(m ->
                        m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();


    }


    @Bean
    public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
    }
}
