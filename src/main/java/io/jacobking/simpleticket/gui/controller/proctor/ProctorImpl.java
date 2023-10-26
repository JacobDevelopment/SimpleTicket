package io.jacobking.simpleticket.gui.controller.proctor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ProctorImpl<T, M> implements IProctor<T> {

    protected final ObservableList<M> modelList;

    public ProctorImpl() {
        this.modelList = FXCollections.observableArrayList();
        fetch();
    }

    public ObservableList<M> getModelList() {
        return modelList;
    }
}
