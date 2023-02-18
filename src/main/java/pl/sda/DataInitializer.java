package pl.sda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitializer {

    private Connection connection;

    public DataInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initData() throws SQLException {
        //todo change to Prepared statement
        String dropWorkerQuery = "DROP TABLE IF EXISTS WORKER";
        PreparedStatement dropWorkerStatement = connection.prepareStatement(dropWorkerQuery);
        dropWorkerStatement.execute();

        String dropQuery = "DROP TABLE IF EXISTS DEPARTMENT";
        PreparedStatement dropTableStatement = connection.prepareStatement(dropQuery);
        dropTableStatement.executeUpdate();

        //todo handle affected rows
        String createDepartmentQuery = """
                    CREATE TABLE IF NOT EXISTS DEPARTMENT (
                        department_id int primary key,
                        department_name varchar(50)
                    );""";
        PreparedStatement createTableStatement = connection.prepareStatement(createDepartmentQuery);
        createTableStatement.executeUpdate();

        String createWorkerQuery = """
                CREATE TABLE IF NOT EXISTS WORKER (
                    worker_id int primary key,
                    first_name varchar(50) NOT NULL,
                    last_name varchar(50) NOT NULL,
                    hire_date date,
                    department_id int,
                    FOREIGN KEY (department_id) REFERENCES DEPARTMENT(department_id)
                );
                """;

        PreparedStatement createWorker = connection.prepareStatement(createWorkerQuery);
        createWorker.execute();
    }
}
