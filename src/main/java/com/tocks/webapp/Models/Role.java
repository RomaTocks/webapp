package com.tocks.webapp.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolesid;
    @Column
    private String role;
    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Role()
    {
    }

    public Role(Long rolesid, String role)
    {
        this.rolesid = rolesid;
        this.role = role;
    }

    public Long getRolesid()
    {
        return rolesid;
    }

    public void setRolesid(Long rolesid)
    {
        this.rolesid = rolesid;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
