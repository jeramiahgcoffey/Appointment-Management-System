package db;

import java.util.List;
import java.util.Optional;

/** Data access object layer for CRUD operations. */
public interface DAO<T> {
    /**
     * Get object by id.
     * @param id Unique id associated with the object
     * @return The object associated with the id param
     */
    Optional<T> get(int id);

    /**
     * Get all objects.
     * @return A List of all objects
     */
    List<T> getAll();

    /**
     * Persist changes to the object.
     * @param t The object to save
     */
    void save(T t);

    /**
     * Make changes to the object's data.
     * @param t The object to change
     * @param params Values to be changed
     */
    void update(T t, String[] params);

    /**
     * Delete the object.
     * @param t The object to delete
     */
    void delete(T t);
}
