package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Shape {
    protected double x; // 공의 현재 x 좌표
    protected double y; // 공의 현재 y 좌표
    protected double MaxX;
    protected double MaxY;
    protected double MinX;
    protected double MinY;


    // 생성자
    public Shape(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.MaxX = x + width;
        this.MaxY = y + height;
        this.MinX = x - width;
        this.MinY = y - height;
    }

    public Shape() {

    }

    // Getter와 Setter (필요 시 사용)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMinX(){
        return MinX;
    }

    public double getMinY(){
        return MinY;
    }

    public double getMaxX(){
        return MaxX;
    }
    public double getMaxY(){
        return MaxY;
    }
}

