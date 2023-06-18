package com.example.project_i.Phieu_Nhap_Xuat;

import javafx.scene.control.Control;
import javafx.scene.control.TableCell;

public abstract class ObjectTableCell<C extends Control,T,S> extends TableCell<T, S>{

    private C component;

    public ObjectTableCell(C component) {
        this.component = component;
    }

    protected abstract void onEditStart(C component);
    protected abstract void onEditCommit();

    @Override
    public void startEdit() {
        super.startEdit();
        setGraphic(component);
        onEditStart(component);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

    @Override
    public void commitEdit(S newValue) {
        super.commitEdit(newValue);
        setGraphic(null);
        onEditCommit();
    }

    @Override
    protected void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null) {
            setText(null);
        }else {
            setText(item.toString());
        }
    }

}