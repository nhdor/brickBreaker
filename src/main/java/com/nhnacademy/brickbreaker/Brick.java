package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Drawble {

    private Color color; // 벽돌의 색상
    private boolean isDestroyed; // 벽돌이 파괴되었는지 여부

    // 생성자
    public Brick(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
    }

    // 벽돌을 그리는 메서드
    public void draw(GraphicsContext gc) {
        if (!isDestroyed) {
            gc.setFill(color);
            gc.fillRect(x - width/2, y - height/2, width, height); // 벽돌 그리기
        }
    }


    public void setDestroyed() {
        this.isDestroyed = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
