package com.example.security.controller;
import com.example.security.domain.entity.Product;
import com.example.security.domain.product.ProductRequest;
import com.example.security.domain.repository.UserRepository;
import com.example.security.security.JwtService;
import com.example.security.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("API")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService, JwtService jwtService, UserRepository userRepository, UserDetailsService userDetailsService){
        this.productService=productService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Product>> listar()
    {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/criar")
    public Product criarPorduto(@RequestBody ProductRequest product)
    {
        return productService.create(product);
    }

    @PatchMapping("update")
    public Product update (Long id, ProductRequest dto)
    {
        return  update(id, dto);
    }

    @DeleteMapping("delete")
    public void delete(Long id)
    {
      productService.delete(id);
    }

    @GetMapping("findByName")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> findByName(String name)
    {

         return productService.findByName(name);
    }

    @GetMapping("findByID")
    public List<Product> findByName(Long id )
    {
        return Collections.singletonList(productService.findById(id));
    }


}
