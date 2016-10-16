import java.util.HashMap;

public class PatternMatching{
	public static void main(String args[])
	{
		String pattern = "abba";
		String str = "dog cat cat dog";
		System.out.println(wordPattern(pattern,str));
		
	}
public static boolean wordPattern(String pattern, String str) {
        
        String array[] = str.split(" ");
        HashMap<Character, String> hmap = new HashMap<Character, String>();
        int i=0;boolean flag=true;
        if(array.length!=pattern.length())
        return false;
        for(i=0;i<array.length;i++)
        {
            if(hmap.containsKey(pattern.charAt(i)))
            {
            	
                if(!(hmap.get(pattern.charAt(i)).equals(array[i])))
                {
                	System.out.println("hahs map value for key "+ pattern.charAt(i) + " is " + hmap.get(pattern.charAt(i)));
                	System.out.println("and array string contains " + array[i] + " at " + i);
                    flag = false;
                    break;
                }
            }
            else
            {
                hmap.put(pattern.charAt(i),array[i]);
            }   
        }
        return flag;
    }
    
}