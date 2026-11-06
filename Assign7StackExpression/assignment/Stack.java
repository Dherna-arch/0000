package assignment;

public class Stack<T> implements StackInterface<T>{
	Node<T> head;
	int size;
	
	public void push(T item) {
		Node<T> newNode = new Node<>(item);
		newNode.next=this.head;
		this.head=newNode;
		this.size++;
	}
	
	public T pop() {
		Node<T> node = this.head;
		this.head=this.head.next;
		this.size--;
		return node.value;
	}

	public T peek() {
		return this.head.value;
	}

	public boolean isEmpty() {
		boolean empty = false;
		if(this.head == null) {
			empty = true;
		}
		return empty;
	}

	public int size() {
		return this.size;
	}

}
