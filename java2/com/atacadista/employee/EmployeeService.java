package com.atacadista.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // CREATE
    public Employee insert(Employee employee){
        return repository.save(employee);
    }

    // SELECT
    public List<Employee> selectAll(){
        return repository.findAll();
    }

    public Employee selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Funcionario com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Employee update(Long id, Employee employee)  {
        Employee employeeBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario com esse ID nao existe: " + id));

        employeeBean.setName(employee.getName());
        employeeBean.setCPF(employee.getCPF());

        return repository.save(employeeBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
