package com.tocks.webapp.Services;



import com.tocks.webapp.Models.User;
import com.tocks.webapp.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService
{
    @Autowired
    private UsersRepository usersRepository;
    public List<User> findAll()
    {
        return usersRepository.findAll();
    }
    public User findByUserName(String username) {return usersRepository.findByUsername(username);}
}
