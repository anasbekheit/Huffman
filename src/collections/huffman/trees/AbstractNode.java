package collections.huffman.trees;

public class AbstractNode<E> {
    public char data;
    protected E left;
    protected E right;

    public AbstractNode(char item) {
        data = item;
    }

    public E getLeft() {
        return left;
    }

    public void setLeft(E left) {
        this.left = left;
    }

    public E getRight() {
        return right;
    }

    public void setRight(E right) {
        this.right = right;
    }
}
