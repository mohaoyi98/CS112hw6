/*
 * LinkedTree.java
 *
 * Computer Science 112
 *
 * Modifications and additions by:
 *     name:
 *     username:
 */
package hw06;

import java.util.*;


/*
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private LLList data;     // list of data values for this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent. NOT YET MAINTAINED!
        
        private Node(int key, Object data){
            this.key = key;
            this.data = new LLList();
            this.data.addItem(data, 0);
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    // the root of the tree as a whole
    private Node root;
    
    public LinkedTree() {
        root = null;
    }
    
    /*
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null) {
            preorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null) {
            preorderPrintTree(root.left);
        }
        if (root.right != null) {
            preorderPrintTree(root.right);
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null) {
            postorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null) {
            postorderPrintTree(root.left);
        }
        if (root.right != null) {
            postorderPrintTree(root.right);
        }
        System.out.print(root.key + " ");
    }
    
    /*
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null) {
            inorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null) {
            inorderPrintTree(root.left);
        }
        System.out.print(root.key + " ");
        if (root.right != null) {
            inorderPrintTree(root.right);
        }
    }
    
    /* 
     * Inner class for temporarily associating a node's depth
     * with the node, so that levelOrderPrint can print the levels
     * of the tree on separate lines.
     */
    private class NodePlusDepth {
        private Node node;
        private int depth;
        
        private NodePlusDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a
     * level-order traversal.
     */
    public void levelOrderPrint() {
        LLQueue<NodePlusDepth> q = new LLQueue<NodePlusDepth>();
        
        // Insert the root into the queue if the root is not null.
        if (root != null) {
            q.insert(new NodePlusDepth(root, 0));
        }
        
        // We continue until the queue is empty.  At each step,
        // we remove an element from the queue, print its value,
        // and insert its children (if any) into the queue.
        // We also keep track of the current level, and add a newline
        // whenever we advance to a new level.
        int level = 0;
        while (!q.isEmpty()) {
            NodePlusDepth item = q.remove();
            
            if (item.depth > level) {
                System.out.println();
                level++;
            }
            System.out.print(item.node.key + " ");
            
            if (item.node.left != null) {
                q.insert(new NodePlusDepth(item.node.left, item.depth + 1));
            }
            if (item.node.right != null) {
                q.insert(new NodePlusDepth(item.node.right, item.depth + 1));
            }
        }
        System.out.println();
    }
    
    /*
     * Searches for the specified key in the tree.
     * If it finds it, it returns the list of data items associated with the key.
     * Invokes the recursive searchTree method to perform the actual search.
     */
    public LLList search(int key) {
        Node n = searchTree(root, key);
        if (n == null) {
            return null;
        } else {
            return n.data;
        }
    }
    
    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null) {
            return null;
        } else if (key == root.key) {
            return root;
        } else if (key < root.key) {
            return searchTree(root.left, key);
        } else {
            return searchTree(root.right, key);
        }
    }
    
    /*
     * Inserts the specified (key, data) pair in the tree so that the
     * tree remains a binary search tree.
     */
    public void insert(int key, Object data) { // parent field added
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                trav.data.addItem(data, 0);
                return;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Insert the new node.
        Node newNode = new Node(key, data);
        if (parent == null) {    // the tree was empty
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
            newNode.parent = parent;
        } else {
            parent.right = newNode;
            newNode.parent = parent;
        }
    }
    
    /*
     * FOR TESTING: Processes the integer keys in the specified array from 
     * left to right, adding a node for each of them to the tree. 
     * The data associated with each key is a string based on the key.
     */
    public void insertKeys(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            insert(keys[i], "data for key " + keys[i]);
        }
    }
    
    /*
     * Deletes the node containing the (key, data) pair with the
     * specified key from the tree and return the associated data item.
     */
    public LLList delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Delete the node (if any) and return the removed data item.
        if (trav == null) {   // no such key    
            return null;
        } else {
            LLList removedData = trav.data;
            deleteNode(trav, parent);
            return removedData;
        }
    }
    
    /*
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted. 
     */
    private void deleteNode(Node toDelete, Node parent) { // parent field added
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }
            
            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.data = replace.data;
            
            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null) {
                toDeleteChild = toDelete.left;
            } else {
                toDeleteChild = toDelete.right;  // null if it has no children
            }
            
            if (toDelete == root) {
                root = toDeleteChild;
            } else if (toDelete.key < parent.key) {
                parent.left = toDeleteChild;
            } else {
                parent.right = toDeleteChild;
            }
            
            // update the parent field accordingly
            if (toDeleteChild != null) {
            	toDeleteChild.parent = parent;
            }
        }
    }
    
    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator preorderIterator() {
        return new PreorderIterator();
    }
    
    /* 
     * inner class for a preorder iterator 
     * IMPORTANT: You will not be able to actually use objects from this class
     * to perform a preorder iteration until you have modified the LinkedTree
     * methods so that they maintain the parent fields in the Nodes.
     */
    private class PreorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PreorderIterator() {
            // The traversal starts with the root node.
            nextNode = root;
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.left != null) {
                nextNode = nextNode.left;
            } else if (nextNode.right != null) {
                nextNode = nextNode.right;
            } else {
                // We've just visited a leaf node.
                // Go back up the tree until we find a node
                // with a right child that we haven't seen yet.
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null &&
                       (parent.right == child || parent.right == null)) {
                    child = parent;
                    parent = parent.parent;
                }
                
                if (parent == null) {
                    nextNode = null;  // the traversal is complete
                } else {
                    nextNode = parent.right;
                }
            }
            
            return key;
        }
    }
    
    /*
     * "wrapper method" for the recursive depthInTree() method
     * from PS 7, Problem 4
     */
    public int depth(int key) {
        if (root == null) {    // root is the root of the entire tree
            return -1;
        }
        
        return depthInTree(key, root);
    }
    
    /*
     * original version of the recursive depthInTree() method  
     * from PS 7, Problem 4. You will write a more efficient version
     * of this method.
     */
    private static int depthInTree(int key, Node root) {
        if (key == root.key) {
            return 0;
        }
        
        if (root.left != null) {
            int depthInLeft = depthInTree(key, root.left);
            if (depthInLeft != -1) {
                return depthInLeft + 1;
            }
        }
        
        if (root.right != null) {
            int depthInRight = depthInTree(key, root.right);
            if (depthInRight != -1) {
                return depthInRight + 1;
            }
        }
        
        return -1;
    }
    
    /* depthIter - takes an integer key as its parameter and  
     * uses iteration to determine and return the depth of the 
     * node with that key in a binary search tree (returns -1 if not found)
     */
    public int depthIter(int key) {
    	Node trav = root; // the traversal node that traverses the tree
    	int depth = 0;    // the current depth of traversal
    	
    	while (trav != null) {
    		if (key == trav.key) {
    			return depth;
    		} else if (key < trav.key) {
    			trav = trav.left;
    		} else {
    			trav = trav.right;
    		}
    		depth++;
    	}
    	
    	return -1; // not found
    }
    
    /* numEvenKeysRec - a helper static method that takes a reference to a 
     * Node object as it parameter, and uses recursion to find and return the
     * number of even keys in the binary search tree of subtree whose root 
     * node is specified by the parameter
     */
    private static int numEvenKeysRec(Node root) {
    	if (root == null) {
    		return 0;
    	}
    	
    	int numEvenLeft = numEvenKeysRec(root.left);
        int numEvenRight = numEvenKeysRec(root.right);
        int numEvenRoot;
        
        if (root.key % 2 == 0) {
        	numEvenRoot = 1;
        } else {
        	numEvenRoot = 0;
        }
        
    	return numEvenLeft + numEvenRight + numEvenRoot;
    }
    
    /* numEvenKeys - a non-static method that takes no parameter and returns 
     * the number of even keys in the entire tree. 
     * 
     */
    public int numEvenKeys() {
    	return numEvenKeysRec(root);
    }
    
    /* deleteMax - a non-static method that takes no parameter and uses 
     * iteration to find and delete the node containing the largest key 
     * in the tree; it also returns the value of the key whose node was 
     * deleted, and returns -1 when the tree is empty when the method is called
     */
    public int deleteMax() { // parent field added
    	if (root == null) {
    		return -1;
    	}
    	
    	// look for the node with the largest key
    	// and keep the reference of the parent of that node
    	Node maxNode = root;
    	Node parentNode = null;
    	while (maxNode.right != null) {
    		parentNode = maxNode;
    		maxNode = maxNode.right;
    	}
    	
    	int removedKey = maxNode.key; 
    	// the right subtree of the node with the largest key must be empty
    	if (maxNode == root) {
    		root = root.left;
    		if (root != null) {
    			root.parent = null;
    		}
    	} else {
    		parentNode.right = maxNode.left;
    		
    		if (parentNode.right != null) {
    			parentNode.right.parent = parentNode;
    		}
    	}
    	
    	return removedKey;
    	
    }
    
    /* postorderIterator - creates and returns a postorderIterator
     */
    public LinkedTreeIterator postorderIterator() {
    	return new PostorderIterator();
    }
    
    /* a private class for a linked tree iterator that performs postorder
     *  traversal
     */
    private class PostorderIterator implements LinkedTreeIterator {
    	private Node nextNode;
    	
    	public PostorderIterator() {
    		nextNode = root;
    		if (nextNode != null) {
    			
    			while (nextNode.left != null || nextNode.right != null) {
    				
    				if (nextNode.left != null) {
    					nextNode = nextNode.left;
    				} else if (nextNode.right != null) {
    					nextNode = nextNode.right;
    				}
    				
    			}
    			
    		}
    	}
    	
    	public boolean hasNext() {
    		return (nextNode != null);
    	}
    	
    	public int next() {
    		if (nextNode == null) {
                throw new NoSuchElementException();
            }
    		
    		int key = nextNode.key;
    		

    		if (nextNode.parent == null) {
    			nextNode = null;
    		} else if (nextNode.parent.right == nextNode || nextNode.parent.right == null) {
    			nextNode = nextNode.parent;
    		} else {
    			nextNode = nextNode.parent.right;
    			while (nextNode.left != null || nextNode.right != null) {
    				if (nextNode.left != null) {
    					nextNode = nextNode.left;
    				} else if (nextNode.right != null) {
    					nextNode = nextNode.right;
    				}
    			}
    		}
    		
    		return key;
    	}
    }
    
    public static void main(String[] args) {
        System.out.println("--- Testing depth()/depthInTree() from Problem 4 ---");
        System.out.println();
        System.out.println("(0) Testing on tree from Problem 3, depth of 28 node");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            tree.insertKeys(keys);
            
            int results = tree.depth(28);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(3);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 3);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
                           
        /*
         * Add at least two unit tests for each method from Problem 6.
         * Test a variety of different cases. 
         * Follow the same format that we have used above.
         */
    	
		System.out.println("--- Testing depthIter() from Problem 6 ---");
        System.out.println();
        System.out.println("(1) Testing on empty tree, depth of 28 node");
        try {
            LinkedTree tree = new LinkedTree();
                        
            int results = tree.depthIter(28);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }

        System.out.println();
        
        System.out.println("(2) Testing on tree {1, 2, 3, 4, 5}, depth of 28 node");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {1, 2, 3, 4, 5};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(28);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }

        System.out.println();
        
        System.out.println("(3) Testing on tree {5, 1, 7, 2, 6, 8}, depth of 8 node");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 7, 2, 6, 8};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(8);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(4) Testing on tree {5, 1, 7, 2, 6, 8}, depth of 2 node");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 7, 2, 6, 8};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(2);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(5) Testing on tree {5, 1, 7, 2, 6, 8}, depth of 5 node");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 7, 2, 6, 8};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(5);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(0);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 0);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println("\n");
        
        System.out.println("--- Testing numEvenKeys() from Problem 6 ---");
        System.out.println();
        System.out.println("(1) Testing on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            
            int results = tree.numEvenKeys();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(0);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 0);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(2) Testing on tree {-5, 1, -7, 2, 6, 8}");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {-5, 1, -7, 2, 6, 8};
            tree.insertKeys(keys);
            
            int results = tree.numEvenKeys();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(3);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 3);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(3) Testing on tree {5, 1, 7, 0, 2, 6, -8}");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 7, 0, 2, 6, -8};
            tree.insertKeys(keys);
            
            int results = tree.numEvenKeys();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(4);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 4);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println("\n");
        
        System.out.println("--- Testing deleteMax() from Problem 6 ---");
        System.out.println();
        System.out.println("(1) Testing on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(2) Testing on tree {5, 1, 2, 3, 4} (only a left sub tree)");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 2, 3, 4};
            tree.insertKeys(keys);
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println("expected results:");
            System.out.println(5);
            System.out.println("1\n2\n3\n4");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 5);
            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(3) Testing on tree {5} (only a root)");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5};
            tree.insertKeys(keys);
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println("expected results:");
            System.out.println(5);
            System.out.println();
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 5);
            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(4) Testing on tree {5, 1, 6, 7} (maxNode has no left child)");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 6, 7};
            tree.insertKeys(keys);
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println("expected results:");
            System.out.println(7);
            System.out.println("5\n1 6");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 7);
            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(5) Testing on tree {5, 1, 7, 6} (maxNode has left child)");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 7, 6};
            tree.insertKeys(keys);
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println("expected results:");
            System.out.println(7);
            System.out.println("5\n1 6");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 7);
            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println("\n");
        
        System.out.println("--- Testing PostorderIterator from Problem 7 ---");
        System.out.println();
        System.out.println("(1) Testing on whether insert() method maintains parent field");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            tree.insertKeys(keys);
            
            System.out.println("The initial tree is: ");
            tree.levelOrderPrint();
            
            System.out.println("After inserting " + 25 + ": ");
            tree.insert(25, "Node 25's data");
            tree.levelOrderPrint();
            
            System.out.println("Test parent field using PreorderIterator");
            LinkedTreeIterator preiter = tree.preorderIterator();
            
            System.out.println("actual results:");
            while (preiter.hasNext()) {
            	System.out.print(preiter.next() + " ");
            }
            System.out.println();
            
            System.out.println("expected results:");
            tree.preorderPrint();            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(2) Testing on whether insert() method maintains parent field");
        try {
            LinkedTree tree = new LinkedTree();
            
            System.out.println("The initial tree is: ");
            tree.levelOrderPrint();
            
            System.out.println("After inserting " + 25 + ": ");
            tree.insert(25, "Node 25's data");
            tree.levelOrderPrint();
            
            System.out.println("Test parent field using PreorderIterator");
            LinkedTreeIterator preiter = tree.preorderIterator();
            
            System.out.println("actual results:");
            while (preiter.hasNext()) {
            	System.out.print(preiter.next() + " ");
            }
            System.out.println();
            
            System.out.println("expected results:");
            tree.preorderPrint();            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(3) Testing on whether delete() method maintains parent field");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            tree.insertKeys(keys);
            
            System.out.println("The initial tree is: ");
            tree.levelOrderPrint();
            
            System.out.println("After deleting " + 53 + ": ");
            tree.delete(53);
            tree.levelOrderPrint();
            
            System.out.println("Test parent field using PreorderIterator");
            LinkedTreeIterator preiter = tree.preorderIterator();
            
            System.out.println("actual results:");
            while (preiter.hasNext()) {
            	System.out.print(preiter.next() + " ");
            }
            System.out.println();
            
            System.out.println("expected results:");
            tree.preorderPrint();            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(4) Testing on whether deleteMax() method maintains parent field");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {53};
            tree.insertKeys(keys);
            
            System.out.println("The initial tree is: ");
            tree.levelOrderPrint();
            
            System.out.println("After deleting " + 53 + ": ");
            tree.delete(53);
            tree.levelOrderPrint();
            
            System.out.println("Test parent field using PreorderIterator");
            LinkedTreeIterator preiter = tree.preorderIterator();
            
            System.out.println("actual results:");
            while (preiter.hasNext()) {
            	System.out.print(preiter.next() + " ");
            }
            System.out.println();
            
            System.out.println("expected results:");
            tree.preorderPrint();            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("(5) Testing on PostorderIterator");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            tree.insertKeys(keys);
            
            System.out.println("The initial tree is: ");
            tree.levelOrderPrint();
                        
            System.out.println("Testing PostorderIterator by performing a full post order traverse");
            LinkedTreeIterator postiter = tree.postorderIterator();
            
            System.out.println("actual results:");
            while (postiter.hasNext()) {
            	System.out.print(postiter.next() + " ");
            }
            System.out.println();
            
            System.out.println("expected results:");
            tree.postorderPrint();            
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        
                           
    }
}
