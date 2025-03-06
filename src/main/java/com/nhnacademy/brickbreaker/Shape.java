package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Shape
{
    protected double x; // 공의 현재 x 좌표
    protected double y; // 공의 현재 y 좌표
    protected double MaxX;
    protected double MaxY;
    protected double MinX;
    protected double MinY;



    // Getter와 Setter (필요 시 사용)
    public abstract double getX();

    public abstract double getY();

    public abstract double getMinX();
    public abstract double getMinY();
    public abstract double getMaxX();
    public abstract double getMaxY();
}

