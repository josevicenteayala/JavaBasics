package org.example.binary.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class BinaryTreeTest {
    @Test
    public void testPrintInorder() {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        String expected = "4 2 5 1 3 ";
        assertEquals(expected, getOutput(() -> tree.printInorder(tree.root)));
    }

    @Test
    public void testPrintPreorder() {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        String expected = "1 2 4 5 3 ";
        assertEquals(expected, getOutput(() -> tree.printPreorder(tree.root)));
    }

    @Test
    public void testPrintPostorder() {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        String expected = "4 5 2 3 1 ";
        assertEquals(expected, getOutput(() -> tree.printPostorder(tree.root)));
    }

    private static String getOutput(Runnable r) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        r.run();

        System.setOut(originalOut);
        return outContent.toString();
    }
}