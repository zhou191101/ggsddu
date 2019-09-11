package com.ggsddu.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoup
 */
public class LRUCache {
	private Map<Integer, Node> map = new HashMap<>();
	private Node first;
	private Node last;
	private int maxCap;

	public LRUCache(int capacity) {
		maxCap = capacity;
	}

	public int get(int key) {
		if (!map.containsKey(key)) {
			return -1;
		}
		Node node = move2First(map.get(key));
		return node.val;
	}


	public void put(int key, int value) {
		Node node = map.get(key);

		if (node == null) {

			if (maxCap <= map.size()) {
				map.remove(last.key);
				removeLast();
			}
			node = new Node(key, value);
		}
		node.val = value;
		move2First(node);
		map.put(key, node);


	}

	private Node move2First(Node node) {
		Node pre = node.pre;
		Node next = node.next;

		if (node == first) {
			return node;
		}

		if (pre != null) {
			pre.next = next;
		}

		if (next != null) {
			next.pre = pre;
		}
		if (node == last) {
			last = node.pre;
		}
		if (first == null && last == null) {
			first = last = node;
			return node;
		}
		node.next = first;
		first.pre = node;
		node.pre = null;
		first = node;

		return node;
	}

	private void removeLast() {
		Node pre = last.pre;
		if (pre != null) {
			pre.next = null;
			last = pre;
			return;
		}

		if (last == first) {
			last = first = null;
			return;
		}
	}

	private class Node {
		int key;
		Node pre;
		Node next;
		int val;

		public Node(int key, int val) {
			this.key = key;
			this.val = val;
		}
	}

	public static void main(String[] args) {
		LRUCache lruCache = new LRUCache(1);
		lruCache.put(2, 1);
		System.out.println(lruCache.get(2));
		lruCache.put(3, 2);
		System.out.println(lruCache.get(2));
		//	lruCache.put(3,3);
		//	System.out.println(lruCache.get(2));
		//	lruCache.put(4,4);
		//System.out.println(lruCache.get(1));
		System.out.println(lruCache.get(3));
		//System.out.println(lruCache.get(4));

	}
}
