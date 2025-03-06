package com.nhnacademy.brickbreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Paddle extends Rectangle implements Drawble,Movable {

    double dx;

    // 패들의 이동 상태
    boolean isMovingLeft = false;
    boolean isMovingRight = false;
    boolean isMoving = false;
    private Color color; // 패들의 색상
    private boolean isHit = false;

    @Override
    public void move() {
        if (isMovingLeft) {
            this.x -= dx; // 왼쪽으로 이동
        }
        if (isMovingRight) {
            this.x += dx; // 오른쪽으로 이동
        }
    }

    @Override
    //움직임 중지
    public void pause() {
        isMoving = false;
    }

    @Override
    //움직임 재개
    public void resume() {
        isMoving = true;
    }


    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return 0;
    }


    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dx = dy;
    }


    @Override
    public boolean isCollisionDetected(Shape other) {
        if (isHit) {
            return false;
        }

        double ballX = other.getX();
        double ballY = other.getY();

        double ballRadius = other.getMaxX() - ballX;



        boolean isHit =
                        ballX + ballRadius >= this.getMinX() &&
                        ballX - ballRadius <= this.getMaxX() &&
                        ballY + ballRadius >= this.getMinY() &&
                        ballY - ballRadius <= this.getMaxY();
        return isHit;
    }



    // 생성자
    public Paddle(double x, double y, double width, double height, double dx, Color color) {
        super(x, y, width, height);
        this.dx = dx;
        this.color = color;
    }

    // Getter와 Setter (필요 시 사용)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // 패들을 그리는 메서드
    public void draw(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.fillRect(x - width / 2, y - height / 2, width, height); // 중심을 기준으로 사각형 그리기
    }



    // 패들이 화면 경계를 벗어나지 않도록 제한
    public void checkBounds(double canvasWidth) {
        if (x - width / 2 < 0) { // 왼쪽 경계
            x = width / 2;
        } else if (x + width / 2 > canvasWidth) { // 오른쪽 경계
            x = canvasWidth - width / 2;
        }
    }
}
