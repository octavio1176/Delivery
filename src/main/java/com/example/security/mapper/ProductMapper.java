package com.example.security.mapper;
import com.example.security.domain.product.ProductRequest;
import com.example.security.domain.product.ProductResponse;
import com.example.security.domain.entity.Product;
import java.util.List;

public class ProductMapper {

   public static Product toEntity(ProductRequest  dto)
   {

       Product product = new  Product();
       product.setName(dto.name());
       product.setQuantity(dto.quantity());
       product.setPrice(dto.price());
       product.setCategory(dto.category_id());
       return product;
   }

    public static void updateEntity(Product product, ProductRequest dto) {
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
    }

    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}