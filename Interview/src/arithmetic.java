import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class arithmetic{
	
	static boolean flag= false;
	static boolean flag2  =false;
	public  static void main(String args[])
	{
		
		
		Vector<Integer> list = new Vector<Integer>();
		
		int target=0;
	        String line = "";
	        try {
	        	 InputStreamReader isr = new InputStreamReader(System.in);
	 	        BufferedReader br = new BufferedReader(isr);
				line = br.readLine();
				 isr.close();
				 String[] parts = line.split(" ");
				 target = Integer.parseInt(parts[0]);
				 for(int i=1;i<parts.length;i++)
				 {
					 list.addElement(Integer.parseInt(parts[i]));
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		
		if(!explore(list,target))
			System.out.println("None");
		else
			System.out.println();

	}
	
	 static boolean  explore(Vector<Integer> list,Integer target)
	{
		int capacity = list.size();
		if(capacity!=0 && target==0)
			return false;
		if(capacity==0 && target==0)
		{
			System.out.print("(");
			flag2 =true;
			return true;
		}
			
		int n;
		for(int i =0;i<capacity;i++)
		{
			n = list.elementAt(i);
			int r1 = target + list.elementAt(i);
			list.remove(i);
			if(explore(list, r1))
				{
				if(flag2==true)
				{
					System.out.print("-"+n+")");
					flag2=false;
				}
				else
					System.out.print("-"+n);
				
				flag = true;
				return true;
				
				}
			list.add(i, n);
			n = list.elementAt(i);
			int r2 = target - list.elementAt(i);
			list.remove(i);
			if(explore(list, r2))
			{
				
				if(flag  ==false)
					System.out.print(n);
				else
					if(flag2==true)
					{
						System.out.print("+"+n+")");
						flag2=false;
					}
					else
						System.out.print("+"+n);
				flag =true;
				return true;
			}
			list.add(i, n);
			n = list.elementAt(i);
			int r3 = target * list.elementAt(i);
			list.remove(i);
			if(explore(list, r3))
				{
				if(flag2==true)
					{
						System.out.print("/"+n+")");
						flag2=false;
					}
				else
					System.out.print("/"+n);
					flag = true;
					return true;
				}
			list.add(i, n);
			n = list.elementAt(i);
			if((target%list.elementAt(i))==0)
			{
				int r4 = target / list.elementAt(i);

				list.remove(i);
				if(explore(list, r4))
					{
						if(flag2==true)
						{
							System.out.print("*"+n+")");
							flag2 = false;
						}
						else
							System.out.print("*"+n);
						flag = true;
						return true;
					}
				list.add(i, n);
			}
		}
		
		return false;
		 
		 
		 
		 
		 
		 
	}
	
}