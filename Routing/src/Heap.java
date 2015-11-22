public class Heap
{
	int H[],D[];
	int size,printSize;
	Heap()
	{
		 H = new int[randomGraph.n];
		 D = new int[randomGraph.n];
		 size = 0;
	}
	Heap(int s)
	{
		 H = new int[s];
		 D = new int[s];
		 size = 0;
	}
	
	void heapifyUp(int i)
	{
		while(i>1 && D[parent(i)] < D[i])
		{
			swap(i,parent(i));
			i = parent(i);
		}
	}
	void heapifyDown(int i)
	{
		int l = left(i);
		int r = right(i);
		int largest;
		if(l<=size && D[l]>D[i])
		{
			largest = l;
		}
		else
			largest = i ;
		if(r<=size && D[r]>D[largest])
			largest = r ;
		if(largest != i)
		{
			swap(i,largest);
			heapifyDown(largest);
		}
	}
	int extractMax()
	{
		int ret = H[1];
		D [1] = D[size];
		H [1] = H[size];
		size--;
		heapifyDown(1);
		return ret;
		
	}
	int returnMax()
	{
		return H[1];
		
	}
	private int parent(int i)
	{
		return (int) (Math.floor(i/2.0));
	}
	
	private int left(int i)
	{
		return 2*i;
	}
	
	private int right(int i)
	{
		return (2*i + 1);
	}
	private int heapSize()
	{
		return size;
	}
	private void  swap(int a,int b)
	{
		int temp;
		temp = D[a];
		D[a] = D[b];
		D[b] = temp;
		temp = H[a];
		H[a] = H[b];
		H[b] = temp;
	}
    void insert(int d,int v)
	{
		size++; 
		D[size] = d;
		H[size] = v;
		heapifyUp(size);
	}
    void printHeap()
	{
		int i;
		for(i=1;i<=size;i++)
		{
			System.out.println("vertice " + H[i] + " with distance " + D[i]);
		}
	}
    
    void heapSort()
    {
    	int i = size;
    	while(i>=2)
    	{
    		swap(i,1);
    		i--;
    		size--;
    		heapifyDown(1);
    	}
    }
    
    int[] getSortedOrder()
    {
    	return H;
    }
}