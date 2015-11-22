import java.util.*;
public class Routing
{
	public static  void main(String args[])
	{
		randomGraph newGraph1,newGraph2;
		Vector<Vector<Integer>> toBeTestedGraph;
		int[] degree1,degree2;int [][] weight1,weight2;Vector<edge> edges1,edges2;
		int[] toBeTestedDegree;int [][] toBeTestedWeight;Vector<edge> toBeTestedEdges;
		calcMaxBandwidthPath calc,calc2,calc3;
		int[] dad,dad2,dad3;
		int maxBandwidthValue,maxBandwidthValue2,maxBandwidthValueFromKruskal;
		int s,t;
		Random r = new Random();
		long startTime,endTime;
		for(int i =0;i<5;i++)
		{
			System.out.println();
			System.out.println("----For "+ (i+1) + " Graph Pair----");
			
			//Generating Dense Graph
			newGraph1 = new randomGraph();
			newGraph1.graphWithFixedConnectivity();
			newGraph1.connectAll();
			Vector<Vector<Integer>> denseGraph= newGraph1.getGraph(); 
			degree1 = newGraph1.getDegree();
		    weight1 = newGraph1.getWeights();
			edges1 = newGraph1.getEdges();
			
			//Generating Sparse Graph
		    newGraph2 = new randomGraph();
			newGraph2.graphWithfDegree();
			newGraph2.connectAll();
			Vector<Vector<Integer>> sparseGraph= newGraph2.getGraph(); 
			degree2 = newGraph2.getDegree();
			weight2 = newGraph2.getWeights();
		    edges2 = newGraph2.getEdges();
		    int k; int t1,s1;
		    for(int j=0;j<5;j++)
		    {
		    	//generating random s and t
			    s=r.nextInt(randomGraph.n);
			    t=r.nextInt(randomGraph.n);
			    System.out.println();
			    System.out.println("Values of s and t are " + s + " "+ t );
			    
			    //setting first graph to be tested to sparse graph
		    	toBeTestedGraph = sparseGraph;
		    	toBeTestedDegree = degree2;
		    	toBeTestedEdges  = edges2;
		    	toBeTestedWeight = weight2;
		    	k=0;
		    	while(k<=1)
		    	{	
		    		System.out.println();
		    		if(k==0)
		    			System.out.println("---For Sparse Graph----");
		    		if(k==1)
		    			System.out.println("---For Dense Graph----");
		    		
		    		//Without Using Heap
					System.out.println("-----------Without Using Heap---------");
					calc =new calcMaxBandwidthPath(toBeTestedGraph, toBeTestedDegree, toBeTestedWeight, toBeTestedEdges, s, t);
					startTime = System.nanoTime();
					calc.usingDjiskstraWithOutHeap();
					maxBandwidthValue = calc.getMaxBandwidthValue();
					endTime = System.nanoTime();
					dad = calc.getMaxBandwidthpath();
					System.out.println("Max Bandwidth Valus is "+ maxBandwidthValue);
					t1 =t;s1 =s;
					while(true)
					{
						System.out.print(t1+" <--- ");
						if(t1==s1)
							break;
						t1= dad[t1];
					}
					System.out.println();
					System.out.println("The time elapsed is :" + (endTime-startTime)/1000000.0);
					
					//With Using heap
					t1=t;s1=s;
					calc2 =new calcMaxBandwidthPath(toBeTestedGraph, toBeTestedDegree, toBeTestedWeight, toBeTestedEdges, s, t);
					System.out.println();
					System.out.println("-----------Using Heap---------");
					startTime = System.nanoTime();
					calc2.usingDjiskstraWithHeap();
					maxBandwidthValue2 = calc2.getMaxBandwidthValue();
					endTime = System.nanoTime();
					dad2 = calc2.getMaxBandwidthpath();
					System.out.println("Max Bandwidth Valus is "+ maxBandwidthValue2);
					while(true)
					{
						System.out.print(t1+" <--- ");
						if(t1==s1)
							break;
						t1= dad2[t1];
					}
					System.out.println();
					System.out.println("The time elapsed is :" + (endTime-startTime)/1000000.0);
					
					//With Using Kruskal's Algorithm
					t1=t;s1=s;
					calc3 =new calcMaxBandwidthPath(toBeTestedGraph, toBeTestedDegree, toBeTestedWeight, toBeTestedEdges, s, t);
					System.out.println();
					System.out.println("-----------Using Kruskal---------");
					startTime = System.nanoTime();
					calc3.usingKruskal();
					calc3.findPath();
			        maxBandwidthValueFromKruskal = calc3.getMaxBandwidthValueFromKruskal();
			        endTime = System.nanoTime();
			        dad3 = calc3.getMaxBandwidthpath();
					System.out.println("Max Bandwidth Valus is "+ maxBandwidthValueFromKruskal);
					while(true)
					{
						System.out.print(t1+" <--- ");
						if(t1==s1)
							break;
						t1= dad3[t1];
					}
					System.out.println();
					System.out.println("The time elapsed is :" + (endTime-startTime)/1000000.0);
					
					//To check if there is any mismatching between results in 3 Algorithms
					if(maxBandwidthValue!=maxBandwidthValue2 || maxBandwidthValue!=maxBandwidthValueFromKruskal || maxBandwidthValue2!=maxBandwidthValueFromKruskal)
						break;
					
					//Setting the graph to dense Graph
					toBeTestedGraph = denseGraph;
			    	toBeTestedDegree = degree1;
			    	toBeTestedEdges  = edges1;
			    	toBeTestedWeight = weight1;
			    	k++;
			    	System.out.println();
				
		    	}
		    	
		    }
		    
		}
	}
}


