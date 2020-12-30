public class Queue<T>
{
    

	private Node<T> head;
	private Node<T> tail;
	private int size;
	
    private class Node<T> 
    {
        // ADD YOUR CODE HERE.
        private T data;
		private Node next;
		public Node()
		{
			data = null;
			next = null;
		}
		public Node(T n)
		{
			data = n;
			next = null;
		}
		public Node(T n, Node nextNode)
		{
			data = n;
			next = nextNode;
		}
		public T getData()
		{
		    return data;
		}
		public Node getNext()
		{
		    return next;
		}
    }
    
    public Queue() 
    {
        head =null;
        tail = null;
        
    }
    public void enqueue(T newItem)
    {
        Node newNode = new Node(newItem);
        if(head!= null)
        {
            tail.next = newNode;
            newNode.next = null;
            tail = tail.next;
            size++;
        }
        else
        {
            head = newNode;
            size++;
            tail = head;
        }
        
        
    }
    public T dequeue()
    {
        if(head!=null)
        {
            Node<T> first = head;
            head = head.next;
            size--;
            T data = first.getData();
            return data;
        }
        return null;
    }
   
    public boolean isEmpty() 
    {
        return (head == null);
    }
    public int size()
    {
        return size;
    }
    public T peek()
    {
        if(head!=null)
        {
            T data = head.getData();
            return data;
        }
        return null;
        
    }
}