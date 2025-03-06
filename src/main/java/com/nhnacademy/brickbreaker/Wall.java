package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends Rectangle implements Drawble{
    Color color;

    public Wall(double x, double y, double width, double height, Color color) {
        super(x,y,width,height);
        this.color = color;
    }

    // 벽을 그리는 메서드
    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(x - width/2, y - height/2, width, height); // 벽돌 그리기
    }

}
