package com.nhnacademy.brickbreaker;

public class Rectangle extends Shape {

    double width;
    double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.MaxX = x+width/2;
        this.MinX = x-width/2;
        this.MaxY = y+height/2;
        this.MinY = y-height/2;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getMinX() {
        return x-width;
    }

    @Override
    public double getMinY() {
        return y-height;
    }

    @Override
    public double getMaxX() {
        return x+width;
    }

    @Override
    public double getMaxY() {
        return y+height;
    }
}
