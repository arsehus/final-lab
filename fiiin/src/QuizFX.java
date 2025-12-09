import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizFX extends Application {

    private QuizCore quiz;

    private Label questionLabel = new Label();
    private Label timerLabel = new Label("Время: 10");
    private ImageView imageView = new ImageView();
    private Button[] buttons = new Button[4];

    private Timer timer;
    private int timeLeft = 10;

    @Override
    public void start(Stage stage) {

        quiz = new QuizCore(FoodData.getQuestions());

        VBox root = new VBox(10);
        root.getChildren().addAll(questionLabel, imageView, timerLabel);

        for (int i = 0; i < 4; i++) {
            Button b = new Button();
            b.setMinWidth(200);
            int index = i;
            b.setOnAction(e -> handleAnswer(buttons[index].getText()));
            buttons[i] = b;
            root.getChildren().add(b);
        }

        loadQuestion();

        stage.setScene(new Scene(root, 420, 450));
        stage.setTitle("Food Quiz");
        stage.show();
    }

    private void loadQuestion() {

        stopTimer();

        if (quiz.isFinished()) {
            showResult();
            return;
        }

        QuizCore.Question q = quiz.getCurrentQuestion();

        questionLabel.setText("Из какой страны это блюдо?\n\n" + q.dish);

        imageView.setImage(new Image(q.imagePath));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        List<String> answers = quiz.getShuffledAnswers();
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(answers.get(i));
            buttons[i].setDisable(false);
        }

        startTimer();
    }

    private void handleAnswer(String selected) {
        stopTimer();
        quiz.answer(selected);
        loadQuestion();
    }

    private void startTimer() {
        timeLeft = 10;
        timerLabel.setText("Время: 10");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeLeft--;
                    timerLabel.setText("Время: " + timeLeft);
                    if (timeLeft <= 0) {
                        stopTimer();
                        quiz.answer(""); // тайм-аут = неправильно
                        loadQuestion();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void showResult() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Игра окончена");
        alert.setContentText(
                "Очки: " + quiz.getScore() +
                        "\nМаксимум: " + quiz.getTotalQuestions() * 5
        );
        alert.showAndWait();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
