package com.tocks.webapp.Repositories;

import com.tocks.webapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<User,Integer>
{
    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u")
    List<User> getAll();
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    boolean existsByPasswordAndUsername(String password, String username);
}