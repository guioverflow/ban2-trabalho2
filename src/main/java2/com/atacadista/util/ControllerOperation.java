package com.atacadista.util;

import com.atacadista.employee.Employee;
import com.atacadista.employee.EmployeeRequestDTO;
import com.atacadista.employee.EmployeeResponseDTO;
import com.atacadista.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

public abstract class ControllerOperation {

        Scanner scanner = new Scanner(System.in);
        public abstract void insert();
        public abstract void selectAll();
        public abstract void selectById();
        public abstract void deleteById();
        public abstract void update();
}
