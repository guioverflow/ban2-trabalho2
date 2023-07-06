package com.atacadista.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // CREATE
    public Product insert(Product product){
        return repository.save(product);
    }

    // SELECT
    public List<Product> selectAll(){
        return repository.findAll();
    }

    public Product selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Product update(Long id, Product product)  {
        Product productBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto com esse ID nao existe: " + id));
        productBean.setName(product.getName());
        productBean.setDescription(product.getDescription());

        return repository.save(productBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public void printList(List<Product> productList) {
        for (Product product : productList) {
            System.out.println(
                    String.format("%-10s - %-10s - %-10s",
                            product.getIdProduct(),
                            product.getName(),
                            product.getDescription()
                    )
            );
        }
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
