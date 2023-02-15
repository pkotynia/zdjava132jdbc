package pl.sda.dao;

import pl.sda.dto.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Repository<Department>{

    private final Connection connection;

    public DepartmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department save(Department department) throws SQLException {
        String createQuery ="INSERT INTO DEPARTMENT (department_id, department_name) VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(createQuery);

        statement.setInt(1, department.getDepartmentId());
        statement.setString(2, department.getDepartmentName());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            System.out.println("Unable to save Department " + department.getDepartmentName());
        }
        // just to have returned object
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
        String getByIdQuery = "SELECT * FROM department where department_id=?";
        PreparedStatement statement = connection.prepareStatement(getByIdQuery);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Department department = new Department(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            return Optional.of(department);
        }
        return Optional.empty();
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
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }
}
