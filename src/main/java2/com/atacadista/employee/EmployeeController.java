package com.atacadista.employee;

import com.atacadista.establishment.Establishment;
import com.atacadista.establishment.EstablishmentController;
import com.atacadista.establishment.EstablishmentService;
import com.atacadista.util.ControllerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class EmployeeController extends ControllerOperation {
    @Autowired
    private EmployeeService service;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private EstablishmentController establishmentController;

    Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(){
        Employee employee = new Employee();

        if (scanner.hasNextLine()) scanner.nextLine();
        System.out.println("Nome do funcionario: ");
        String name = scanner.nextLine();
        employee.setName(name);

        if (scanner.hasNextLine()) scanner.nextLine();
        System.out.println("CPF do funcionario: ");
        String CPF = scanner.nextLine();
        employee.setCPF(CPF);

        establishmentController.selectAll();

        System.out.println("ID da empresa (0 sem): ");
        Long userInput = scanner.nextLong();
        if (userInput != 0)
            if (establishmentService.checkId(userInput))
                employee.setEstablishment(establishmentService.selectById(userInput));
            else System.out.println("ID nao existe. Cadastrando sem.");

        service.insert(employee);
    }

    @Override
    public void selectAll() {
        List<Employee> employees = service.selectAll();
        System.out.println(
                String.format("%-20s - %-20s - %-20s - %-20s",
                        "ID Funcionario",
                        "Nome do funcionario",
                        "CPF",
                        "ID da empresa"
                )
        );

        String IDEstabelecimento = "";
        for(Employee employee : employees) {
            if (employee.getEstablishment() != null)
                IDEstabelecimento = employee.getEstablishment().getIdEstablishment().toString();
            System.out.println(
                    String.format("%-20s - %-20s - %-20s - %-20s",
                            employee.getIdEmployee(),
                            employee.getName(),
                            employee.getCPF(),
                            IDEstabelecimento
                    )
            );
        }
    }

    @Override
    public void selectById() {
        selectAll();

        System.out.print("Digite o ID: ");
        Long id = scanner.nextLong();
        Employee employee =  service.selectById(id);

        if (!service.checkId(id)) {
            System.out.println("ID nao existe.");
            return;
        }

        System.out.println(
                String.format("%-20s - %-20s",
                        "Nome do funcionario",
                        "CPF",
                        "ID do estabelecimento"
                )
        );

        String IDEstabelecimento = "";
        if (employee.getEstablishment() != null)
            IDEstabelecimento = employee.getEstablishment().getIdEstablishment().toString();
        System.out.println("Nome: " + employee.getName());
        System.out.println("CPF: " + employee.getCPF());
        System.out.println("ID da empresa: " + IDEstabelecimento);
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

        Employee employeeBean = new Employee();

        System.out.println("Nome: ");
        String name = scanner.nextLine();
        employeeBean.setName(name);

        System.out.println("CPF: ");
        String CPF = scanner.nextLine();
        employeeBean.setCPF(CPF);

        establishmentController.selectAll();
        System.out.println("ID da empresa (0 sem): ");
        Long userInput = scanner.nextLong();
        if (userInput != 0)
            if (establishmentService.checkId(id))
                employeeBean.setEstablishment(establishmentService.selectById(userInput));
            else System.out.println("ID nao existe. Cadastrando sem.");

        service.update(id, employeeBean);
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
