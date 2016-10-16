import java.io.*;
public class UglyNumber {
	public static void  main(String args[])
	{
		if(isUgly(-1000))
			System.out.println("ugly");
		else
			System.out.println("not ugly");
		
	}
    public static boolean isUgly(int num) {
        
        int[] ugly = new int[100000];
        ugly[0] = 1;
        int i2 =0,i3 =0,i5=0;
        
        int next_mul_2 = ugly[i2]*2;
        int next_mul_3 = ugly[i3]*3;
        int next_mul_5 = ugly[i5]*5;
        int next_ugly;
        int i=1,flag=0;
        if(num<0)
        num = num *-1;
        while(i<100000)
        {
            next_ugly = min(next_mul_2,next_mul_3,next_mul_5);
            ugly[i]= next_ugly;
            if(next_ugly ==next_mul_2 )
            {
                i2++;
                next_mul_2 = ugly[i2]*2;
            }
            if(next_ugly ==next_mul_3 )
            {
                i3++;
                next_mul_3 = ugly[i3]*3;
            }
            if(next_ugly ==next_mul_5 )
            {
                i5++;
                next_mul_5 = ugly[i5]*5;
            }
            if(ugly[i]==num)
            {
                flag=1;
                break;
            }
            if(ugly[i]>num)
            {
                break;
            }
            i++;
            
        }
        
        if(flag==1)
        return true;
        else
        return false;
        
        
        
    }
    static int min(int a,int b,int c)
    {
        if(a<b)
        {
            if(a<c)
                return a;
            else
                return c;
        }
        else
            if(b<c)
                return b;
            else
                return c;
    }
}