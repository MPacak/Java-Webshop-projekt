package hr.webshop.service;

import hr.webshop.irepository.UserRepository;
import hr.webshop.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       //ako ces imati listu rola onda vidi njegovo sto je napravio
       return org.springframework.security.core.userdetails.User
               .withUsername(username)
               .password(user.getPasswordHash())
               .roles(String.valueOf(user.getRole()))
               .build();
    }
}

