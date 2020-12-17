package com.tocks.webapp.Controllers;


import com.tocks.webapp.Exceptions.BadDataException;
import com.tocks.webapp.Exceptions.NotFoundException;
import com.tocks.webapp.Exceptions.RequestException;
import com.tocks.webapp.Models.User;
import com.tocks.webapp.Repositories.RoleRepository;
import com.tocks.webapp.Repositories.UsersRepository;
import com.tocks.webapp.Services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController
{
    private final UsersRepository usersRepository;

    public List<User> getSomeUsers()
    {
        return usersRepository.getAll();
    }

    @Autowired
    public MessageController(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public List<User> users()
    {
        return getSomeUsers();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable Integer id)
    {
        return getUser(id);
    }

    private User getUser(@PathVariable Integer id)
    {
        return getSomeUsers().stream().filter(user -> user.getUserid().equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }
    @PostMapping
    public ResponseEntity<? extends Object> create( @RequestBody User user)
    {
        if(user.getPassword() == "" || user.getUsername() == "" || user.getEmail() == "")
        {
            return new ResponseEntity<RequestException>(new RequestException("Введите данные"), HttpStatus.UNAUTHORIZED);
        }
        else
        {
            if(usersRepository.existsByUsername(user.getUsername()))
            {
                return new ResponseEntity<RequestException>(new RequestException("Пользователь с таким именем уже существует!"), HttpStatus.UNAUTHORIZED);
            }
            else
            {
                usersRepository.save(user);
                return new ResponseEntity<RequestException>(new RequestException("OK"), HttpStatus.OK);
            }

        }
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") User userFromDB, @RequestBody User user)
    {
        BeanUtils.copyProperties(user,userFromDB,"id");
        return usersRepository.save(userFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id)
    {
        usersRepository.delete(new User(id));
    }

}
