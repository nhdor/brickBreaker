package com.nhnacademy.brickbreaker;

//이동 가능한 객체 - Ball , Paddle

public interface Movable {


    // 단위 거리만큼 이동
    void move();

    // 단위시간당 X축 상으로의 이동량을 반환
    double getDx();
    // 단위시간당 Y축 상으로의 이동량을 반환
    double getDy();

    // 단위시간당 X축 상으로의 이동량을 설정
    void setDx(double dx);
    // 단위시간당 Y축 상으로의 이동량을 설정
    void setDy(double dy);

    // 움직임을 중지. move()를 호출하더라도 이동하지 않습니다.
    void pause();
    // 움직임을 재개. move()를 호출시 단위 시간 이동량만큼 이동.
    void resume();

    // 주어진 객체와 충돌이 감지되었는지 확인
    boolean isCollisionDetected(Shape other);


}
