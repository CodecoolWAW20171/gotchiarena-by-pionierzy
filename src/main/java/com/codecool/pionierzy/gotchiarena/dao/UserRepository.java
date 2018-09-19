package com.codecool.pionierzy.gotchiarena.dao;


import com.codecool.pionierzy.gotchiarena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
