package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

import static sun.management.Agent.error;

public class Controller {
    @FXML
    private TextField graphX;
    @FXML
    private TextField graphY;
    @FXML
    private TextField vertexGraph1;
    @FXML
    private TextField vertexGraph2;
    @FXML
    private Pane pane;
    @FXML
    private TextField startVertex;
    @FXML
    private TextField endVertex;
    @FXML
    private TextField numDemVer;
    @FXML
    private TextArea textArea;
    private static int amountV = 0;
    private static int amountE = 0;
    private ArrayList<VertexGraph> listVertex = new ArrayList<>();
    private ArrayList<EdgeGraph> listEdge = new ArrayList<>();

    public void handleAddVertexGraph() {
        setDefaultLine(1);
        if (graphX.getText() == null || graphX.getText().length() == 0) {
            error("Пожалуйста, заполните поле <X>");
        } else {
            if (graphY.getText() == null || graphY.getText().length() == 0) {
                error("Пожалуйста, заполните поле <Y>");
            } else {
                try {
                    int x = Integer.parseInt(graphX.getText());
                    int y = Integer.parseInt(graphY.getText());
                    if (x > 15 || x < 0 || y > 10 || y < 0) {
                        error("Граф может быть не отображен. Пожалуйста, введите координаты в соответствии заданной координатной осью.");
                    } else {
                        VertexGraph vertexGraph = new VertexGraph(x, y);
                        listVertex.add(vertexGraph);
                        amountV++;
                        Circle circle = new Circle(vertexGraph.getX(), vertexGraph.getY() + 10, 10);
                        circle.setFill(Color.AQUA);
                        Label label = new Label(Integer.toString(vertexGraph.getNum()));
                        label.setTextFill(Color.DARKBLUE);
                        label.setLayoutX(vertexGraph.getX() - 5);
                        label.setLayoutY(vertexGraph.getY() + 5);
                        pane.getChildren().add(circle);
                        pane.getChildren().add(label);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }

    public void setDefaultLine(int width) {
        for (EdgeGraph edge : listEdge) {
            pane.getChildren().remove(edge.line);
            edge.line.setStrokeWidth(width);
            pane.getChildren().add(edge.line);
        }
    }

}
