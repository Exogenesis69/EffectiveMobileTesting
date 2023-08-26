package ru.sukhdmi.effectiveMobileTesting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.sukhdmi.effectiveMobileTesting.services.UsrDetailsService;

@Configuration
@EnableWebSecurity//настройка spring Security
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

private final UsrDetailsService usrDetailsService;
private final JWTFilter jwtFilter;

@Autowired
    public SecurityConfig(UsrDetailsService usrDetailsService, JWTFilter jwtFilter) {
        this.usrDetailsService = usrDetailsService;
    this.jwtFilter = jwtFilter;
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //конфигурируем сам спринг секьюрити
        //конфигурируем авторизацию
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests().requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello",true)
                .failureUrl("/auth/login?error")
                .and()//logout
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();//фильтр помогающий проводить аутентификацию
    }

    //настраивает аутентификацию

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

}
