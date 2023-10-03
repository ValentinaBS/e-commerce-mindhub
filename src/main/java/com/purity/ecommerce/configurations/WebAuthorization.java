package com.purity.ecommerce.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/products", "/api/login", "/api/register",
                        "/web/index.html", "/web/pages/products.html", "/web/pages/product.html", "/web/pages/product.html", "/web/pages/login-signup.html", "/web/pages/contact.html", "/web/pages/about-us.html",
                        "/web/styles/**", "/web/js/**", "/web/assets/**",
                        "/api/products/{productId}", "/api/product/{id}", "/api/customer/current",
                        "/api/cart/**").permitAll()

                .antMatchers(HttpMethod.POST, "/api/cart/**", "/api/process-payment").permitAll()
                .antMatchers(HttpMethod.GET, "/api/customers", "/api/cart/**", "/api/current/orders/generate-pdf").permitAll()

                .antMatchers(HttpMethod.POST, "/api/products/create").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/products/update/{productId}").hasAuthority("ADMIN")
                .antMatchers("/web/pages/admin/**").hasAuthority("ADMIN")

                .antMatchers("/web/pages/profile.html", "/web/pages/checkout.html").hasAuthority("CLIENT")

                .antMatchers("/api/logout").authenticated()

                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login")
                .and()
                .logout().logoutUrl("/api/logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        http.cors();
        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
