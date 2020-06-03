package cz.muni.fi.pa165.dmbk.machinerental.service;


import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service providing dozer mapping
 *
 * @author Peter Baltazarovič
 */
@Service
public interface BeanMappingService {

    /**
     * Provide mapping of lists between DTO and entities
     * @param objects collection of objects to map
     * @param mapToClass class of desires mapping
     * @param <T> desired type
     * @return list of mapped objects
     */
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Provide mapping between DTO and entity
     * @param u source object to map
     * @param mapToClass  class of desired mapping
     * @param <T> desired type
     * @return mapped object
     */
    public  <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Getter of mapper object
     * @return mapper
     */
    public Mapper getMapper();
}
