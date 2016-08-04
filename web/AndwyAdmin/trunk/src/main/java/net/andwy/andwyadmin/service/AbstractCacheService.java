package net.andwy.andwyadmin.service;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public abstract class AbstractCacheService<T> extends AbstractService<T> {
    protected static long CACHE_SECONDS = 60;
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Override
    protected abstract List<T> fetchListFromDB(Long key);
    protected void clearCache() {
        this.listCache.invalidateAll();
        this.entityCache.invalidateAll();
    }
    @Override
    protected abstract CrudRepository<T, Long> getDao();
    private LoadingCache<Long, List<T>> listCache = CacheBuilder.newBuilder().maximumSize(1000)//
            //.refreshAfterWrite(CACHE_SECONDS, TimeUnit.SECONDS)//
            .expireAfterAccess(CACHE_SECONDS, TimeUnit.SECONDS)//
            .build(new CacheLoader<Long, List<T>>() {//
                @Override
                public List<T> load(Long key) throws Exception {
                    //LOGGER.info("Fetch list from database");
                    return fetchListFromDB(key);
                }
            });//
    private LoadingCache<Long, T> entityCache = CacheBuilder.newBuilder().maximumSize(1000)//
            .refreshAfterWrite(CACHE_SECONDS, TimeUnit.SECONDS)//
            .expireAfterAccess(CACHE_SECONDS * 2, TimeUnit.SECONDS)//
            .build(new CacheLoader<Long, T>() {//
                @Override
                public T load(Long key) throws Exception {
                    //LOGGER.info("Fetch list from database");
                    return get(key);
                }
            });
    public T getCached(Long id) {
        try {
            return entityCache.get(id);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
    protected List<T> getCacheList(Long key) {
        try {
            return listCache.get(key);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
    @Override
    @Transactional(readOnly = false)
    public T save(T entity) {
        T t = super.save(entity);
        clearCache();
        return t;
    }
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        super.delete(id);
        clearCache();
    }
}
