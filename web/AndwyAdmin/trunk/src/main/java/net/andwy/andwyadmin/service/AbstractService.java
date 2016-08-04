package net.andwy.andwyadmin.service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected List<T> fetchListFromDB(Long key) {
        return new ArrayList<T>();
    };
    protected abstract CrudRepository<T, Long> getDao();
    @Transactional(readOnly = false)
    public T save(T entity) {
        T t = getDao().save(entity);
        return t;
    }
    public T get(Long id) {
        return getDao().findOne(id);
    }
    public List<T> getAll() {
        return (List<T>) getDao().findAll();
    }
    @Transactional(readOnly = false)
    public void delete(Long id) {
        getDao().delete(id);
    }
}
