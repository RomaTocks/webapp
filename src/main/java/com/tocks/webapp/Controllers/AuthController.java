package com.tocks.webapp.Controllers;

import com.tocks.webapp.Exceptions.BadDataException;
import com.tocks.webapp.Exceptions.RequestException;
import com.tocks.webapp.Models.User;
import com.tocks.webapp.Repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthController
{
    private final UsersRepository usersRepository;

    public AuthController(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @PostMapping
    public ResponseEntity<? extends Object> check(@RequestBody User user)
    {
        if(!usersRepository.existsByUsername(user.getUsername()))
        {
            return new ResponseEntity<RequestException>(new RequestException("Неправильный логин!"), HttpStatus.UNAUTHORIZED);
        }
        else
        {
            if (!usersRepository.existsByPasswordAndUsername(user.getPassword(),user.getUsername()))
            {
                return new ResponseEntity<RequestException>(new RequestException("Неправильный пароль!"), HttpStatus.UNAUTHORIZED);
            }
            else
            {
                User send = new User();
                send.setUserid(usersRepository.findByUsername(user.getUsername()).getUserid());
                send.setUsername(usersRepository.findByUsername(user.getUsername()).getUsername());
                send.setEmail(usersRepository.findByUsername(user.getUsername()).getEmail());
                return new ResponseEntity<>(send, HttpStatus.OK);
            }
        }
    }
    @PostMapping("/role")
    public void role(@RequestBody User user)
    {
        if(usersRepository.findByUsername(user.getUsername()).getRoles().getRolesid() != 2)
        {
            throw new BadDataException();
        }
        else
        {
            return;
        }
    }
}
