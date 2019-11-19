package com.buraktas.service;

import com.buraktas.entity.UserDAO;
import com.buraktas.entity.UserDTO;
import com.buraktas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Long save(UserDTO userDTO) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUsername(userDTO.getUsername());
        userDAO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userDAO).getId();
    }
}
