package com.tocks.webapp.Controllers;

import com.tocks.webapp.Models.Brand;
import com.tocks.webapp.Models.Category;
import com.tocks.webapp.Models.Product;
import com.tocks.webapp.Models.User;
import com.tocks.webapp.Repositories.BrandRepository;
import com.tocks.webapp.Repositories.CategoryRepository;
import com.tocks.webapp.Repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class CatalogController
{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public CatalogController(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }
    @PostMapping
    public void create(@RequestBody Product product)
    {
        productRepository.save(product);
    }
    @PostMapping("/brands")
    public void createBrand(@RequestBody Brand brand)
    {
        brandRepository.save(brand);
    }
    @PostMapping("/categories")
    public void createCategory(@RequestBody Category category)
    {
        categoryRepository.save(category);
    }
    @PutMapping("{id}")
    public void update(@PathVariable("id") Product productFromDB, @RequestBody Product product)
    {
        BeanUtils.copyProperties(product,productFromDB,"id");
        productRepository.save(productFromDB);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id)
    {
        productRepository.delete(new Product(id));
    }
    @GetMapping
    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }
    @GetMapping("{id}")
    public Optional<Product> getOne(@PathVariable Long id)
    {
        return productRepository.findById(id);
    }
    @GetMapping("/brands")
    public List<Brand> getBrands()
    {
        return brandRepository.findAll();
    }
    @GetMapping("/categories")
    public List<Category> getCategories()
    {
        return categoryRepository.findAll();
    }
}
