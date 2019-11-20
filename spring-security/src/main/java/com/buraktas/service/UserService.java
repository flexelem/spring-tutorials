package com.buraktas.service;

import com.buraktas.entity.UserDAO;
import com.buraktas.entity.UserDTO;
import com.buraktas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDAO findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Long save(UserDTO userDTO) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUsername(userDTO.getUsername());
        userDAO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userDAO).getId();
    }
}
