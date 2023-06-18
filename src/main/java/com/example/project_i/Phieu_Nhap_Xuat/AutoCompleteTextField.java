package com.example.project_i.Phieu_Nhap_Xuat;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AutoCompleteTextField<S> extends TextField {

    private final ObjectProperty<S> selectedObject = new SimpleObjectProperty<>();
    private SortedSet<S> sortedEntries;
    private ObservableList<S> filteredEntries;
    private ContextMenu entriesPopup = new ContextMenu();
    private String textOccurenceStyle = "-fx-font-weight: bold; " + "-fx-fill: black;";

    public AutoCompleteTextField() {
        super();
        textProperty().addListener((obs, old, niu) -> {
            if (getText() == null || getText().length() == 0) {
                getFilteredEntries().clear();
                getFilteredEntries().addAll(getSortedEntries());
                entriesPopup.hide();
            } else {
                LinkedList<S> searchResult = new LinkedList<>();
                Pattern pattern = Pattern.compile(".*" + niu + ".*", Pattern.CASE_INSENSITIVE);
                for (S entry : getSortedEntries()) {
                    Matcher matcher = pattern.matcher(entry.toString());
                    if (matcher.matches()) {
                        searchResult.add(entry);
                    }
                }
                if (!getSortedEntries().isEmpty()) {
                    getFilteredEntries().clear();
                    getFilteredEntries().addAll(searchResult);
                    populatePopup(searchResult, niu);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });
        focusedProperty().addListener((obs, old, niu) -> {
            entriesPopup.hide();
        });
    }

    private void populatePopup(List<S> searchResult, String text) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int count = Math.min(searchResult.size(), 8);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i).toString();
            final S itemObject = searchResult.get(i);
            int occurence = result.toLowerCase().indexOf(text.toLowerCase());
            if (occurence < 0) {
                continue;
            }
            // Part before occurence (might be empty)
            Text pre = new Text(result.substring(0, occurence));
            // Part of (first) occurence
            Text in = new Text(result.substring(occurence, occurence + text.length()));
            in.setStyle(getTextOccurenceStyle());
            // Part after occurence
            Text post = new Text(result.substring(occurence + text.length(), result.length()));

            TextFlow entryFlow = new TextFlow(pre, in, post);

            CustomMenuItem item = new CustomMenuItem(entryFlow, true);
            item.setOnAction((ActionEvent actionEvent) -> {
                selectedObject.set(itemObject);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    public void setEntries(SortedSet<S> entrySet) {
        this.sortedEntries = (entrySet == null ? new TreeSet<>() : entrySet);
        setFilteredEntries(FXCollections.observableArrayList(sortedEntries));
    }

    public ObservableList<S> getFilteredEntries() {
        return filteredEntries;
    }

    public void setFilteredEntries(ObservableList<S> filteredEntries) {
        this.filteredEntries = filteredEntries;
    }

    public SortedSet<S> getSortedEntries() {
        return this.sortedEntries;
    }

    public ObjectProperty<S> selectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(S object) {
        selectedObject.set(object);
        Platform.runLater(() -> {
            entriesPopup.hide();
        });
    }

    public S getSelectedObject() {
        return selectedObject.get();
    }

    public ContextMenu getEntryMenu() {
        return entriesPopup;
    }

    public String getTextOccurenceStyle() {
        return textOccurenceStyle;
    }

    public void setTextOccurenceStyle(String textOccurenceStyle) {
        this.textOccurenceStyle = textOccurenceStyle;
    }

}