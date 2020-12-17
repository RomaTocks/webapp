package com.tocks.webapp.Models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "brand")
public class Brand
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandid;
    @Column
    private String brandname;
    @OneToMany(mappedBy = "brands", fetch=FetchType.EAGER)
    private Collection<Product> products;

    public Brand()
    {
    }

    public Brand(String brandname)
    {
        this.brandname = brandname;
    }

    public Brand(Long brandid)
    {
        this.brandid = brandid;
    }

    public Brand(Long brandid, String brandname)
    {
        this.brandid = brandid;
        this.brandname = brandname;
    }

    public Long getBrandid()
    {
        return brandid;
    }

    public void setBrandid(Long brandid)
    {
        this.brandid = brandid;
    }

    public String getBrandname()
    {
        return brandname;
    }

    public void setBrandname(String brandname)
    {
        this.brandname = brandname;
    }
}
