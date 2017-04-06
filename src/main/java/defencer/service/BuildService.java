package defencer.service;

import defencer.model.Instructor;

import java.sql.SQLException;
import java.util.List;

/**
 * @param <T> is given entity.
 * @author Igor Gnes on 3/18/17.
 */
public interface BuildService<T> {

    int createEntity(T entity) throws SQLException;

    default List<T> getAll() throws SQLException {
        return null;
    }

    List<T> getTwelfth() throws SQLException;

    List<T> getByValue(String value, String like) throws SQLException;

    void deleteEntity(String like) throws SQLException;

    void updateEntity(String like, String name, String phone,
                      String email, String description,
                      String occupation, String projectName) throws SQLException;

    default  List<Instructor> getInstructorsName() throws SQLException {
        return null;
    }
}
