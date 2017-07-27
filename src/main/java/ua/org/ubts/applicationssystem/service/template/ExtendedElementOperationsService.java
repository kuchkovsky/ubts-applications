package ua.org.ubts.applicationssystem.service.template;

public interface ExtendedElementOperationsService<E> {

    E findByData(E element);

    boolean isExist(E element);

}
