package cs250.hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BinaryTree implements TreeStructure {
    private class TreeNode {
        Integer key;
        Long timestamp;
        TreeNode left, right;

        public TreeNode(Integer item, Long time) {
            key = item;
            timestamp = time;
            left = right = null;
        }
    }

    TreeNode root;

    @Override
    public void insert(Integer num) {
        root = insertRec(root, num, System.nanoTime());
    }

    private TreeNode insertRec(TreeNode root, Integer key, Long time) {
        if (root == null) {
            root = new TreeNode(key, time);
            return root;
        }
        if (key < root.key)
            root.left = insertRec(root.left, key, time);
        else if (key > root.key)
            root.right = insertRec(root.right, key, time);
        return root;
    }

    @Override
    public Boolean remove(Integer num) {
        root = removeRec(root, num);
        return root != null;
    }

    private TreeNode removeRec(TreeNode root, Integer key) {
        if (root == null) return null;
        if (key < root.key) {
            root.left = removeRec(root.left, key);
        } else if (key > root.key) {
            root.right = removeRec(root.right, key);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode temp = findMin(root.right);
            root.key = temp.key;
            root.timestamp = temp.timestamp;
            root.right = removeRec(root.right, temp.key);
        }
        return root;
    }

    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    @Override
    public Long get(Integer num) {
        TreeNode node = find(root, num);
        return (node != null) ? node.timestamp : null;
    }

    private TreeNode find(TreeNode root, Integer num) {
        while (root != null && !root.key.equals(num)) {
            if (num < root.key) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return root;
    }

    @Override
    public Integer findMaxDepth() {
        return findDepth(root, true);
    }

    @Override
    public Integer findMinDepth() {
        return findDepth(root, false);
    }

    private Integer findDepth(TreeNode root, boolean max) {
        if (root == null) return 0;
        int leftDepth = findDepth(root.left, max);
        int rightDepth = findDepth(root.right, max);
        return (max ? Math.max(leftDepth, rightDepth) : Math.min(leftDepth, rightDepth)) + 1;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java cs250.hw4.BinaryTree <filename>");
            return;
        }

        File file = new File(args[0]);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        TreeStructure tree = new BinaryTree();
        Random rng = new Random(42);
        ArrayList<Integer> nodesToRemove = new ArrayList<>();
        ArrayList<Integer> nodesToGet = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            Integer lineInt = Integer.parseInt(line);
            tree.insert(lineInt);
            Integer rand = rng.nextInt(10);
            if (rand < 5) nodesToRemove.add(lineInt);
            else if (rand >= 5) nodesToGet.add(lineInt);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        for (int i = 0; i < 10 && i < nodesToGet.size(); i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }

        System.out.println("Max depth: " + tree.findMaxDepth());
        System.out.println("Min depth: " + tree.findMinDepth());

        for (Integer num : nodesToRemove) {
            tree.remove(num);
        }

        for (int i = 0; i < 10 && i < nodesToGet.size(); i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }

        System.out.println("Max depth after removals: " + tree.findMaxDepth());
        System.out.println("Min depth after removals: " + tree.findMinDepth());
    }
}
