package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Drawble,Movable {

    private Color color; // 공의 색상
    private double dx;
    private double dy;
    private double saved_dx;
    private double saved_dy;
    private boolean collided = false;


    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x,y,radius);
        this.dx = dx;
        this.dy = dy;
        this.saved_dx = dx;
        this.saved_dy = dy;
        this.color = color;
    }

    // 공을 그리는 메서드
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }

    // 공이 화면 경계와 충돌했는지 확인 및 속도 반전
    public void checkCollision(double canvasWidth, double canvasHeight) {
        // 좌우 경계 충돌
        if (x - radius <= 0 || x + radius >= canvasWidth) {
            dx = -dx; // x축 속도 반전
        }
        // 상하 경계 충돌
        if (y - radius <= 0) {
            dy = -dy; // y축 속도 반전
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void move() {
        x += dx; // x축 위치 업데이트
        y += dy; // y축 위치 업데이트
    }


    //주어진 '객체'와 충돌했는지 감지
    @Override
    public boolean isCollisionDetected(Shape other) {

        double ballX = this.getX();
        double ballY = this.getY();
        double ballRadius = this.getRadius();

        // 벽(브릭)의 위치와 크기
        double brickX = other.getX();
        double brickY = other.getY();
        double brickWidth = other.getMaxX()-other.getMinX();
        double brickHeight = other.getMaxY()-other.getMinY();

        // 공의 가장 가까운 위치를 구하기 (벽의 범위 안에서)
        double closestX = Math.max(brickX, Math.min(ballX, brickX + brickWidth));  // 공의 X가 벽의 왼쪽 또는 오른쪽 경계 내에 오게끔 조정
        double closestY = Math.max(brickY, Math.min(ballY, brickY + brickHeight));  // 공의 Y가 벽의 위쪽 또는 아래쪽 경계 내에 오게끔 조정

        // 충돌 지점과 공의 중심 간의 거리 계산
        double deltaX = ballX - closestX;
        double deltaY = ballY - closestY;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);


        boolean collision = distance < ballRadius;

        if (collision) {
            System.out.println("공이 벽에 충돌했습니다! 충돌 지점: (" + closestX + ", " + closestY + ")");
            this.setX(closestX);
            this.setY(closestY);
        }
        return collision;
    }


    public double getDy() {
        return dy;
    }

    public double getDx() {
        return dx;
    }


    //사용 X
    @Override
    public void pause() {
        this.saved_dx = this.dx;  // 현재 속도를 저장
        this.saved_dy = this.dy;
        this.dx = 0;  // 속도 0으로 설정
        this.dy = 0;
    }


    //사용 X
    @Override
    public void resume() {
        if(this.dx == 0 && this.dy == 0){
            this.dx = -this.saved_dx;
            this.dy = -this.saved_dy;
        }
    }

    public void setCollided() {
        collided = true;
    }

    public boolean isCollided() {
        return collided;
    }
}
