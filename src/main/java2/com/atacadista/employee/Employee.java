package com.atacadista.employee;

import com.atacadista.establishment.Establishment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Funcionario")
public class Employee {
    @Id
    @GeneratedValue
    private Long idEmployee;

    @Property("nome")
    private String name;

    @Property("cpf")
    private String CPF;

    @Relationship(type = "EMPREGADO_EM", direction = Relationship.Direction.OUTGOING)
    private Establishment establishment;

    public void employedBy(Establishment establishment) {
        this.establishment = establishment;
    }

    public Employee(String name, String cpf) {
        this.name = name;
        this.CPF = CPF;
    }

    public List<Object> toList() {
        List<Object> employeeList = new ArrayList<>();
        employeeList.add(this.getIdEmployee());
        employeeList.add(this.getCPF());
        employeeList.add(this.getName());

        return employeeList;
    }
}
