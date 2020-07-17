package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.Random;

public class GridPaneTest extends Application {

    ToggleButton[][] matrix;

    @Override
    public void start(Stage primaryStage) throws Exception {
        matrix = new ToggleButton[5][5];
        //AnchorPane root = new AnchorPane();
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        final int row = 5;
        for (int i = 0; i < 6; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / row);
            grid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / row);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Random rand = new Random();
                if (i != j) {
                    int             r   = rand.nextInt(5);
                    JFXToggleButton btn = new JFXToggleButton();
                    btn.setPrefSize(400, 400);
                    btn.setText("Visitante");
                    matrix[i][j] = btn;
                    int finalI = i;
                    int finalJ = j;
                    matrix[i][j].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (matrix[finalI][finalJ].isSelected()) {
                                matrix[finalI][finalJ].setText("Local");
                                matrix[finalJ][finalI].setSelected(false);
                                matrix[finalJ][finalI].setText("Visitante");
                            } else {
                                matrix[finalI][finalJ].setText("Visitante");
                                matrix[finalJ][finalI].setSelected(true);
                                matrix[finalJ][finalI].setText("Local");
                            }
                        }
                    });
                    grid.add(matrix[i][j], i, j);
                }
            }
        }
        //root.getChildren().add(grid);
        JFXButton button = new JFXButton("CLICK ME");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        if (matrix[i][j].isSelected()) {
                            System.out.println("Boton en la posicion " + i + "," + j + " está seleccionado");
                        }
                    }
                }
            }
        });
        grid.add(button, 6, 6);

        primaryStage.setScene(new Scene(grid, 678, 829));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
