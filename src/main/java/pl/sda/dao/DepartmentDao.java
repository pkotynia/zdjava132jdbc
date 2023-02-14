package pl.sda.dao;

import pl.sda.model.DepartmentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Repository<DepartmentDto>{

    private final Connection connection;

    public DepartmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) throws SQLException {
        String createQuery ="INSERT INTO DEPARTMENT (department_id, department_name) VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(createQuery);

        statement.setInt(1, departmentDto.getDepartmentId());
        statement.setString(2, departmentDto.getDepartmentName());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            System.out.println("Unable to save Department " + departmentDto.getDepartmentName());
        }
        // just to have returned object
        return departmentDto;
    }

    @Override
    public List<DepartmentDto> getAll() throws SQLException {
        String getAllDepartmentsQuery = "SELECT * FROM department";

        Statement getAllDepartmentsStatement = connection.createStatement();

        ResultSet resultSet = getAllDepartmentsStatement.executeQuery(getAllDepartmentsQuery);

        List<DepartmentDto> departments = new ArrayList<>();
        while (resultSet.next()) {
            DepartmentDto departmentDto = new DepartmentDto(
                    resultSet.getInt("department_id"),
                    resultSet.getString("department_name")
            );
            departments.add(departmentDto);
        }
        return departments;
    }

    @Override
    public Optional<DepartmentDto> getById(int id) throws SQLException {
        String getByIdQuery = "SELECT * FROM department where department_id=?";
        PreparedStatement statement = connection.prepareStatement(getByIdQuery);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            DepartmentDto department = new DepartmentDto(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            return Optional.of(department);
        }
        return Optional.empty();
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) {
        return null;
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
