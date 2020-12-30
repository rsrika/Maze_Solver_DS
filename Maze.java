public class Maze
{
    private Cell[][] board;
    private final int DELAY = 200;

    public Maze(int rows, int cols, int[][] map){
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        //grab number of rows to invert grid system with StdDraw (lower-left, instead of top-left)
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c , height - r, r, c, 0.5, false) : new Cell(c, height - r, r, c, 0.5, true);
            }
    }

    public void draw()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++){
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
            StdDraw.show();
    }
    



    /*
     * This method uses a stack to manage the order of the cells that are visited. 
     * Returns a boolean indicating whether a path to the exit has been found beginning 
     * at the location (row, col) of board.
     */
    public boolean visitedAllCells()
    {
    	int numCellsVisited = 0;  
    	int numValidCells = 0;
    	for(int i = 0; i<board[0].length; i++)
    	{
    		for(int j = 0; j< board.length; j++)
    		{
    			if(isValid(i,j))
    			{
    				numValidCells++;
    				if (board[i][j].isVisited())
        			{
        				numCellsVisited++;
        			}
    			}
    		}
    	}
    	return (numValidCells == numCellsVisited);
    }
    
    public boolean isValidRight(Cell currentPos){
    	return (isValid(currentPos.getRow(), currentPos.getCol()+1));
    }
    public boolean isValidDown(Cell currentPos){
    	return (isValid(currentPos.getRow()+1, currentPos.getCol()));
    }
    public boolean isValidLeft(Cell currentPos){
    	return (isValid(currentPos.getRow(), currentPos.getCol()-1));
    }
    public boolean isValidUp(Cell currentPos) {
    	return (isValid(currentPos.getRow()-1, currentPos.getCol()));
    }
    public void makeCellsGreenStack(Stack<Cell> stack)
    {
    	while(!stack.isEmpty())
    	{
    		Cell temp = stack.pop();
    		temp.becomePath();
    	}
    }
    public void makeCellsGreenQueue(Queue<Cell> queue)
    {
    	while(!queue.isEmpty())
    	{
    		Cell temp = queue.dequeue();
    		temp.becomePath();
    	}
    }
 
    public int numValidPositions(Cell currentPos)
    {
    	int num = 0;
    	if(isValidRight(currentPos)){
    		num++;
    	}
    	if(isValidLeft(currentPos)){
    		num++;
    	}
    	if(isValidUp(currentPos)){
    		num++;
    	}
    	if(isValidDown(currentPos)){
    		num++;
    	}
    	return num;
    }
    public boolean findPathWithStack(int row, int col)
    {
    	boolean pathFound = false;
    	Stack<Cell> path = new Stack<Cell>(); 
        path.push(board[row][col]);
        Cell currentPos = path.peek();
        Cell[] cellsWithMoreThanOnePos = new Cell[board[0].length * board.length];
        int index = 0;
        while(pathFound == false)
        {
        	if(numValidPositions(currentPos)>1)
        	{
        		for(int i = 0; i<numValidPositions(currentPos); i++)
        		{
        			cellsWithMoreThanOnePos[index+i] = currentPos;
        		}
        		
        	}
        	if(visitedAllCells())
        	{
        		return false;
        	}
        	if(isExit(currentPos.getRow(), currentPos.getCol()))
    		{
    			pathFound = true;
    			makeCellsGreenStack(path);
    			return pathFound;
    		}      	
        	// Priority: right, down, left, up
        	if(isValidRight(currentPos))
        	{
        		currentPos.visitCell();
        		path.push(board[currentPos.getRow()][currentPos.getCol()+1]);
        		currentPos = path.peek();
        	}
        	else if(isValidDown(currentPos))
        	{
        		currentPos.visitCell();
        		path.push(board[currentPos.getRow()+1][currentPos.getCol()]);
        		currentPos = path.peek();
        	}
        	else if (isValidLeft(currentPos))
        	{
        		currentPos.visitCell();
        		path.push(board[currentPos.getRow()][currentPos.getCol()-1]);
        		currentPos = path.peek();
        	}
        	else if(isValidUp(currentPos))
        	{
        		currentPos.visitCell();
        		path.push(board[currentPos.getRow()-1][currentPos.getCol()]);
        		currentPos = path.peek();
        	}
        	else
        	{
        		currentPos.visitCell();
        		path.pop();
        		currentPos = cellsWithMoreThanOnePos[index];
        		cellsWithMoreThanOnePos[index] = null;
        		index++;
        		if(cellListEmpty(cellsWithMoreThanOnePos))
        		{
        			return false;
        		}
        	}
        }
        if(visitedAllCells())
    	{
    		return false;
    	}
        return pathFound;
    }
    
    public boolean cellListEmpty(Cell[] list)
    {
    	for(int i = 0; i<list.length;i++)
    	{
    		if(list[i] != null)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    
    /*
     * This method uses a queue to manage the order of the cells that are visited. 
     * Returns a boolean indicating whether a path to the exit has been found beginning 
     * at the location (row, col) of board.
     */
    public boolean findPathWithQueue(int row, int col)
    {
    	Queue<Cell> path = new Queue<Cell>();
    	int count = 0;
    	Cell firstCell = board[row][col];
    	Cell currentCell;
    	boolean solved = false;
    	Cell[] cellsWithMoreThanOnePos = new Cell[board[0].length * board.length];
    	Queue<Cell> actualPath = new Queue<Cell>();
        int index = 0;
    	path.enqueue(firstCell); 
    	while (!path.isEmpty())
    	 {
    		currentCell = path.dequeue();
    		if(numValidPositions(currentCell)>1)
        	{
        		for(int i = 0; i<numValidPositions(currentCell); i++)
        		{
        			cellsWithMoreThanOnePos[index+i] = currentCell;
        		}
        		
        	}
    		if (isExit(currentCell.getRow(),currentCell.getCol()))
	        {
	           solved = true;
	           makeCellsGreenQueue(actualPath);
	           return solved;
	        }  
			if (isValidRight(currentCell))
			{
				currentCell.visitCell();
				actualPath.enqueue(currentCell);
				path.enqueue(board[currentCell.getRow()][currentCell.getCol()+1]);
				currentCell = path.peek();
				currentCell.visitCell();
				
			}
			else if (isValidDown(currentCell))
			{
				currentCell.visitCell();
				actualPath.enqueue(currentCell);
				path.enqueue(board[currentCell.getRow()+1][currentCell.getCol()]);
				currentCell = path.peek();
				currentCell.visitCell();
				
			}
			else if (isValidLeft(currentCell))
			{
				currentCell.visitCell();
				actualPath.enqueue(currentCell);
				path.enqueue(board[currentCell.getRow()][currentCell.getCol()-1]);
				currentCell = path.peek();
				currentCell.visitCell();
				
				  
			}
			else if (isValidUp(currentCell))
			{
				currentCell.visitCell();
				actualPath.enqueue(currentCell);
				path.enqueue(board[currentCell.getRow()-1][currentCell.getCol()]);
				currentCell = path.peek();
				currentCell.visitCell();
			} 
			else
			{
				currentCell.visitCell();
        		currentCell = cellsWithMoreThanOnePos[index];
        		path.enqueue(currentCell);
        		cellsWithMoreThanOnePos[index] = null;
        		index++;
        		if(cellListEmpty(cellsWithMoreThanOnePos))
        		{
        			return false;
        		}
			}
			if(currentCell.getRow() == board.length-1 && currentCell.getCol() == board.length-1)
			{
				actualPath.enqueue(currentCell);
			}
    	 }
    	 return solved;
    }

    /* 
     * Returns a boolean whether or not position (row, col) is an open cell in the
     * board.
     */
    private boolean isValid(int row, int col)
    {
    	if((row<0 || col<0) || row>=board[0].length || col>= board.length)
    	{
    		return false;
    	}
    	Cell cell1 = board[row][col];
    	if(cell1.isWall() || cell1.isVisited())
    	{
    		return false;
    	}
        return true;
    }
    
    /*
     * Returns a boolean whether position (row, col) is the exit in the board.
     */
    private boolean isExit(int row, int col)
    {
    	Cell cell1 = board[row][col];
    	if( (row == board[0].length-1 && col == board.length-1)) // isValid(row,col) &&
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        int[][] maze = {
        		{1,1,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,1,0,1,1,1,1,1,0,1,1,1,1,1},
        		{0,1,0,1,0,0,0,1,1,1,0,0,1,0},
        		{0,1,1,1,1,0,0,1,1,0,1,1,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,1}
        		};

        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        // Change the commenting to test your queue method instead of the stack method
        geerid.findPathWithStack(0, 0);
        //geerid.findPathWithQueue(0, 0);
        geerid.draw();
    }
}
