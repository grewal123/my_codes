public class Palindrom{
	
	public static void main(String[] args){
		
		int l =10;
		Palindrom  p = new Palindrom();
		if(p.isPalindrome(l))
			System.out.println("is palindrome");
		else
			System.out.println("not a Palindrome" );
		
		
	}
	
public boolean isPalindrome(int x) {
        
        int i=1,flag = 0;
        
        
        while(x/i>=10)
        {
            i*=10;
        }
        if(x<0)
        return false;
        if(x<10 && x>=0)
        return true;
        int l,r;
        while(i>=10)
        {
            l = x/i;
            r = x%10;
            
            if(l!=r)
            {
                flag = 1;
                return false;
               //break;
            }
            x = x%i;
            x = x/10;
            i=i/100;
        }
        //if(x==10)
        //return false;
        if(flag==1)
        return false;
        else 
        return true;
    }
	
	
}