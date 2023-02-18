package pl.sda;

import pl.sda.dao.DepartmentDao;
import pl.sda.dao.WorkerDao;
import pl.sda.dto.Department;
import pl.sda.dto.Worker;
import pl.sda.service.WorkerFullInfoService;
import pl.sda.service.WorkerWithDepartment;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        //Creating jdbc connection to MySql db, providing user and password.
        //Connection and DriverManager are part of java.sql package
        //In order to make it work we need to add mysql-connector-java dependency in the pom file
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/jdbc", "root", "Start$123")) {

            // drop and reinitialize tables
            DataInitializer dataInitializer = new DataInitializer(connection);
            dataInitializer.initData();

            //creating Dao object for CRUD operations on department table
            DepartmentDao departmentDao = new DepartmentDao(connection);
            departmentDao.save(new Department(1, "Ministry of Magic"));

            Department ministryOfMysteries = new Department(2, "Ministry of Mysteries");
            departmentDao.save(ministryOfMysteries);

            System.out.println(departmentDao.getAll());

            Optional<Department> optionalDepartment = departmentDao.getById(5);

            System.out.println(optionalDepartment);

            ministryOfMysteries.setDepartmentName("Ministry of Something else");
            departmentDao.update(ministryOfMysteries);

            System.out.println( departmentDao.getById(ministryOfMysteries.getDepartmentId()));

            WorkerDao workerDao = new WorkerDao(connection);
            Worker worker = new Worker(1, "Petter", "Gibbons", LocalDate.of(2022, 1, 1), 2);
            workerDao.save(worker);

            System.out.println(workerDao.getById(1));

            WorkerFullInfoService workerFullInfoService = new WorkerFullInfoService(workerDao, departmentDao);
            System.out.println(workerFullInfoService.presentFullWorkerDataById(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}