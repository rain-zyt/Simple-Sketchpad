package com.rain.hello;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.*;

import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.awt.image.BufferedImage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //退出选择框
        Platform.setImplicitExit(false);    //取消退出按钮原功能
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  //创建退出警告弹窗
            alert.setTitle("是否退出");
            alert.setHeaderText(null);
            alert.setContentText("是否要退出程序");
            //警告窗选择结果判断
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()  == ButtonType.OK){
                Platform.exit();
            }
        });

        StackPane root = new StackPane();
        //画板控件canvas
        Canvas picture1 = new Canvas(1920, 1080);
        Canvas picture2 = new Canvas(1920, 1080);
        picture2.setStyle("-fx-background-color: rgba(255, 255, 255, 0)");
        GraphicsContext g1 = picture1.getGraphicsContext2D();
        GraphicsContext g2 = picture2.getGraphicsContext2D();

        //画笔参数初始化对象
        Init_pen initDraw = new Init_pen();
        initDraw.initDraw(g1, g2);

        //颜色选择
        ColorPicker color_picker = new ColorPicker(Color.BLACK);
        color_picker.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        color_picker.setOnAction((actionEvent)->{
            initDraw.set_color(g1,g1,color_picker.getValue());
        });

        //画笔粗细
        Slider slider = new Slider();
        slider.setMax(5.0);
        slider.setMin(1.0);
        slider.setValue(2.0);
        slider.setPrefWidth(250);
        slider.setMinWidth(250);
        slider.setMaxWidth(250);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        //slider.setSnapToTicks(true);
        //slider.setMajorTickUnit(10);
        //slider.setMinorTickCount(5);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initDraw.set_thickness(g1,g2,newValue);
            }
        });

        //清屏按钮
        Button button_clear = new Button("清屏");
        button_clear.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        button_clear.setOnAction(event -> g1.clearRect(0, 0, picture2.getWidth(), picture2.getHeight()));

        //保存图片
        Button button_save = new Button("保存");
        button_save.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        button_save.setOnAction(event -> {
            WritableImage writableImage = picture1.snapshot(null,null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage,null);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("保存为PNG图片");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","jpg"));
            File file = fileChooser.showSaveDialog(picture1.getScene().getWindow());

            if(file != null){
                try {
                    ImageIO.write(bufferedImage, "PNG", file);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        //打开图片
        Button button_open = new Button("打开");
        button_open.setStyle("-fx-pref-width: 100px; -fx-pref-height: 30px");
        button_open.setOnAction(actionEvent -> {


            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("打开图片");
            fileChooser1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG","jpg"));
            File select_file = fileChooser1.showOpenDialog(primaryStage);

            if(select_file != null) {
                try {
                    Image a = new Image(select_file.getAbsolutePath());
                    g1.drawImage(a, 500, 500);
                    g2.drawImage(a, 500, 500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //布局
        root.setStyle("-fx-background-color: #ffffff");
        root.getChildren().addAll(picture1, picture2, button_clear,color_picker,slider,button_save,button_open);
        root.setAlignment(Pos.CENTER);
        StackPane.setAlignment(picture1, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(picture2, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(color_picker, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(slider, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(button_clear, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(button_save, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(button_open, Pos.BOTTOM_LEFT);
        color_picker.setTranslateX(150);
        slider.setTranslateX(450);
        button_clear.setTranslateX(800);
        button_save.setTranslateX(950);
        button_open.setTranslateX(1100);
        new set_type(root, picture2, g1, g2);
        Scene scene = new Scene(root);
        primaryStage.setTitle("易画图");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
