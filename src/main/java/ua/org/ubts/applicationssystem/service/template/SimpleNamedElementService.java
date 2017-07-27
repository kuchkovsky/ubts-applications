package ua.org.ubts.applicationssystem.service.template;

public interface SimpleNamedElementService<E, I> extends BasicService<E, I>, ExtendedElementOperationsService<E> {

    E findByName(String name);

}
