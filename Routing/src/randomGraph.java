import java.util.Random;
import java.util.Vector;

public class randomGraph{
	
	Vector<Vector<Integer>> graph;
	Vector<edge> edges;
	int weight[][];
	int degree[];
	public static int n = 5000;
	public static int f = 6;
	public static int p = 20;
	public static int max_weight = 1000000;
	Random r;
	randomGraph()
	{
		
		graph = new Vector<Vector<Integer>>();
		edges = new Vector<edge>();
		weight = new int[n][n];
		degree = new int[n];
		r= new Random();
		for(int i=0;i<n;i++)
		{
			degree[i]=0;
			Vector<Integer> l = new Vector<Integer>();
			graph.addElement(l);
			for(int j=0;j<n;j++)
				weight[i][j]=-1;
		}
	}
	
	public void graphWithfDegree()
	{
		int v;int retry = 0;int rand_weight;
		edge newEdge;
		for(int i=0;i<n;i++)
		{
			retry =0;
			while(degree[i]<6 && retry <=100)
			{
				v= r.nextInt(n);
				if(v==i)
				{
					retry++;
					continue;
				}
				else
					if(degree[v]==6)
					{
						retry++;
						continue;
					}
					else
						if(weight[v][i]!=-1)
							continue;
				        else
						{
							rand_weight = r.nextInt(max_weight);
							graph.get(i).addElement(v);
							graph.get(v).addElement(i);
							degree[v]++;
							degree[i]++;
							weight[i][v] = rand_weight;
							weight[v][i]  = rand_weight;
							newEdge = new edge(i,v,rand_weight);
							edges.addElement(newEdge);
						}
				
			}
			
		}
	}
	void graphWithFixedConnectivity()
	{
		int value,rand_weight;
		edge newEdge;
		for(int i =0;i<n;i++)
		{
			for(int j =i+1;j<n;j++)
			{
				if(i==j)
					continue;
				else
					if(weight[i][j]!=-1)
						continue;
					else
					{
						value = r.nextInt(100);
						if(value<p)
						{
							rand_weight = r.nextInt(max_weight);
							graph.get(i).addElement(j);
							graph.get(j).addElement(i);
							degree[j]++;
							degree[i]++;
							weight[i][j] = rand_weight;
							weight[j][i]  = rand_weight;
							newEdge = new edge(i,j,rand_weight);
							edges.addElement(newEdge);
							
						}
					}
				
					
			}
		}
		
	}
	void connectAll()
	{
		int rand_weight; edge newEdge;
		for(int i=0;i<n-1;i++)
		{
			if(weight[i][i+1]==-1)
			{
				rand_weight = r.nextInt(max_weight);
				graph.get(i).addElement(i+1);
				graph.get(i+1).addElement(i);
				degree[i]++;
				degree[i+1]++;
				weight[i][i+1] = rand_weight;
				weight[i+1][i]  = rand_weight;
				newEdge = new edge(i,i+1,rand_weight);
				edges.addElement(newEdge);
			}
		}
	}
	Vector<Vector<Integer>> getGraph()
	{
		return graph;
	}
	int[] getDegree()
	{
		return degree;
	}
	int[][] getWeights()
	{
		return weight;
	}
	Vector<edge> getEdges()
	{
		return edges;
	}
}
