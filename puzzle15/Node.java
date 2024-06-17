package puzzle15;

class Node implements Comparable<Node> {
    int[][] board;
    int g;
    int h;
    Node parent;

    public Node(int[][] board, int g, int h, Node parent) {
        this.board = board;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    public int getF() {
        return g + h;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }
}
