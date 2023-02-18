package pl.sda.dao;

import pl.sda.dto.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* Dao - Data Access Object class provides operations allowing to Save, Get, Update, Remove
* implements Repository - Interface that defines common methods
* Dao objects operates on DTO (Data Transport Object) objects that are java representation of the DB table
* */
public class DepartmentDao implements Repository<Department>{

    // Connection object keeping reference to open DB connection
    // used to create Statement objects
    private final Connection connection;

    public DepartmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department save(Department department) throws SQLException {
        // SQL query with dwo parameters in form of ?
        //parameters are used to customize query base on provided department object
        String createQuery ="INSERT INTO DEPARTMENT (department_id, department_name) VALUES(?,?)";

        //We are always using PreparedStatement that allows to replace ? parameters with values
        PreparedStatement statement = connection.prepareStatement(createQuery);

        //base on parameterIndex (index of ? starting form 1) replace parameter with value
        statement.setInt(1, department.getDepartmentId());
        statement.setString(2, department.getDepartmentName());
        // after this operations query will look like this:
        //"INSERT INTO DEPARTMENT (department_id, department_name) VALUES(department.getDepartmentId(),department.getDepartmentName())";

        // executeUpdate will return int describing if there were any rows affected by Update operation
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            System.out.println("Unable to save Department " + department.getDepartmentName());
        }
        // this is just to satisfy API, normally we should probably fetch data form DB to be sure it was saved
        return department;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        String getAllDepartmentsQuery = "SELECT * FROM department";

        Statement getAllDepartmentsStatement = connection.createStatement();

        ResultSet resultSet = getAllDepartmentsStatement.executeQuery(getAllDepartmentsQuery);

        List<Department> departments = new ArrayList<>();
        while (resultSet.next()) {
            Department department = new Department(
                    resultSet.getInt("department_id"),
                    resultSet.getString("department_name")
            );
            departments.add(department);
        }
        return departments;
    }

    @Override
    public Optional<Department> getById(int id) throws SQLException {
        //SQL query with one parameter ?
        String getByIdQuery = "SELECT * FROM department where department_id=?";
        PreparedStatement statement = connection.prepareStatement(getByIdQuery);

        //setting query parameter to value id passed to method as an argument
        statement.setInt(1, id);

        //execute Query will return ResultSet that represent the rows form DB matching the query form line 71
        /* for example for query SELECT * FROM department where department_id=1 we can have something like:
        * +---------------+-------------------+
        | department_id | department_name   |
        +---------------+-------------------+
        |             1 | Ministry of Magic | --> resultStet.next() will point to this row
        +---------------+-------------------+
        * */
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            //base on resultSet (represents values fetched from DB) we will create Department objects (DTO)
            Department department = new Department(
                    // form result set we can get the DB row values by DB columnIndex or by column name
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            //wrapping in optional to indicate that value may be missing
            //null is not good way to represent lack of value because it just means that object was not initiated.
            return Optional.of(department);
        } else {
            // if we are not able to find any item we will return Optional.empty
            return Optional.empty();
        }
    }

    @Override
    public Department update(Department department) throws SQLException {
        String updateQuery = "UPDATE DEPARTMENT SET department_name=? where department_id=?";

        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setString(1, department.getDepartmentName());
        statement.setInt(2, department.getDepartmentId());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("department not updated");
        }

        return department;
    }

    @Override
    public boolean removeById(int id) {
        //todo homework - implement method
        return false;
    }

    @Override
    public boolean removeAll() {
        //todo homework - implement method
        return false;
    }
}
