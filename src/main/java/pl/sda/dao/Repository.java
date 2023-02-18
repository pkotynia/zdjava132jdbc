package pl.sda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/*
* Defines set of common CRUD operations
* this is generic interface - every implementing class needs to provide concrete type in place of T
* in our case it will be Department and Worker
* */
public interface Repository<T> {

     T save (T t) throws SQLException;

     List<T> getAll() throws SQLException;

     Optional<T> getById(int id) throws SQLException;

     T update (T t) throws SQLException;

     boolean removeById(int id);

     boolean removeAll();




}
