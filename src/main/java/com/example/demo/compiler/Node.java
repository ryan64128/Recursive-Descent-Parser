package com.example.demo.compiler;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T data;
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private Node<T> parent = null;
	private int depth = 0;
	
	public Node(T data, int depth) {
		this.data = data;
		this.depth = depth;
	}
	
	public Node<T> addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}
	
	public void addChildren(List<Node<T>> children) {
		children.forEach(node -> node.setParent(this));
		this.children.addAll(children);
	}
	
	public List<Node<T>> getChildren() {
		return this.children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public void printTree() {
		StringBuilder depthSpace = new StringBuilder("");
		for (int i=0; i<this.depth; i++) {
			depthSpace.append("  ");
		}
		System.out.println(depthSpace.toString() + this.data);
		children.forEach(node -> node.printTree());
	}
}
