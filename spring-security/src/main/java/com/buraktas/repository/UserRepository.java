package com.buraktas.repository;

import com.buraktas.entity.UserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Long> {

    UserDAO findByUsername(String username);
}
