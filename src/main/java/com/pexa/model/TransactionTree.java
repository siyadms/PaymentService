package com.pexa.model;

import static com.pexa.model.Transaction.getkey;

import java.util.ArrayList;
import java.util.List;

public class TransactionTree{

	class Node {
	
		Transaction value;
		Node left;
		Node right;
		Node(Transaction t){
			this.value = t;
		}
		
	}
	
	Node root;
	

	
	public List<Transaction> getNetTransactions() {
		List<Transaction> netTxns = new ArrayList<>();
		 
		if (root == null) return null;
		traverseInOrder(root,netTxns);
		
		
		return netTxns;
	}
	
	private Transaction getNode(Node current, Transaction value) {
	    if (current == null) {
	        return null;
	    } 
	    if (getkey(value).equals(getkey(current.value))) {
	        return current.value;
	    } 
	    return (getkey(current.value).compareTo(getkey(value)) > 0)
	      ? getNode(current.left, value)
	      : getNode(current.right, value);
	}
	
	private Node deleteNode(Node current, Transaction value) {
		    if (current == null) {
		        return null;
		    }

		    if (getkey(value).equals(getkey(current.value))) {
		    	if (current.left == null && current.right == null) {
		    	    return null;
		    	}
		    	else if (current.right == null) {
		    	    return current.left;
		    	}

		    	else if (current.left == null) {
		    	    return current.right;
		    	}
		    	else {
		    		Transaction smallestNode = findSmallestValue(current.right);
		    		current.value = smallestNode;
		    		current.right = deleteNode(current.right, smallestNode);
		    		return current;
		    	}
		    	
		    } 
		    if (getkey(current.value).compareTo(getkey(value)) > 0) {
		        current.left = deleteNode(current.left, value);
		        return current;
		    }
		    current.right = deleteNode(current.right, value);
		    return current;
	}
	
	private Transaction findSmallestValue(Node root) {
	    return root.left == null ? root.value : findSmallestValue(root.left);
	}
	
	public void add(Transaction t) {
		root = addNode(root,t); 
	}

	private Node addNode(Node current, Transaction value) {
		if (current == null) {
	        return new Node(value);
	    }

	    if (getkey(current.value).compareTo(getkey(value))>0) { //new value is less, so add to left
	        current.left = addNode(current.left, value);
	    } else if (getkey(current.value).compareTo(getkey(value))<0) {//new value is greater ,so add to right
	        current.right = addNode(current.right, value);
	    } else {
	        // value already exists, add amount
	    	current.value.setAmount(current.value.getAmount()+value.getAmount());
	        return current;
	    }

	    return current;
	}
	
	private void traverseInOrder(Node node, List<Transaction> txns) {
	    if (node != null) {
	        traverseInOrder(node.left, txns);
	        // found last node
	        Transaction selectedTx = node.value;

	        Transaction tx=getNode(root, new Transaction("test",selectedTx.getTo(),selectedTx.getFrom(),0)) ;
	        if (tx != null) { //reverse txn exist
	        	if(selectedTx.getAmount() - tx.getAmount() > 0){ // delete tx
	        		selectedTx.setAmount(selectedTx.getAmount() - tx.getAmount());
	        		deleteNode(root, tx);
	        		txns.add(selectedTx);
	        	}
	        	else if (selectedTx.getAmount() - tx.getAmount() < 0){ // delete selectedTx
	        		tx.setAmount(tx.getAmount()-selectedTx.getAmount());
	        		node = deleteNode(node, selectedTx);
	        		txns.add(tx);
	        	}
	        	else { //case 0 delete both nodes.
	        		node = deleteNode(node, tx);
	        		deleteNode(node, selectedTx);
	        	}
	        }
	        else {
	        	txns.add(selectedTx);
	        }
	        
	        traverseInOrder(node.right, txns);
	    }
	}


}
