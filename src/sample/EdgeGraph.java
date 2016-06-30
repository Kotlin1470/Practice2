package sample;

import javafx.scene.shape.Line;

import java.util.Comparator;

/**
 *  В данном классе описана структура управления ребрами графа.
 *  @author NiPaYu
 */
public class EdgeGraph implements Comparable<EdgeGraph> {
    private VertexGraph vertexGraphStart; // вершина, из которой выходит ребро
    private VertexGraph vertexGraphEnd; // вершина, в которую входит ребро
    private double length; // длина ребра
    Line line; // визуализация

    /**
     * Конструктор по умолчанию
     */
    public EdgeGraph() {
        vertexGraphStart = null;
        vertexGraphEnd = null;
        length = 0;
    }

    /**
     * Конструктор с параметрами
     * @param vertex1 начальная вершина
     * @param vertex2 конечная вершина
     * @param length1 длина ребра
     * @param line1 визуализация
     */
    public EdgeGraph(VertexGraph vertex1, VertexGraph vertex2, double length1, Line line1) {
        vertexGraphStart = vertex1;
        vertexGraphEnd = vertex2;
        length = length1;
        line = line1;
    }

    public VertexGraph getVertexGraphStart() {
        return vertexGraphStart;
    }

    public void setVertexGraphStart(VertexGraph vertexGraphStart) {
        this.vertexGraphStart = vertexGraphStart;
    }

    public VertexGraph getVertexGraphEnd() {
        return vertexGraphEnd;
    }

    public void setVertexGraphEnd(VertexGraph vertexGraphEnd) {
        this.vertexGraphEnd = vertexGraphEnd;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Реализация своего compareTo
     * @param o объект данного класса
     * @return
     */
    @Override
    public int compareTo(EdgeGraph o) {
        int result;
        if (this.getVertexGraphStart().getNum() > (o.getVertexGraphStart().getNum())) {
            result = 1;
        } else if (this.getVertexGraphStart().getNum() == (o.getVertexGraphStart().getNum())) {
            result = 0;
        } else {
            result = -1;
        }
        if (result == 0) {
            if (this.getVertexGraphEnd().getNum() > (o.getVertexGraphEnd().getNum())) {
                result = 1;
            } else {
                result = -1;
            }
        }
        return result;
    }


    static class EdgeCompare implements Comparator<EdgeGraph> {

        public int compare(EdgeGraph e1, EdgeGraph e2) {
            return e1.compareTo(e2);
        }
    }
}


