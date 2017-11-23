package com.github.ydydwang.aio.collection;

public class ListNode<T> {
	private final T val;
	private ListNode<T> next;

	public ListNode(T val) { 
		this.val = val; 
	}

	public T getVal() {
		return val;
	}

	public ListNode<T> getNext() {
		return next;
	}

	public void setNext(ListNode<T> next) {
		this.next = next;
	}

}