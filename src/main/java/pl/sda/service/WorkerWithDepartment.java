package pl.sda.service;

import java.time.LocalDate;

public record WorkerWithDepartment(int workerId,
                                   String firstName,
                                   String lastName,
                                   LocalDate hireDate,
                                   String departmentName) {

    public void hello(){
        System.out.println("hello");
    }
}
