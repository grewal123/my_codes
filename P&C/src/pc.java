public class pc{
	public static void main(String args[])
	{
		pc p = new pc();
		System.out.println("aswewr is" + p.uniquePaths(40,1));
		
	}
	
	public long uniquePaths(long m, long n) {
        long d = fac(m)*fac(n);
        long res = fac(m+n) / d;
        return res;
        
    }
    
    long fac(long n)
    {
        if(n==1)
        return 1;
        long f = fac(n-1);
        long res = n*f;
        return res;
    }
}