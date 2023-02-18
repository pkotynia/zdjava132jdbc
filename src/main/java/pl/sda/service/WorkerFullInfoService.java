package pl.sda.service;

import pl.sda.dao.DepartmentDao;
import pl.sda.dao.WorkerDao;

import java.sql.SQLException;
import java.util.Optional;

// Purpose of this class is to return worker information including department name
public class WorkerFullInfoService {

    private final WorkerDao workerDao;
    private final DepartmentDao departmentDao;

    public WorkerFullInfoService(WorkerDao workerDao, DepartmentDao departmentDao) {
        this.workerDao = workerDao;
        this.departmentDao = departmentDao;
    }

    public Optional<WorkerWithDepartment> presentFullWorkerDataById(int id) throws SQLException {
        return workerDao.getById(id)
                .map(worker -> {
                    try {
                        return new WorkerWithDepartment(
                                worker.getWorkerId(),
                                worker.getFirstName(),
                                worker.getLastName(),
                                worker.getHireDate(),
                                departmentDao.getById(worker.getDepartmentId())
                                        .map(department -> department.getDepartmentName())
                                        .orElse("Unknown Department")
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });


    }
}



