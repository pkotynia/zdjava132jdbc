package pl.sda.dto;

import java.time.LocalDate;

public class Worker {
    private int workerId;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private int departmentId;

    public Worker(int workerId, String firstName, String lastName, LocalDate hireDate, int departmentId) {
        this.workerId = workerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.departmentId = departmentId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerId=" + workerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate=" + hireDate +
                ", departmentId=" + departmentId +
                '}';
    }
}
