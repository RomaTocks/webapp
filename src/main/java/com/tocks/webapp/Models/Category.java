package com.tocks.webapp.Models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryid;
    @Column
    private String categoryname;
    @OneToMany(mappedBy = "categories", fetch=FetchType.EAGER)
    private Collection<Product> products;

    public Category()
    {
    }

    public Category(Long categoryid)
    {
        this.categoryid = categoryid;
    }

    public Category(Long categoryid, String categoryname)
    {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
    }

    public Long getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(Long categoryid)
    {
        this.categoryid = categoryid;
    }

    public String getCategoryname()
    {
        return categoryname;
    }

    public void setCategoryname(String categoryname)
    {
        this.categoryname = categoryname;
    }
}
