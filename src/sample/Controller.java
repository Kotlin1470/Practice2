package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Alert;



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
    private Alert alert;
    private static int amountV = 0;
    private static int amountE = 0;
    private ArrayList<VertexGraph> listVertex = new ArrayList<>();
    private ArrayList<EdgeGraph> listEdge = new ArrayList<>();
    @FXML
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

    @FXML
    public void find_mst(){
        ArrayList<EdgeGraph> e = listEdge;
        ArrayList<VertexGraph> v = listVertex;
        if(e.isEmpty() || v.isEmpty())
            throw new IllegalArgumentException("No MST");
        double [][] m=new double[v.size()][v.size()];
        for (int i = 0; i < e.size(); ++i){
            m[e.get(i).getVertexGraphStart().getNum() - 1][e.get(i).getVertexGraphEnd().getNum() - 1]=e.get(i).getLength();
            m[e.get(i).getVertexGraphEnd().getNum() - 1][e.get(i).getVertexGraphStart().getNum() - 1]=e.get(i).getLength();
        }
        int inf=10000000;
        ArrayList<EdgeGraph> g = new ArrayList<>();
        boolean[] used = new boolean[m.length];
        double min;
        int x = inf,y = inf;
        used[0] = true;
        for (int i = 0;i < m.length - 1; ++i){
            min = inf;
            for (int j=0;j < m.length; ++j)
                if(used[j])
                    for (int k = 0;k < m.length; ++k)
                        if(m[j][k] < min && m[j][k] > 0){
                            min = m[j][k];
                            x = j;y = k;
                        }
            for (int k = 0;k < e.size(); ++k)
                if((e.get(k).getVertexGraphStart().getNum() - 1 == x && e.get(k).getVertexGraphEnd().getNum() - 1 == y) || (e.get(k).getVertexGraphStart().getNum() - 1 == y && e.get(k).getVertexGraphEnd().getNum() - 1 == x))
                    g.add(e.get(i));


            m[x][y] = 0;//x и y концы очередного добавленного ребра
            m[y][x] = 0;
            used[y] = true;//если где то здесь накапливать min получим вес мод'а, не знаю надо оно нам или нет
        }
        for(int i = 0; i < used.length; i++)
        {
            used[i] = false;
        }

        for (EdgeGraph edge: g) {
            paintLine(edge.getVertexGraphStart().getNum()-1, edge.getVertexGraphEnd().getNum()-1, 2);
        }
        g.clear();
    }

    @FXML
    public void handleAddEdge() {
        setDefaultLine(1);
        if (vertexGraph1.getText() == null || vertexGraph1.getText().length() == 0) {
            error("Пожалуйста, заполните поле <Начальная>");
        } else {
            if (vertexGraph2.getText() == null || vertexGraph2.getText().length() == 0) {
                error("Пожалуйста, заполните поле <Конечная>");
            } else {
                try {
                    int x = Integer.parseInt(vertexGraph1.getText());
                    int y = Integer.parseInt(vertexGraph2.getText());
                    if (x > amountV || y > amountV || x <= 0 || y <= 0) {
                        error("Не существет заданных вершин.");
                    }
                    if (x == y) {
                        error("Выберите различные вершины.");
                    } else {
                        paintLine(x-1,y-1,1);
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

    public void paintLine(int first, int second, int mod){
        VertexGraph vertexGraph1 = listVertex.get(first);
        VertexGraph vertexGraph2 = listVertex.get(second);
        double length = getLength(vertexGraph1.getX(), vertexGraph1.getY(), vertexGraph2.getX(), vertexGraph2.getY());
        amountE++;
        double horizontalComponent = vertexGraph2.getX() - vertexGraph1.getX();
        double verticalComponent = vertexGraph2.getY() - vertexGraph1.getY();
        double realLength = Math.sqrt(horizontalComponent * horizontalComponent +
                verticalComponent * verticalComponent);
        double angle1 = Math.acos(horizontalComponent / realLength); // Угол грани
        if (verticalComponent >= 0) angle1 = Math.PI * 2 - angle1;
        double shiftX = 10 * Math.cos(angle1);
        double shiftY = 10 * Math.sin(angle1);
        EdgeGraph edgeGraph = new EdgeGraph(listVertex.get(first), listVertex.get(second), length,
                new Line(vertexGraph1.getX() + shiftX,
                        vertexGraph1.getY() - shiftY + 10,
                        vertexGraph2.getX() - shiftX,
                        vertexGraph2.getY() + shiftY + 10));
        edgeGraph.line.setStroke(Color.BLACK);
        if(mod != 1)
            edgeGraph.line.setStroke(Color.RED);
        listEdge.add(edgeGraph);


        pane.getChildren().add(edgeGraph.line);

    }

    public double getLength(int x, int y, int x1, int y1) {
        double X = Math.pow(x1 - x, 2);
        double Y = Math.pow(y1 - y, 2);
        double length = (Math.sqrt(X + Y)) / 39;
        return length;
    }

    @FXML
    public void deleteGraph() {
        textArea.clear();
        vertexGraph2.clear();
        vertexGraph1.clear();
        graphX.clear();
        graphY.clear();
        pane.getChildren().clear();
        listEdge.clear();
        listVertex.clear();
        VertexGraph.setNumber(0);
    }

    @FXML
    public void displayCoordinates() {
        textArea.clear();
        for (VertexGraph ver : listVertex) {
            textArea.appendText("Координаты графа № " + ver.getNum() + "\n" + " X: " + ver.getX() / 39 + " Y: " + (390 - ver.getY()) / 39 + "\n");
        }
    }

    @FXML
    public void displayEdge() {
        textArea.clear();
        int i = 0;
        for (EdgeGraph edge : listEdge) {
            textArea.appendText("Ребро № " + ++i + "\n" + edge.getVertexGraphStart().getNum() + " -> " + edge.getVertexGraphEnd().getNum() + "\n" + "Длина ребра: " + edge.getLength() + "\n");
        }
    }

    public void error(String s) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Некорректный ввод");
        alert.setHeaderText(s);
        alert.showAndWait();
    }


}
