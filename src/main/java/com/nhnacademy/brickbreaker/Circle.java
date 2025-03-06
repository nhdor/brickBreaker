package com.nhnacademy.brickbreaker;

public class Circle extends Shape{
    double radius;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.MaxX = this.x + radius;
        this.MaxY = this.y + radius;
        this.MinX = this.x - radius;
        this.MinY = this.y - radius;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getMinX() {
        return this.x-radius;
    }

    @Override
    public double getMinY() {
        return this.y-radius;
    }

    @Override
    public double getMaxX() {
        return this.x+radius;
    }

    @Override
    public double getMaxY() {
        return this.y+radius;
    }
}
