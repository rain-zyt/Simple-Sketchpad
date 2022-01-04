package com.rain.hello;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;


public class set_type {
    Draw_line drawLine;
    Draw_round drawCircle;
    Draw_rectangle drawRectangle;

    public set_type(StackPane root, Canvas canvas_effect, GraphicsContext gc, GraphicsContext gc_effect) {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "线",
                        "圆",
                        "矩形"
                );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        root.getChildren().addAll(comboBox);
        comboBox.getSelectionModel().selectFirst();
        StackPane.setAlignment(comboBox, Pos.BOTTOM_LEFT);

        drawLine = new Draw_line(canvas_effect, gc);
        drawCircle = new Draw_round(canvas_effect, gc, gc_effect);
        drawRectangle = new Draw_rectangle(canvas_effect, gc, gc_effect);

        drawLine.takeAction();
        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (drawLine.isActive)
                        drawLine.removeAction();
                    if (drawCircle.isActive)
                        drawCircle.removeAction();
                    if (drawRectangle.isActive)
                        drawRectangle.removeAction();
                    switch (newValue) {
                        case "线":
                            drawLine.takeAction();
                            break;
                        case "圆":
                            drawCircle.takeAction();
                            break;
                        case "矩形":
                            drawRectangle.takeAction();
                            break;
                    }
                }
        );

        ObservableList<String> options_type =
                FXCollections.observableArrayList(
                        "实线",
                        "虚线",
                        "点线"
                );
        ComboBox<String> comboBox_type = new ComboBox<>(options_type);
        comboBox_type.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        root.getChildren().addAll(comboBox_type);
        comboBox_type.getSelectionModel().selectFirst();
        StackPane.setAlignment(comboBox_type, Pos.BOTTOM_LEFT);
        comboBox_type.setTranslateX(300);

        comboBox_type.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue) {
                        case "实线":
                            drawLine.line_type(0,gc);
                            break;
                        case "虚线":
                            drawLine.line_type(1,gc);
                            break;
                        case "点线":
                            drawLine.line_type(2,gc);
                            break;
                    }
                });
    }
}
