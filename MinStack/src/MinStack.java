public class MinStack {
    static int size1 = 0;
    static int size2 = 0;
    static int stack1[] = new int[20000];
    static int stack2[] = new int[20000];
    static int min = 5000;
    public static void main(String args[])
    {
    	push(395);
    	getMin();
    	top();
    	getMin();
    	push(276);
    	push(29);
    	getMin();
    	push(-482);
    	getMin();
    	pop();
    	push(-108);
    	push(-251);
    	getMin();
    }
    public static void push(int x) {
        
        size1++;
        stack1[size1-1] = x;
        size2++;
        if(size2==1)
        {
            stack2[0] = x;
            min = x;
            return;
        }
        if(x<min)
        {
            stack2[size2-1] = x;
            min = x;
        }
        else
        stack2[size2-1] = stack2[size2-2];
        return;
    }

    public static void pop() {
        
       // int ret = stack1[size1-1];
        size1--;size2--;
        min = stack2[size2-1];
       // return ret;
    }

    public static void top() {
        
        System.out.println(stack1[size1-1]);
    }

    public static void getMin() {
        
        System.out.println(stack2[size2-1]);
    }
}
