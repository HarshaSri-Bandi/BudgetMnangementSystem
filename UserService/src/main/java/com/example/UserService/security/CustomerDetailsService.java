package com.example.UserService.security;

import com.example.UserService.Entity.CustomerEntity;
import com.example.UserService.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<CustomerEntity> findUser = customerRepo.findByEmail(email);

        return findUser.map(CustomerDetailsInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

}
