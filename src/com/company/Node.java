package com.company;

public class Node implements  Comparable<Node>{
    final char character;
    final int frequency;
    final Node leftChild,rightChild;

    Node(char character, int frequency, Node leftChild, Node rightChild) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild=leftChild;
        this.rightChild= rightChild;
    }
    boolean isLeaf(){
        return this.leftChild==null && this.rightChild==null;
    }


    @Override
    public int compareTo(final Node o) {
        final int result=Integer.compare(this.frequency,o.frequency);

        return result!=0? result:Integer.compare(this.character,o.character);
    }
}
