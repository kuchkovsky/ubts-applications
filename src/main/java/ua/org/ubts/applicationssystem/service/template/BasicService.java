package ua.org.ubts.applicationssystem.service.template;

import java.util.List;

public interface BasicService<E, I> {

    E findById(I id);

    List<E> findAll();

    void save(E element);

    void deleteById(I id);

    void deleteAll();

}
