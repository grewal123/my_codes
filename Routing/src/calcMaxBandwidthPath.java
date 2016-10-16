import java.util.Vector;

public class calcMaxBandwidthPath{
	Vector<Vector<Integer>> graph;
	Vector<edge> treeEdges;
	Vector<edge> edges;
	int degree[];
	int weight[][];
	int treeWeight[][];
	Vector<Vector<Integer>> tree;
	int status[];
	int stage[];
	public static int unseen = 0;
	public static int fringe = 1;
	public static int intree = 2;
	public  int n;
	int s,t;
	int cap[],dad[],rank[];
	public int min(int a,int b)
	{
		if(a<b)
			return a;
		else 
			return b;
	}
	public calcMaxBandwidthPath(Vector<Vector<Integer>> inGraph,int inDegree[],int inWeight[][],Vector<edge> inEdges, int s ,int t) {
		// TODO Auto-generated constructor stub
		graph = inGraph;
		edges = inEdges;
		degree = inDegree;
		weight = inWeight;
		n = degree.length;
		treeEdges =new Vector<edge>();
		tree = new Vector<Vector<Integer>>();
		treeWeight = new int[n][n];
		cap = new int[n];
		dad = new int[n];
		rank = new int[n];
		status = new int[n];
		stage = new int[n];
		this.s = s;
		this.t = t;
		for(int i=0;i<n;i++)
		{
			dad[i] = -1;
			Vector<Integer> l = new Vector<Integer>();
			tree.addElement(l);
		}
	}
	void usingDjiskstraWithOutHeap()
	{
		for(int i =0;i<n;i++)
		{
			status[i] = unseen;
		}
		status[s] = intree;
		int v;
		for(int i=0;i<graph.get(s).size();i++)
		{
		    v = graph.get(s).elementAt(i);
			status[v] = fringe;
			cap[v] = weight[s][v];
			dad[v] = s; 
		}
		int w;
		while(status[t]!=intree)
		{
			v = getMaxFringe();
			status[v] = intree;
			for(int i = 0;i<graph.get(v).size();i++)
			{	
				w= graph.get(v).elementAt(i);
				if(status[w]==unseen)
				{
					status[w] = fringe;
					dad[w]    = v;
					cap[w]    = min(cap[v],weight[v][w]);
				}
				else
					if(status[w]==fringe && cap[w]<min(cap[v],weight[v][w]))
					{
						dad[w] = v;
						cap[w] = min(cap[v],weight[v][w]);
					}
			}
		}
	}
	int getMaxFringe()
	{
		int v=0,max=0;
		for(int i=0;i<n;i++)
		{
			if(status[i]==fringe && weight[i][dad[i]]>max)
			{
				max = weight[i][dad[i]];
				v   = i;
			}
				
		}
		
		return v;
	}
	void usingDjiskstraWithHeap()
	{
		for(int i =0;i<n;i++)
		{
			status[i] = unseen;
		}
		status[s] = intree;
		int v;
		Heap heap = new Heap();
		for(int i=0;i<graph.get(s).size();i++)
		{
		    v = graph.get(s).elementAt(i);
			status[v] = fringe;
			heap.insert(weight[s][v], v);
			cap[v] = weight[s][v];
			dad[v] = s; 
		}
		int w;
		while(status[t]!=intree)
		{
			v = heap.extractMax();
			status[v] = intree;
			for(int i = 0;i<graph.get(v).size();i++)
			{	
				w= graph.get(v).elementAt(i);
				if(status[w]==unseen)
				{
					status[w] = fringe;
					dad[w]    = v;
					cap[w]    = min(cap[v],weight[v][w]);
					heap.insert(cap[w], w);
				}
				else
					if(status[w]==fringe && cap[w]<min(cap[v],weight[v][w]))
					{
						dad[w] = v;
						cap[w] = min(cap[v],weight[v][w]);
						for(int j =1;j<=heap.size;j++)
						{
							if(heap.H[j]==w)
							{
								heap.D[j] = cap[w];
								heap.heapifyUp(j);
							}
						}
					}
			}
		}
	}
	
	void usingKruskal()
	{
		//Add Logic to sort Edges here
		int order[]  = sortEdges();
		for(int i=0;i<n;i++)
		{
			makeSet(i);
		}
		int s1,s2;
		for(int i=edges.size();i>0;i--)
		{
			s1 = find(edges.elementAt(order[i]).v1);
			s2 = find(edges.elementAt(order[i]).v2);
			if(s1!=s2)
			{
				union(s1,s2);
				treeEdges.addElement(edges.elementAt(order[i]));
				treeWeight[edges.elementAt(order[i]).v1][edges.elementAt(order[i]).v2] = edges.elementAt(order[i]).weight;
				treeWeight[edges.elementAt(order[i]).v2][edges.elementAt(order[i]).v1] = edges.elementAt(order[i]).weight;
				tree.get(edges.elementAt(order[i]).v1).addElement(edges.elementAt(order[i]).v2);
				tree.get(edges.elementAt(order[i]).v2).addElement(edges.elementAt(order[i]).v1);
				
			}
			
		}
		
	}
	
	void findPath()
	{
		
		for(int i=0;i<n;i++)
			stage[i] = 0;
				dad[s] =-1;
				DFS(s);
	}
	
	void DFS(int v)
	{
		stage[v] = 1;int w=-1;
		for(int i=0;i<tree.get(v).size();i++)
		{
			w = tree.get(v).elementAt(i);
			if(stage[w]==0)
			{
				dad[w] = v;
				DFS(w);
			}
			
		}
		stage[v]=2;
	}
	
	int getMaxBandwidthValueFromKruskal()
	{
		int min =randomGraph.max_weight +1;int par;
		int v =t;
		while(dad[v]!=-1)
		{
			par = dad[v];
			if(min > weight[par][v])
			{
				min = weight[par][v];
			}
			v = dad[v];
			
		}
		
		return min;
	}
	
	int[] sortEdges()
	{
		Heap heap = new Heap(edges.size()+1);
		for(int i=0;i<edges.size();i++)
		{
			heap.insert(edges.elementAt(i).weight, i);
		}
		heap.heapSort();
		int H[] = heap.getSortedOrder();
		return H;
	}
	
	void makeSet(int v)
	{
		dad[v] = -1;
		rank[v] = 0;
	}
	
	void union(int v1,int v2)
	{
		if(rank[v1]>rank[v2])
			dad[v2] = v1;
		if(rank[v1]<rank[v2])
			dad[v1] = v2;
		if(rank[v1]==rank[v2])
			{
				dad[v2] = v1;
				rank[v1]++;
			}
	}
	int find(int v)
	{
		int w = v;
		while(dad[w]!=-1)
		{
			w= dad[w];
		}
		return w;
	}
	int getMaxBandwidthValue()
	{
		return cap[t];
	}
	int[] getMaxBandwidthpath()
	{
		return dad;
	}
	

}