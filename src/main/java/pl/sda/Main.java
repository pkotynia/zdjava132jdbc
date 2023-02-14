package pl.sda;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try {
            //Creating jdbc connection to MySql db, providing user and password.
            //Connection and DriverManager are part of java.sql package
            //In order to make it work we need to add mysql-connector-java dependency in the pom file
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc", "root", "Start$123"
            );

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
            PreparedStatement insertDepartmentStatement = connection.prepareStatement(insertDepartmentQuery);
            insertDepartmentStatement.setInt(1, 1);
            insertDepartmentStatement.setString(2, "Ministry of Magic");

            insertDepartmentStatement.executeUpdate();

            String getAllDepartmentsQuery = "SELECT * FROM department";
            Statement getAllDepartmentsStatement = connection.createStatement();

            ResultSet resultSet = getAllDepartmentsStatement.executeQuery(getAllDepartmentsQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                System.out.println("Department " + name + " with id " + id);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}