package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TicTacToeApp extends Application {

    private final Board board = new Board();
    private final Button[] cells = new Button[Board.SIZE * Board.SIZE];
    private Mark current = Mark.X;
    private boolean gameOver;

    @Override
    public void start(Stage stage) {
        Label status = new Label("X goes first");
        status.setFont(Font.font("System", FontWeight.SEMI_BOLD, 16));
        status.setMaxWidth(Double.MAX_VALUE);
        status.setAlignment(Pos.CENTER_LEFT);

        Button newGame = new Button("New game");
        newGame.setOnAction(e -> reset(status));

        HBox toolbar = new HBox(12, status, newRegion(), newGame);
        toolbar.getStyleClass().add("toolbar");
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(8, 16, 8, 16));

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(16));

        for (int i = 0; i < cells.length; i++) {
            int index = i;
            Button b = new Button();
            b.setMinSize(96, 96);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            b.setFont(Font.font("System", FontWeight.BOLD, 36));
            b.getStyleClass().add("cell");
            b.setOnAction(e -> onCellClick(index, status));
            cells[i] = b;
            grid.add(b, i % Board.SIZE, i / Board.SIZE);
        }

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(grid);
        root.getStyleClass().add("root-pane");

        Scene scene = new Scene(root, 380, 460);
        scene.getStylesheets().add(
                TicTacToeApp.class.getResource("/tictactoe/styles.css").toExternalForm());

        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMinHeight(400);
        stage.show();
    }

    private static Region newRegion() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }

    private void onCellClick(int index, Label status) {
        if (gameOver || !board.isEmpty(index)) {
            return;
        }
        board.place(index, current);
        cells[index].setText(markText(current));
        cells[index].getStyleClass().add(current == Mark.X ? "mark-x" : "mark-o");
        cells[index].setDisable(true);

        board.winner().ifPresentOrElse(
                w -> {
                    gameOver = true;
                    status.setText(w + " wins!");
                    disableEmptyCells();
                },
                () -> {
                    if (board.isFull()) {
                        gameOver = true;
                        status.setText("Draw");
                    } else {
                        current = current.other();
                        status.setText(current + "'s turn");
                    }
                });
    }

    private void disableEmptyCells() {
        for (int i = 0; i < cells.length; i++) {
            if (board.isEmpty(i)) {
                cells[i].setDisable(true);
            }
        }
    }

    private void reset(Label status) {
        board.clear();
        current = Mark.X;
        gameOver = false;
        for (Button b : cells) {
            b.setText("");
            b.setDisable(false);
            b.getStyleClass().removeAll("mark-x", "mark-o");
        }
        status.setText("X goes first");
    }

    private static String markText(Mark m) {
        return m == Mark.X ? "X" : "O";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
