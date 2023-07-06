package com.atacadista.sale;

import com.atacadista.employee.Employee;
import com.atacadista.employee.EmployeeController;
import com.atacadista.employee.EmployeeService;
import com.atacadista.establishment.Establishment;
import com.atacadista.establishment.EstablishmentController;
import com.atacadista.establishment.EstablishmentService;
import com.atacadista.product.Product;
import com.atacadista.product.ProductService;
import com.atacadista.relations.SaleContains;
import com.atacadista.relations.Stock;
import com.atacadista.util.ControllerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class SaleController extends ControllerOperation {
    @Autowired
    private SaleService saleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private EstablishmentController establishmentController;

    @Autowired
    private ProductService productService;
    private SaleController saleController;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Sale saleBean = new Sale();
        Product chosenProduct;

        if (scanner.hasNextLine()) scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Digite a data da venda (ano-mes-dia): ");
        String localDate = scanner.nextLine();
        LocalDate saleDate = LocalDate.parse(localDate, formatter);
        saleBean.setSaleDate(saleDate);

        System.out.print("Feita por qual funcionario (ID): ");
        employeeController.selectAll();
        System.out.println("\n\nInput: ");
        Long EmployeeID = scanner.nextLong();
        if (!employeeService.checkId(EmployeeID)) {
            System.out.println("ID nao existe.");
            return;
        }
        Employee employee = employeeService.selectById(EmployeeID);
        saleBean.madeBy(employee);

        System.out.print("Feita para qual estabelecimento (ID): ");
        establishmentController.selectAll();
        System.out.println("\n\nInput: ");
        Long EstablishmentID = scanner.nextLong();
        if (!establishmentService.checkId(EstablishmentID)) {
            System.out.println("ID nao existe.");
            return;
        }
        Establishment establishment = establishmentService.selectById(EstablishmentID);
        saleBean.madeIn(establishment);

        // Loop de produtos
        Integer userInput;
        Float userInputFloat;
        SaleContains saleContains = new SaleContains();
        List<Stock> actualStock;
        while (true) {
            System.out.println("Continuar cadastrando produtos?\n\n1 - Sim.\n2 - Não.");
            userInput = scanner.nextInt();
            if (userInput.equals(2))
                break;
            List<Product> availableProducts = productService.selectAll();

            productService.printList(availableProducts);

            System.out.println("ID do produto: ");
            chosenProduct = productService.selectById(scanner.nextLong());
            saleContains.setProduct(chosenProduct);

            System.out.println("Quantidade: ");
            userInput = scanner.nextInt();
            saleContains.setQuantity(userInput);

            /*
            // Lógica de dedução de estoque
            actualStock = establishmentService.selectById(EstablishmentID).getStockProducts();
            for (Stock stock : actualStock) {
                if stock.get
            }

             */

            System.out.println("Preco: ");
            userInputFloat = scanner.nextFloat();
            saleContains.setPrice(userInputFloat);

            saleBean.contains(saleContains);
            Sale savedSale = saleService.insert(saleBean);
        }
    }

    @Override
    public void selectAll() {
        List<Sale> sales = saleService.selectAll();
        System.out.println("---- Vendas ----");
        System.out.println(String.format("%-20s - %-20s",
                "ID Venda",
                "Data da venda"
        ));
        saleService.printList(sales);
    }

    @Override
    public void selectById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();
        if (!saleService.checkId(id)) {
            System.out.println("ID nao existe.");
        }

        Sale sale =  saleService.selectById(id);

        System.out.println("Data da venda: " + sale.getSaleDate());

        System.out.println("");
        System.out.println(String.format("%-20s - %-20s - %-20s - %-20s - %-20s",
                "ID Descritivo",
                "Id Venda",
                "Nome do produto",
                "Quantidade",
                "Preco"
        ));

        for (SaleContains saleContains : sale.getSaleContains()) {
            String IDProduct = "";
            String ProductName = "";
            if (saleContains.getProduct() != null) {
                IDProduct = saleContains.getProduct().getIdProduct().toString();
                ProductName = saleContains.getProduct().getName();
            }
            System.out.println(
                    String.format("%-20s - %-20s - %-20s - %-20s",
                            IDProduct,
                            ProductName,
                            saleContains.getQuantity(),
                            saleContains.getPrice()
                    )
            );
        }
    }

    @Override
    public void update() {
        System.out.println("Venda nao pode ser alterada apos fechamento.");
    }

    @Override
    public void deleteById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (saleService.checkId(id))
            saleService.deleteById(id);
        else System.out.println("ID nao existe.");
    }
}
