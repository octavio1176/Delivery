package com.example.security.controller;

import com.example.security.domain.entity.Product;
import com.example.security.domain.product.ProductRequest;
import com.example.security.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("API")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/list")
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


}
