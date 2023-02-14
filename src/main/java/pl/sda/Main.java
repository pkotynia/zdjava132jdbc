package pl.sda;

import pl.sda.dao.DepartmentDao;
import pl.sda.model.DepartmentDto;

import java.sql.*;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //Creating jdbc connection to MySql db, providing user and password.
        //Connection and DriverManager are part of java.sql package
        //In order to make it work we need to add mysql-connector-java dependency in the pom file
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/jdbc", "root", "Start$123")) {

            String dropQuery = "DROP TABLE department";
            Statement dropTableStatement = connection.createStatement();
            dropTableStatement.execute(dropQuery);

            String createDepartmentQuery = """
                    CREATE TABLE IF NOT EXISTS department (
                        department_id int primary key,
                        department_name varchar(50)
                    );""";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createDepartmentQuery);

            String insertDepartmentQuery = """
                    INSERT INTO DEPARTMENT (department_id, department_name)
                    VALUES(?,?)
                    """;

            DepartmentDao departmentDao = new DepartmentDao(connection);
            departmentDao.save(new DepartmentDto(1, "Ministry of Magic"));
            departmentDao.save(new DepartmentDto(2, "Ministry of Mysteries"));

            System.out.println(departmentDao.getAll());

            Optional<DepartmentDto> optionalDepartment = departmentDao.getById(5);
            if (optionalDepartment.isPresent()) {
                System.out.println(optionalDepartment.get());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}