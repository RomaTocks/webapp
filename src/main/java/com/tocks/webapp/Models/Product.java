package com.tocks.webapp.Models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column
    private String img;
    @Column
    private int category;
    @Column(name = "description")
    private String description;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_categoryid")
    private Category categories;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_brandid")
    private Brand brands;
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductOrder> products = new ArrayList<>();
    public Product()
    {
    }

    public Product(Long productid, String name, int price, int category, String description, Category categories)
    {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.categories = categories;
    }

    public Product(Long productid, String name, int price, int category, String description)
    {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
    public Product(Long productid, String name, int price, int category, String description, Category categories, Brand brands)
    {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.categories = categories;
        this.brands = brands;
    }

    public Product(Long productid, String name, int price, String img, int category, String description, Category categories, Brand brands)
    {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
        this.description = description;
        this.categories = categories;
        this.brands = brands;
    }

    public Product(Long productid)
    {
        this.productid = productid;
    }

    public Product(Long productid, String name, int price, String description)
    {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public Long getProductid()
    {
        return productid;
    }

    public void setProductid(Long productid)
    {
        this.productid = productid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Category getCategories()
    {
        return categories;
    }

    public void setCategories(Category categories)
    {
        this.categories = categories;
    }

    public Brand getBrands()
    {
        return brands;
    }

    public void setBrands(Brand brands)
    {
        this.brands = brands;
    }
}
