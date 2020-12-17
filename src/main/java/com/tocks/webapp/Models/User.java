package com.tocks.webapp.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "roles_rolesid")
    private Role roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    public User()
    {
    }

    public User(Integer userid, String username)
    {
        this.userid = userid;
        this.username = username;
    }

    public User(String username, String email, String password)
    {
        userid = getUserid();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Integer userid, String username, String email, String password, Role roles)
    {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Integer userid, String username, String email, String password)
    {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Integer userid)
    {
        this.userid = userid;
    }

    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer id)
    {
        this.userid = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String mail)
    {
        this.email = mail;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Role getRoles()
    {
        return roles;
    }

    public void setRole(Role role)
    {
        this.roles = roles;
    }
}
