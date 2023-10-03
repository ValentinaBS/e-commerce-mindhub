package com.purity.ecommerce.configurations;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

            Customer customer = customerRepository.findByEmail(inputName);

            if (customer != null) {

                if (inputName.contains("admin")) {
                    return new User(customer.getEmail(), customer.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    return new User(customer.getEmail(), customer.getPassword(),

                            AuthorityUtils.createAuthorityList("CLIENT"));
                }

            } else {

                throw new UsernameNotFoundException("Unknown user: " + inputName);

            }
        }).passwordEncoder(passwordEncoder());

    }
}
