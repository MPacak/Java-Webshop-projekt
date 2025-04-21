package hr.webshop.config;

import hr.webshop.component.CustomLoginSuccessHandler;
import hr.webshop.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityBeans {
    private final UserDetailService userDetailService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomLoginSuccessHandler successHandler) throws Exception {
        final String SUCCESSURL = "/webshop/products";
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.contentSecurityPolicy(csp ->
                        csp.policyDirectives("frame-ancestors 'self'")))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/images/**","/webshop/register","/css/**", "/js/**", "/webshop/products",
                                "/webshop/login", "/webshop/order/cart", "/webshop/order/add/**", "webshop/order/remove/**", "webshop/order/update")
                        .permitAll()
                        .requestMatchers("/h2/**", "/webshop/order/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/webshop/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
             /*   .formLogin(form -> form
                        .loginPage("/webshop/login")
                        .defaultSuccessUrl(SUCCESSURL, true)
                        .permitAll()
                )*/
                .formLogin(login -> login
                        .loginPage("/webshop/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(SUCCESSURL)
                        .permitAll())
                .userDetailsService(userDetailService);

        return http.build();
    }

}

