package com.rain.hello;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Draw_rectangle {
    double x1, y1, x2, y2;
    Canvas canvas_effect;
    GraphicsContext gc, gc_effect;
    Boolean isActive;

    public Draw_rectangle(Canvas canvas_effect, GraphicsContext gc, GraphicsContext gc_effect) {
        this.canvas_effect = canvas_effect;
        this.gc = gc;
        this.gc_effect = gc_effect;
        isActive = false;
    }

    private final EventHandler<MouseEvent> pressed = (event) -> {
        x1 = event.getX();
        y1 = event.getY();
    };

    private final EventHandler<MouseEvent> dragged = (event) -> {
        x2 = event.getX();
        y2 = event.getY();
        gc_effect.clearRect(0, 0, canvas_effect.getWidth(), canvas_effect.getHeight());
        if (x2 > x1 && y2 > y1) {
            gc_effect.strokeRect(x1, y1, x2 - x1, y2 - y1);
        } else if (x2 > x1 && y2 < y1) {
            gc_effect.strokeRect(x1, y1, x2 - x1, Math.abs(y2 - y1));
        } else if (x2 < x1 && y2 > y1) {
            gc_effect.strokeRect(x1, y1, Math.abs(x1 - x2), y2 - y1);
        } else if (x2 < x1 && y2 < y1) {
            gc_effect.strokeRect(x1, y1, Math.abs(x1 - x2), Math.abs(y2 - y1));
        }
    };

    private final EventHandler<MouseEvent> released = (event) -> {
        gc_effect.clearRect(0, 0, canvas_effect.getWidth(), canvas_effect.getHeight());
        if (x2 > x1 && y2 > y1) {
            gc.strokeRect(x1, y1, x2 - x1, y2 - y1);
        } else if (x2 > x1 && y2 < y1) {
            gc.strokeRect(x1, y1, x2 - x1, Math.abs(y2 - y1));
        } else if (x2 < x1 && y2 > y1) {
            gc.strokeRect(x1, y1, Math.abs(x1 - x2), y2 - y1);
        } else if (x2 < x1 && y2 < y1) {
            gc.strokeRect(x1, y1, Math.abs(x1 - x2), Math.abs(y2 - y1));
        }
    };

    public void takeAction() {
        canvas_effect.addEventHandler(MouseEvent.MOUSE_PRESSED, pressed);
        canvas_effect.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragged);
        canvas_effect.addEventHandler(MouseEvent.MOUSE_RELEASED, released);
        isActive = true;
    }

    public void removeAction() {
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressed);
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragged);
        canvas_effect.removeEventHandler(MouseEvent.MOUSE_RELEASED, released);
        isActive = false;
    }
}
