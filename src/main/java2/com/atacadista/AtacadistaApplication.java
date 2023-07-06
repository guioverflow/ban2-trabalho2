package com.atacadista;

import com.atacadista.employee.Employee;
import com.atacadista.employee.EmployeeController;
import com.atacadista.establishment.Establishment;
import com.atacadista.establishment.EstablishmentController;
import com.atacadista.establishment.EstablishmentResponseDTO;
import com.atacadista.establishment.EstablishmentService;
import com.atacadista.order.Order;
import com.atacadista.order.OrderController;
import com.atacadista.order.OrderService;
import com.atacadista.product.ProductController;
import com.atacadista.relations.OrderContains;
import com.atacadista.relations.SaleContains;
import com.atacadista.relations.Stock;
import com.atacadista.sale.Sale;
import com.atacadista.sale.SaleController;
import com.atacadista.sale.SaleService;
import com.atacadista.supplier.Supplier;
import com.atacadista.supplier.SupplierController;
import com.atacadista.util.ControllerOperation;
import com.atacadista.util.MenuInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Fonte:
// https://stackoverflow.com/questions/56358562/receive-input-from-command-line-with-spring-boot
// https://www.youtube.com/watch?v=LjtcrWkC0-E
// https://www.youtube.com/watch?v=lUVureR5GqI

// Requisitos:
// Generated ID precisa ser do tipo Long
// Não pode instanciar Controller, Service e Repository (Injeção de dependência)
// Mapear com Node e Property


@SpringBootApplication
@RequiredArgsConstructor
public class AtacadistaApplication implements CommandLineRunner {

	private MenuInformation menu = new MenuInformation();

	@Autowired
	private EstablishmentController establishmentController;

	@Autowired
	private EstablishmentService establishmentService;

	@Autowired
	private EmployeeController employeeController;

	@Autowired
	private SupplierController supplierController;

	@Autowired
	private ProductController productController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private OrderService orderService;

	@Autowired
	private SaleController saleController;

	@Autowired
	private SaleService saleService;

	Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(AtacadistaApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		int operation;
		int tabela;
		boolean isRunning = true;
		boolean backToTheStart = false;
		ControllerOperation controller = null;

		while (isRunning) {
			operation = -1;
			tabela = -1;
			backToTheStart = false;

			while (operation < 0 || operation > 6) {
				operation = menu.menuInicial();
				if (operation == 0) {
					System.out.println("Encerrando execucao");
					isRunning = false;
				} else if (operation < 0 || operation > 6) {
					System.out.println("Escolha invalida");
				} else break;
			}

			if (!isRunning) break;

			menu.clearTerminal();

			if (operation != 6) {
				while (tabela < 0 || tabela > 6) {
					tabela = menu.menuTabelas();
					if (tabela == 0) {
						System.out.println("Voltando...");
						backToTheStart = true;
					} else if (tabela < 0 || tabela > 8) {
						System.out.println("Escolha invalida");
					} else break;
				}
			}

			if (backToTheStart) continue;

			if (operation != 6) {
				switch (tabela) {
					case 1 -> controller = establishmentController;
					case 2 -> controller = employeeController;
					case 3 -> controller = supplierController;
					case 4 -> controller = productController;
					case 5 -> controller = orderController;
					case 6 -> controller = saleController;
				}
			}

			assert controller != null;
			switch (operation) {
				case 1 -> controller.insert();
				case 2 -> controller.selectById();
				case 3 -> controller.selectAll();
				case 4 -> controller.update();
				case 5 -> controller.deleteById();
				case 6 -> menuRelatorios();
			}

			System.out.println("\nPressione qualquer tecla para continuar.");

			menu.pauseTerminal();
			menu.clearTerminal();
		}
	}

	public void menuRelatorios() {
		System.out.println("""
                Informe a opção desejada:
                
                1 - Itens em estoque por estabelecimento
                2 - Vendas por funcionario
                3 - Pedidos por fornecedor""");
		Integer choice = scanner.nextInt();
		switch (choice) {
			case 1 -> availableProductsPerEstablishment();
			case 2 -> salesPerEmployee();
			case 3 -> salesPerSupplier();
		}

	}


	public void availableProductsPerEstablishment() {
		List<Establishment> establishmentList = establishmentService.selectAll();
		System.out.println("");
		System.out.println(String.format("%-20s - %-20s - %-20s",
				"ID Estabelecimento",
				"CNPJ",
				"Itens em estoque"
		));

		String IDEstablishment = "";
		Integer sum = 0;
		for (Establishment establishment : establishmentList) {
			if (establishment.getIdEstablishment() != null) {
				IDEstablishment = establishment.getIdEstablishment().toString();
				for (Stock stock : establishment.getStockProducts())
					sum += stock.getQuantity();
			}
			System.out.println(
					String.format("%-20s - %-20s - %-20s",
							IDEstablishment,
							establishment.getCNPJ(),
							sum
					)
			);
		}
	}

	private void salesPerEmployee() {
		List<Sale> saleList = saleService.selectAll();
		Map<String, Float> salesPerEmployee = new HashMap<>();

		for (Sale sale : saleList) {
			Employee employee = sale.getEmployee();
			List<SaleContains> saleContainsList = sale.getSaleContains();

			if (employee != null && saleContainsList != null) {
				String employeeName = employee.getName();

				for (SaleContains saleContains : saleContainsList) {
					Float valorVenda = saleContains.getPrice() * saleContains.getQuantity();

					if (valorVenda != null) {
						salesPerEmployee.put(employeeName,
								salesPerEmployee.getOrDefault(employeeName, 0f) + valorVenda);
					}
				}
			}

			System.out.println(
					String.format("%-20s - %-20s",
							"Funcionario",
							"Arrecadado"
					)
			);
			for (Map.Entry<String, Float> entry : salesPerEmployee.entrySet()) {
				String employeeName = entry.getKey();
				Float totalSales = entry.getValue();
				System.out.println(
						String.format("%-20s - %-20s",
								employeeName,
								totalSales
						)
				);
			}
		}
	}


	private void salesPerSupplier() {
		List<Order> orderList = orderService.selectAll();
		Map<String, Float> totalSalesPerSupplier = new HashMap<>();

		for (Order order : orderList) {
			Supplier supplier = order.getSupplier();
			List<OrderContains> orderContainsList = order.getOrderContains();

			if (supplier != null && orderContainsList != null) {
				String supplierName = supplier.getName();

				for (OrderContains orderContains : orderContainsList) {
					Float totalPrice = orderContains.getPrice() * orderContains.getQuantity();

					totalSalesPerSupplier.put(supplierName,
							totalSalesPerSupplier.getOrDefault(supplierName, 0f) + totalPrice);
				}
			}
		}

		System.out.println(
				String.format("%-20s - %-20s",
						"Fornecedor",
						"Investido"
				)
		);
		for (Map.Entry<String, Float> entry : totalSalesPerSupplier.entrySet()) {
			String supplierName = entry.getKey();
			Float totalSales = entry.getValue();
			System.out.println(
					String.format("%-20s - %-20s",
							supplierName,
							totalSales
					)
			);
		}
	}
}
