package com.nhnacademy.brickbreaker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private AnimationTimer gameLoop; // 게임 루프를 위한 변수 선언

    // 패들 이동 상태를 추적하는 변수
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean gameFinished = false; // 게임 완료 여부를 나타내는 플래그

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성: 게임 화면을 그릴 캔버스
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ball 생성: 공의 초기 위치와 속도 설정
        Ball ball = new Ball(400, 300, 10, 3, 3, Color.RED);

        // Paddle 생성: 패들의 초기 위치, 크기, 속도 설정
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);

        // 벽돌 생성: 5개의 행과 10개의 열로 벽돌 배치
        List<Brick> bricks = new ArrayList<>();
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        // 벽돌을 초기 위치에 맞춰 생성
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                bricks.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
            }
        }


        // 게임 루프: 매 프레임마다 호출되는 메서드
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 화면 초기화: 배경을 검은색으로 채움
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // 조건 체크: 예를 들어, 특정 프레임에서 게임 완료 처리


                if (ball.getY() + ball.getRadius() >= canvas.getHeight()) { // 300프레임 이후 게임 종료 조건
                    gameFinished = true; // 게임 완료 플래그 설정
                    showGameOverPopup(); // 팝업 출력
                    gameLoop.stop();
                }


                // Paddle 움직임 처리: 사용자가 키를 눌러 패들을 이동
                if (moveLeft) {
                    paddle.moveLeft(); // 왼쪽으로 이동
                }
                if (moveRight) {
                    paddle.moveRight(); // 오른쪽으로 이동
                }

                // Ball 업데이트 및 그리기: 공의 상태를 갱신하고 그리기
                ball.update(); // 공의 위치 업데이트
                ball.checkCollision(canvas.getWidth(), canvas.getHeight()); // 공이 경계를 벗어나지 않도록 확인
                ball.draw(gc); // 공 그리기

                // 벽돌 그리기 및 충돌 처리: 벽돌과 공의 충돌을 체크하고 벽돌을 그리기
                for (Brick brick : bricks) {
                    if (brick.checkCollision(ball)) {
                        ball.setDy(-ball.getDy()); // 충돌 시 공의 y 방향 반전
                    }
                    brick.draw(gc); // 벽돌 그리기
                }

                if(paddle.checkCollision(ball)){
                    System.out.println(paddle.checkCollision(ball));
                    ball.setDx(-ball.getDx());
                    ball.setDy(-ball.getDy()); // 충돌 시 공의 y 방향 반전

                };

                // Paddle 경계 확인 및 그리기: 패들이 화면 경계를 벗어나지 않도록 확인하고 그리기
                paddle.checkBounds(canvas.getWidth());
                paddle.draw(gc); // 패들 그리기

            }
        };
        gameLoop.start(); // 게임 루프 시작

        // 레이아웃 설정: StackPane을 사용하여 캔버스를 화면에 배치
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Scene 설정: 키 이벤트를 처리하기 위한 Scene 생성
        Scene scene = new Scene(root, 800, 600);

        // 키보드 입력 처리: 왼쪽, 오른쪽 방향키를 눌렀을 때 패들 움직임 설정
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = true; // 왼쪽 방향키를 누르면 moveLeft를 true로 설정
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = true; // 오른쪽 방향키를 누르면 moveRight를 true로 설정
            }
        });

        // 키보드 입력 해제 처리: 방향키를 떼면 패들이 더 이상 움직이지 않도록 설정
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false; // 왼쪽 방향키를 떼면 moveLeft를 false로 설정
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false; // 오른쪽 방향키를 떼면 moveRight를 false로 설정
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
