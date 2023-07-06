package com.atacadista.employee;

public record EmployeeResponseDTO(Long idEmployee, String CPF, String name) {
    public EmployeeResponseDTO(Employee employeeBean) {
        this(
                employeeBean.getIdEmployee(),
                employeeBean.getCPF(),
                employeeBean.getName()
        );
    }
}
