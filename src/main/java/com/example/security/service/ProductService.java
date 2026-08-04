package com.example.security.service;
import com.example.security.domain.entity.Product;
import com.example.security.domain.product.ProductRequest;
import com.example.security.domain.repository.ProductRepository;
import com.example.security.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(ProductRequest dto)
    {
        if (productRepository.findByName(dto.name()).isPresent())
        {
            throw new IllegalStateException("Já existe um produto com esse nome: " + dto.name());
        }

        Product product = ProductMapper.toEntity(dto);
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequest dto)
    {
        Product product = findById(id);

        productRepository.findByName(dto.name())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new IllegalStateException("Já existe outro produto com esse nome: " + dto.name());
                });

        ProductMapper.updateEntity(product, dto);
        return productRepository.save(product);
    }

    @Transactional
    public Product findById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + id));
    }

    @Transactional
    public List<Product> findAll()
    {
        return productRepository.findAll();
    }

    @Transactional
    public void delete(Long id)
    {
        Product product = findById(id);
        productRepository.delete(product);
    }

}