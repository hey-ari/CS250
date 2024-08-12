package cs250.hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileNotFoundException;

public class BTree implements TreeStructure {
    private static final int T = 2; // Minimum degree of B-Tree

    private static class BTreeNode {
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<BTreeNode> children = new ArrayList<>();
        boolean isLeaf;

        public BTreeNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }

        public void insertNonFull(Integer key) {
            int i = keys.size() - 1;

            if (isLeaf) {
                keys.add(0); // Add a dummy key for easier insertion at the end
                while (i >= 0 && keys.get(i) > key) {
                    keys.set(i + 1, keys.get(i));
                    i--;
                }
                keys.set(i + 1, key);
            } else {
                while (i >= 0 && keys.get(i) > key) {
                    i--;
                }

                BTreeNode child = children.get(i + 1);
                if (child.keys.size() == 2 * T - 1) {
                    splitChild(i + 1, child);
                    if (keys.get(i + 1) < key) {
                        i++;
                    }
                }
                children.get(i + 1).insertNonFull(key);
            }
        }

        private void splitChild(int i, BTreeNode y) {
            BTreeNode z = new BTreeNode(y.isLeaf);
            for (int j = 0; j < T - 1; j++) {
                z.keys.add(y.keys.get(T + j));
            }

            if (!y.isLeaf) {
                for (int j = 0; j < T; j++) {
                    z.children.add(y.children.get(T + j));
                }
            }

            for (int j = 0; j < T - 1; j++) {
                y.keys.remove(T);
                if (!y.isLeaf) {
                    y.children.remove(T);
                }
            }

            children.add(i + 1, z);
            keys.add(i, y.keys.remove(T - 1));
        }

        public BTreeNode search(Integer k) {
            int i = 0;
            while (i < keys.size() && k > keys.get(i)) {
                i++;
            }

            if (i < keys.size() && keys.get(i).equals(k)) {
                return this;
            }

            if (isLeaf) {
                return null;
            }

            return children.get(i).search(k);
        }

        public void remove(Integer key) {
            int pos = findKey(key);

            if (pos < keys.size() && keys.get(pos).equals(key)) {
                if (isLeaf) {
                    removeFromLeaf(pos);
                } else {
                    removeFromNonLeaf(pos);
                }
            } else {
                if (isLeaf) {
                    return;
                }
                boolean flag = pos == keys.size();
                if (children.get(pos).keys.size() < T) {
                    fill(pos);
                }
                if (flag && pos > keys.size()) {
                    children.get(pos - 1).remove(key);
                } else {
                    children.get(pos).remove(key);
                }
            }
        }

        private void removeFromLeaf(int pos) {
            keys.remove(pos);
        }

        private void removeFromNonLeaf(int pos) {
            Integer k = keys.get(pos);
            if (children.get(pos).keys.size() >= T) {
                Integer pred = getPred(pos);
                keys.set(pos, pred);
                children.get(pos).remove(pred);
            } else if (children.get(pos + 1).keys.size() >= T) {
                Integer succ = getSucc(pos);
                keys.set(pos, succ);
                children.get(pos + 1).remove(succ);
            } else {
                merge(pos);
                children.get(pos).remove(k);
            }
        }

        private Integer getPred(int pos) {
            BTreeNode cur = children.get(pos);
            while (!cur.isLeaf) {
                cur = cur.children.get(cur.children.size() - 1);
            }
            return cur.keys.get(cur.keys.size() - 1);
        }

        private Integer getSucc(int pos) {
            BTreeNode cur = children.get(pos + 1);
            while (!cur.isLeaf) {
                cur = cur.children.get(0);
            }
            return cur.keys.get(0);
        }

        private void fill(int pos) {
            if (pos != 0 && children.get(pos - 1).keys.size() >= T) {
                borrowFromPrev(pos);
            } else if (pos != keys.size() && children.get(pos + 1).keys.size() >= T) {
                borrowFromNext(pos);
            } else {
                if (pos != keys.size()) {
                    merge(pos);
                } else {
                    merge(pos - 1);
                }
            }
        }

        private void borrowFromPrev(int pos) {
            BTreeNode child = children.get(pos);
            BTreeNode sibling = children.get(pos - 1);
            child.keys.add(0, keys.get(pos - 1));
            if (!child.isLeaf) {
                child.children.add(0, sibling.children.remove(sibling.children.size() - 1));
            }
            keys.set(pos - 1, sibling.keys.remove(sibling.keys.size() - 1));
        }

        private void borrowFromNext(int pos) {
            BTreeNode child = children.get(pos);
            BTreeNode sibling = children.get(pos + 1);
            child.keys.add(keys.get(pos));
            if (!child.isLeaf) {
                child.children.add(sibling.children.remove(0));
            }
            keys.set(pos, sibling.keys.remove(0));
        }

        private void merge(int pos) {
            BTreeNode child = children.get(pos);
            BTreeNode sibling = children.get(pos + 1);
            child.keys.add(keys.remove(pos));
            child.keys.addAll(sibling.keys);
            if (!child.isLeaf) {
                child.children.addAll(sibling.children);
            }
            children.remove(sibling);
        }

        private int findKey(int k) {
            int idx = 0;
            while (idx < keys.size() && keys.get(idx) < k) {
                idx++;
            }
            return idx;
        }
    }

    private BTreeNode root;

    public BTree() {
        root = null;
    }

    @Override
    public void insert(Integer key) {
        if (root == null) {
            root = new BTreeNode(true);
            root.keys.add(key);
        } else {
            if (root.keys.size() == 2 * T - 1) {
                BTreeNode newRoot = new BTreeNode(false);
                newRoot.children.add(root);
                newRoot.splitChild(0, root);
                int i = 0;
                if (newRoot.keys.get(0) < key) {
                    i++;
                }
                newRoot.children.get(i).insertNonFull(key);
                root = newRoot;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    @Override
    public Boolean remove(Integer key) {
        if (root == null) {
            System.out.println("The tree is empty");
            return false;
        }
        root.remove(key);
        if (root.keys.size() == 0) {
            if (root.isLeaf) {
                root = null;
            } else {
                root = root.children.get(0);
            }
        }
        return true;
    }

    @Override
public Long get(Integer key) {
    BTreeNode node = root.search(key);
    if (node != null) {
        int idx = node.keys.indexOf(key);
        // Assuming each key corresponds to a value
        return (long) node.keys.get(idx); // Return the value associated with the key
    }
    return null;
}

    @Override
    public Integer findMaxDepth() {
        return findDepth(root, true);
    }

    @Override
    public Integer findMinDepth() {
        return findDepth(root, false);
    }

    private int findDepth(BTreeNode node, boolean max) {
        if (node == null) {
            return 0;
        }
        int depth = 0;
        if (max) {
            for (BTreeNode child : node.children) {
                depth = Math.max(depth, findDepth(child, true));
            }
        } else {
            int minDepth = Integer.MAX_VALUE;
            for (BTreeNode child : node.children) {
                minDepth = Math.min(minDepth, findDepth(child, false));
            }
            depth = (node.children.isEmpty() ? 0 : minDepth);
        }
        return depth + 1;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(args[0]);
        FileReader fReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fReader);
        TreeStructure tree = new BTree();
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
        for (int i = 0; i < 10; i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }
        System.out.println("Max depth: " + tree.findMaxDepth());
        System.out.println("Min depth: " + tree.findMinDepth());
        for (Integer num : nodesToRemove) {
            tree.remove(num);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }
        System.out.println("Max depth: " + tree.findMaxDepth());
        System.out.println("Min depth: " + tree.findMinDepth());
    }
}




