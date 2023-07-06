package com.atacadista.product;

import com.atacadista.util.ControllerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class ProductController extends ControllerOperation {
    @Autowired
    private ProductService service;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Product product = new Product();

        System.out.println("Nome do produto: ");
        String name = scanner.nextLine();
        product.setName(name);

        System.out.println("Descricao do produto (Opcional): ");
        String description = scanner.nextLine();
        product.setDescription(description);

        service.insert(product);
    }

    @Override
    public void selectAll() {
        List<Product> products = service.selectAll();
        System.out.println(
                String.format("%-20s - %-20s",
                        "ID Produto",
                        "Nome do produto"
                )
        );
        for(Product product : products) {
            System.out.println(
                    String.format("%-20s - %-20s",
                            product.getIdProduct(),
                            product.getName()
                    )
            );
        }
    }

    @Override
    public void selectById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        System.out.println("\n");

        if (!service.checkId(id)) {
            System.out.println("ID nao existe.");
            return;
        }

        Product product =  service.selectById(id);

        System.out.println("Nome: " + product.getName());
        System.out.println("Descricao: " + product.getDescription());
    }

    @Override
    public void update() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (!service.checkId(id)) {
            System.out.println("ID nao existe.");
            return;
        }
        Product productBean = new Product();

        // Limpa buffer
        if (scanner.hasNextLine()) scanner.nextLine();

        System.out.println("Nome: ");
        String name = scanner.nextLine();
        productBean.setName(name);

        System.out.println("Descricao (Opcional): ");
        String description = scanner.nextLine();
        productBean.setDescription(description);

        service.update(id, productBean);
    }

    @Override
    public void deleteById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (service.checkId(id))
            service.deleteById(id);
        else System.out.println("ID nao existe.");
    }
}
