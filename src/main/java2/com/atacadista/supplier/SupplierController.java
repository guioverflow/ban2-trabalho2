package com.atacadista.supplier;

import com.atacadista.supplier.Supplier;
import com.atacadista.util.ControllerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class SupplierController extends ControllerOperation {
    @Autowired
    private SupplierService service;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Supplier supplier = new Supplier();

        System.out.println("Nome do fornecedor: ");
        String name = scanner.nextLine();
        supplier.setName(name);

        System.out.println("Telefone do fornecedor: ");
        String phone = scanner.nextLine();
        supplier.setPhone(phone);

        System.out.println("Email do fornecedor: ");
        String email = scanner.nextLine();
        supplier.setPhone(email);

        System.out.println("CNPJ do fornecedor: ");
        String CNPJ = scanner.nextLine();
        supplier.setPhone(CNPJ);

        service.insert(supplier);
    }

    @Override
    public void selectAll() {
        List<Supplier> suppliers = service.selectAll();
        for(Supplier supplier : suppliers) {
            System.out.println(
                    String.format("%-20s - %-20s - %-20s",
                            supplier.getIdSupplier(),
                            supplier.getName(),
                            supplier.getPhone()
                    )
            );
        }
    }

    @Override
    public void selectById() {
        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();

        if (!service.checkId(id)) {
            System.out.println("ID nao existe.\n");
            return;
        }

        Supplier supplier =  service.selectById(id);

        System.out.println("Nome: " + supplier.getName());
        System.out.println("Telefone: " + supplier.getPhone());
        System.out.println("Email: " + supplier.getEmail());
        System.out.println("CNPJ: " + supplier.getCNPJ());
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

        Supplier supplierBean = new Supplier();

        System.out.println("Nome do fornecedor: ");
        String name = scanner.nextLine();
        supplierBean.setName(name);

        System.out.println("Telefone do fornecedor: ");
        String phone = scanner.nextLine();
        supplierBean.setPhone(phone);

        System.out.println("Email do fornecedor: ");
        String email = scanner.nextLine();
        supplierBean.setPhone(email);

        System.out.println("CNPJ do fornecedor: ");
        String CNPJ = scanner.nextLine();
        supplierBean.setPhone(CNPJ);

        service.update(id, supplierBean);
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
