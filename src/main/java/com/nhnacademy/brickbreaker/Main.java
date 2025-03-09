package com.nhnacademy.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {

    private AnimationTimer gameLoop; // 게임 루프를 위한 변수 선언

    //게임 상태 관리(종료, 점수, 이동)
    GameState gameState = new GameState();

    @Override
    public void start(Stage primaryStage) {

        // Canvas 생성: 게임 화면을 그릴 캔버스
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ball 생성: 공의 초기 위치와 속도 설정
        Ball ball = new Ball(400, 300, 10, 1, 3, Color.BEIGE);

        // Paddle 생성: 패들의 초기 위치, 크기, 속도 설정
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);

        // 점수 표시 라벨
        Label scoreLabel = new Label("score : "+ gameState.getScore() + "");
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // VBox에 점수 표시 추가
        VBox uiOverlay = new VBox();
        uiOverlay.getChildren().add(scoreLabel);
        uiOverlay.setStyle("-fx-alignment: top-center; -fx-padding: 10;");

        //벽 생성
        List<Shape> shapes = new ArrayList<>();
        int rows_w = 1;
        int cols_w = 3;
        double wallWidth = 100;
        double wallHeight = 20;
        double padding_w = 100;
        double startX_w = 160;
        double startY_w = 300;

//        // 벽을 초기 위치에 맞춰 생성
//        for (int row = 0; row < rows_w; row++) {
//            for (int col = 0; col < cols_w; col++) {
//                double x = startX_w + col * (wallWidth + padding_w);
//                double y = startY_w + row * (wallHeight + padding_w);
//                shapes.add(new Wall(x, y, wallWidth, wallHeight, Color.GREEN));
//            }
//        }

        // 벽돌 생성: 5개의 행과 10개의 열로 벽돌 배치
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 30;
        double startY = 50;

        // 벽돌을 초기 위치에 맞춰 생성
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                shapes.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
            }
        }

        // 게임 루프: 매 프레임마다 호출되는 메서드
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 화면 초기화: 배경을 검은색으로 채움
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // 게임 종료
                if (ball.getY() + ball.getRadius() >= canvas.getHeight()) {
                    showGameOverPopup(); // 팝업 출력
                    gameLoop.stop();
                }

                // Ball 업데이트 및 그리기
                ball.move(); // 공의 위치 업데이트
                ball.checkCollision(canvas.getWidth(), canvas.getHeight()); // 공이 경계를 벗어나지 않도록 확인
                ball.draw(gc); // 공 그리기


                // 객체 그리기 & 충돌 감지
                for (Iterator<Shape> iterator = shapes.iterator(); iterator.hasNext();) {
                    Shape shape = iterator.next();
                    if(shape instanceof Drawble) ((Drawble)shape).draw(gc);
                    {
                        if(shape instanceof Brick && ball.isCollisionDetected(shape)) {
                            ((Brick) shape).setDestroyed();
                            ball.pause();

                            //충돌지점
                            double collisionPointX = ball.getX();
                            double collisionPointY = ball.getMinY();

                            //<1>좌측 아래
                            if(collisionPointX >= shape.getMinX() && collisionPointX <= shape.getMinX() +(((Brick) shape).getWidth())*1/2 && ball.getMinY() <= shape.getMaxY() ) {
                                iterator.remove();
                                ball.resume();
                                System.out.println("좌측 아래");
                                break;
                                //우측 아래
                            }else if(collisionPointX <= shape.getMaxX() && collisionPointX >= shape.getMaxX() - (((Brick) shape).getWidth())*1/2 && ball.getMinY() <= shape.getMaxY()){
                                iterator.remove();
                                ball.resume();
                                System.out.println("우측 아래");
                                break;
                                //좌측 위
                            }else if(collisionPointX >= shape.getMinX() && collisionPointX <= shape.getMinX() +(((Brick) shape).getWidth())*1/2 && ball.getMaxY() >= shape.getMinY()){
                                iterator.remove();
                                ball.resume();
                                System.out.println("좌측 위");
                                break;
                                //우측 위
                            }else if(collisionPointX <= shape.getMaxX() && collisionPointX >= shape.getMaxX() - (((Brick) shape).getWidth())*1/2 && ball.getMaxY() >= shape.getMinY()){
                                iterator.remove();
                                ball.resume();
                                System.out.println("우측 위");
                                break;
                                //상/단 중앙(2)
                            }else if(shape.getMinX() + (((Brick) shape).getWidth())*1/2 <= collisionPointX && collisionPointX <= shape.getMaxX() - (((Brick) shape).getWidth())*1/2 && ball.getMinY() >= shape.getMaxY()){
                                iterator.remove();
                                ball.setDy(-ball.getSaved_dy());
                                System.out.println("상/하 중앙");
                                break;
                                //좌/우 중앙(2)
                            }else if(collisionPointX >= shape.getMinX() && ball.getMinY() <= shape.getMaxY() && ball.getMaxY() >= shape.getMinY()){
                                iterator.remove();
                                ball.setDx(-ball.getSaved_dx());
                                System.out.println("좌/우 중앙");
                            }else{// 그 외의 경우
                                iterator.remove();
                                ball.resume();
                                break;
                            }
                        }else if(shape instanceof Wall && ball.isCollisionDetected(shape)) {
                            ball.pause();
                            ball.setDx(0);
                            ball.setDy(0);
                            ball.resume();
                            break;
                        }
                    }
                }

                System.out.println(ball.getDy());

                //패들 이동
                if(paddle.isMoving){
                    paddle.move();
                }

                if(ball.isCollisionDetected(paddle)){
                    ball.setDx(-ball.getDx());
                    ball.setDy(-ball.getDy());
                }

                // Paddle 경계 확인 및 그리기: 패들이 화면 경계를 벗어나지 않도록 확인하고 그리기
                paddle.checkBounds(canvas.getWidth());
                paddle.draw(gc); // 패들 그리기
            }
        };

        gameLoop.start(); // 게임 루프 시작

        // 레이아웃 설정: StackPane을 사용하여 캔버스를 화면에 배치
        StackPane root = new StackPane();
        root.getChildren().addAll(canvas, uiOverlay);

        // Scene 설정: 키 이벤트를 처리하기 위한 Scene 생성
        Scene scene = new Scene(root, 800, 600);

        // 키보드 입력 처리: 왼쪽, 오른쪽 방향키를 눌렀을 때 패들 움직임 설정
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                paddle.isMovingLeft = true;
                paddle.isMoving = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                paddle.isMovingRight = true;
                paddle.isMoving = true;
            }
        });

        // 키보드 입력 해제 처리: 방향키를 떼면 패들이 더 이상 움직이지 않도록 설정
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                paddle.isMovingLeft = false;
            }else if(event.getCode() == KeyCode.RIGHT){
                paddle.isMovingRight = false;
            }
        });

        // Stage 설정: 창 제목과 Scene 설정
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show(); // 게임 창을 표시
    }

    // 팝업을 표시하고 종료하는 메서드
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Over! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args); // JavaFX 애플리케이션 시작
    }
}

