package sample;

/**
 * Класс вершин
 * @author NiPaYu
 */
public class VertexGraph {
    private static int number = 0;
    private int X;
    private int Y;
    private int num;

    /**
     * Конструктор по умолчанию
     */
    public VertexGraph() {
        number++;
        X = 0;
        Y = 0;
        num = number;
    }

    /**
     * Конструктор с параметрами
     * @param X1 координата вершины по оси Х
     * @param Y1 координата вершины по оси Y
     */
    public VertexGraph(int X1, int Y1) {
        number++;
        X = X1 * 39;
        Y = 390 - Y1 * 39;
        num = number;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getX() {
        return X;
    }

    public static int getNumber() {
        return number;
    }

    public int getY() {
        return Y;
    }

    public static void setNumber(int number) {
        VertexGraph.number = number;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

}
