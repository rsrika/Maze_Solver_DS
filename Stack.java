public class Stack<T> 
{
    private T data;
	private Node head;
	private int size = 0;
	
	private class Node
	{
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
    public Stack()
    {
        // ADD YOUR CODE HERE.
        head = new Node();
    }
    
    public void push(T newItem) 
    {
        // ADD YOUR CODE HERE.
       head = new Node(newItem, head);
       size++;
        
    }
    
    public T pop() 
    {
        if(head != null)
        {
            
            // ADD YOUR CODE HERE.
            T data = head.getData();
            head = head.next;
            size--;
            return data;
        }
        return null;
    }
    
    public T peek() 
    {
        // ADD YOUR CODE HERE.
        if (head != null)
        {
            T data = head.getData();
            return data;
        }
        return null;
    }
    
    public boolean isEmpty() 
    {
        // ADD YOUR CODE HERE.
        if (head.next == null)
        {
            return true;
        }
        return false;
    }
    
    public int size() 
    {
        return size;
        // ADD YOUR CODE HERE.
    }
}