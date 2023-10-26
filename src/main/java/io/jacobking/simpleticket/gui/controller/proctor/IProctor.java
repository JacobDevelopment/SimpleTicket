package io.jacobking.simpleticket.gui.controller.proctor;


public interface IProctor<T> {

    void fetch();

    void create(T t);

    T insert(T t);

    void delete(int id);

}
