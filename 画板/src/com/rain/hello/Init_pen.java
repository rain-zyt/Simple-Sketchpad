package com.rain.hello;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Init_pen {
    public void initDraw(GraphicsContext gc, GraphicsContext gc_effect) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc_effect.setStroke(Color.BLACK);
        gc_effect.setLineWidth(2);
    }

    public void set_color(GraphicsContext gc, GraphicsContext gc_effect,Color c1)
    {
        gc.setStroke(c1);
        gc_effect.setStroke(c1);
    }

    public  void set_thickness(GraphicsContext gc, GraphicsContext gc_effect,Number a)
    {
        gc.setLineWidth(a.doubleValue());
        gc_effect.setLineWidth(a.doubleValue());
    }
}
