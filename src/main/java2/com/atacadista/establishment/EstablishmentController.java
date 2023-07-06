package com.atacadista.establishment;

import com.atacadista.establishment.Establishment;
import com.atacadista.product.Product;
import com.atacadista.product.ProductService;
import com.atacadista.relations.Stock;
import com.atacadista.util.ControllerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class EstablishmentController extends ControllerOperation {
    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private ProductService productService;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Establishment establishment = new Establishment();
        Product chosenProduct;
        Integer stockNumber = null;
        Long userLong = null;
        Stock stock = new Stock();

        System.out.println("Telefone do estabelecimento: ");
        String phone = scanner.nextLine();
        establishment.setPhone(phone);

        System.out.println("CNPJ do estabelecimento: ");
        String CNPJ = scanner.nextLine();
        establishment.setCNPJ(CNPJ);

        establishmentService.insert(establishment);

        // Loop de produtos
        Integer userInput;
        while (true) {
            System.out.println("Continuar cadastrando produtos?\n\n1 - Sim.\n2 - Não.");
            userInput = scanner.nextInt();
            if (userInput.equals(2))
                break;

            List<Product> availableProducts = productService.selectAll();
            productService.printList(availableProducts);

            System.out.println("ID do produto (0 para cancelar): ");
            userLong = scanner.nextLong();
            if (userLong == 0) break;
            else if (!productService.checkId(userLong)) continue;
            chosenProduct = productService.selectById(userLong);

            System.out.println("Estoque do produto: ");
            stockNumber = scanner.nextInt();

            stock.setProduct(chosenProduct);
            stock.setQuantity(stockNumber);
            establishment.stocks(stock);
        }
    }

    @Override
    public void selectAll() {
        List<Establishment> establishments = establishmentService.selectAll();

        System.out.println(
                String.format("%-20s - %-20s - %-20s",
                        "ID Estabelecimento",
                        "Telefone",
                        "CNPJ"
                )
        );
        for(Establishment establishment : establishments) {
            System.out.println(
                    String.format("%-20s - %-20s - %-20s",
                            establishment.getIdEstablishment(),
                            establishment.getPhone(),
                            establishment.getCNPJ()
                    )
            );
        }
    }

    @Override
    public void selectById() {

        selectAll();
        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (!establishmentService.checkId(id)) {
            System.out.println("ID nao existe.");
            return;
        }

        Establishment establishment =  establishmentService.selectById(id);

        System.out.println("Telefone: " + establishment.getPhone());
        System.out.println("CNPJ: " + establishment.getCNPJ());

        System.out.println("");
        System.out.println(String.format("%-20s - %-20s - %-20s - %-20s",
                "ID Produto",
                "Nome do produto",
                "Descricao",
                "Quantidade"
        ));
        for (Stock stock : establishment.getStockProducts()) {
            System.out.println(
                    String.format("%-20s - %-20s - %-20s - %-20s",
                            stock.getProduct().getIdProduct(),
                            stock.getProduct().getName(),
                            stock.getProduct().getDescription(),
                            stock.getQuantity()
                    )
            );
        }
    }

    @Override
    public void update() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (!establishmentService.checkId(id)) {
            System.out.println("ID nao existe.");
            return;
        }

        // Limpa buffer
        if (scanner.hasNextLine()) scanner.nextLine();

        Establishment establishmentBean = new Establishment();

        System.out.println("Telefone: ");
        String phone = scanner.nextLine();
        establishmentBean.setPhone(phone);

        System.out.println("CNPJ: ");
        String CNPJ = scanner.nextLine();
        establishmentBean.setCNPJ(CNPJ);

        establishmentService.update(id, establishmentBean);
    }

    @Override
    public void deleteById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (establishmentService.checkId(id))
            establishmentService.deleteById(id);
        else System.out.println("ID nao existe.");
    }
}