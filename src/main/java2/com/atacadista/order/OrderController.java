package com.atacadista.order;

import com.atacadista.employee.Employee;
import com.atacadista.employee.EmployeeController;
import com.atacadista.employee.EmployeeService;
import com.atacadista.establishment.Establishment;
import com.atacadista.establishment.EstablishmentController;
import com.atacadista.establishment.EstablishmentService;
import com.atacadista.product.Product;
import com.atacadista.product.ProductController;
import com.atacadista.product.ProductService;
import com.atacadista.relations.OrderContains;
import com.atacadista.relations.Stock;
import com.atacadista.sale.Sale;
import com.atacadista.supplier.Supplier;
import com.atacadista.supplier.SupplierController;
import com.atacadista.supplier.SupplierService;
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
public class OrderController extends ControllerOperation {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierController supplierController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private EstablishmentController establishmentController;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Order orderBean = new Order();
        Product chosenProduct;


        if (scanner.hasNextLine()) scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Digite a data da venda (ano-mes-dia): ");
        String localDate = scanner.nextLine();
        LocalDate orderDate = LocalDate.parse(localDate, formatter);
        orderBean.setOrderDate(orderDate);

        System.out.print("Feita por qual funcionario (ID): ");
        employeeController.selectAll();
        System.out.println("\n\nInput: ");
        Long EmployeeID = scanner.nextLong();
        if (!employeeService.checkId(EmployeeID)) {
            System.out.println("ID nao existe.");
            return;
        }
        Employee employee = employeeService.selectById(EmployeeID);
        orderBean.askedBy(employee);

        System.out.print("Feita para qual estabelecimento (ID): ");
        establishmentController.selectAll();
        System.out.println("\n\nInput: ");
        Long EstablishmentID = scanner.nextLong();
        if (!establishmentService.checkId(EstablishmentID)) {
            System.out.println("ID nao existe.");
            return;
        }
        Establishment establishment = establishmentService.selectById(EstablishmentID);
        orderBean.askedTo(establishment);

        System.out.println("Pedida para qual fornecedor (ID): ");
        supplierController.selectAll();
        System.out.println("\n\nInput: ");
        Long SupplierID = scanner.nextLong();
        if (!supplierService.checkId(SupplierID)) {
            System.out.println("ID nao existe.");
            return;
        }
        Supplier supplier = supplierService.selectById(SupplierID);
        orderBean.askedFor(supplier);

        // Loop de produtos
        Integer userInput;
        Float userInputFloat;
        OrderContains orderContains = new OrderContains();
        while (true) {
            System.out.println("Continuar cadastrando produtos?\n\n1 - Sim.\n2 - Não.");
            userInput = scanner.nextInt();
            if (userInput.equals(2))
                break;
            List<Product> availableProducts = productService.selectAll();

            productService.printList(availableProducts);

            System.out.println("ID do produto: ");
            chosenProduct = productService.selectById(scanner.nextLong());
            orderContains.setProduct(chosenProduct);

            System.out.println("Quantidade: ");
            userInput = scanner.nextInt();
            orderContains.setQuantity(userInput);

            System.out.println("Preco: ");
            userInputFloat = scanner.nextFloat();
            orderContains.setPrice(userInputFloat);

            orderBean.contains(orderContains);
            Order savedOrder = orderService.insert(orderBean);
        }
    }

    @Override
    public void selectAll() {
        List<Order> orders = orderService.selectAll();
        System.out.println("---- Pedidos ----");
        System.out.println(String.format("%-20s - %-20s",
                "ID Pedido",
                "Data do pedido"
        ));
        orderService.printList(orders);
    }

    @Override
    public void selectById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();
        if (!orderService.checkId(id)) {
            System.out.println("ID nao existe.");
        }

        Order order =  orderService.selectById(id);

        System.out.println("Data do pedido: " + order.getOrderDate());

        System.out.println("");
        System.out.println(String.format("%-20s - %-20s - %-20s - %-20s - %-20s",
                "ID Descritivo",
                "Id Produto",
                "Nome do produto",
                "Quantidade",
                "Preco"
        ));

        for (OrderContains orderContains : order.getOrderContains()) {
            String IDProduct = "";
            String ProductName = "";
            if (orderContains.getProduct() != null) {
                IDProduct = orderContains.getProduct().getIdProduct().toString();
                ProductName = orderContains.getProduct().getName();
            }
            System.out.println(
                    String.format("%-20s - %-20s - %-20s - %-20s",
                            IDProduct,
                            ProductName,
                            orderContains.getQuantity(),
                            orderContains.getPrice()
                    )
            );
        }
    }

    @Override
    public void update() {
        System.out.println("Pedido nao pode ser atualizado apos fechamento.");
    }

    @Override
    public void deleteById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (orderService.checkId(id))
            orderService.deleteById(id);
        else System.out.println("ID nao existe.");
    }
}
