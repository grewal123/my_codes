#include<stdio.h>
#include<iostream>
using namespace std;
char* getHint(char* secret, char* guess) {
    int a[500][500];
    for(int i=0;i<500;i++)
    for(int j=0;j<500;j++)
    a[i][j] = -1;
    int C[10];
    for(int i=0;i<10;i++)
    C[i] = 0;
    int pos = 0;
    int itr;
    //cout<<secret[400]<<"\n";
   
    while(secret[pos]!= '\0')
    
    {   //cout<<secret[pos]<<"at posiiton"<<pos<<"\n";
    	itr=0;
    	while(a[secret[pos] - '0'][itr]!=-1)
    	itr++;
        a[secret[pos] - '0'][itr] = pos ;
        pos++;
    } 
    // cout <<"done"<<"\n";
    pos =0;
    int B=0,A=0;
    
    int flag =0;
    while(guess[pos]!= '\0')
    { 
    	itr =0;
        while(a[guess[pos] - '0'][itr]!=-1)
       {
        if(a[guess[pos] - '0'][itr] == pos)
        {
        	flag =1;
        	break;
        	
        }
        itr++;
       }
       if(flag==1)
       {
       		A++;
       	//	cout<<"adding bulls"<<guess[pos]<<"\n";
       }
       else
        if(a[guess[pos] - '0'][0]!=-1 && C[guess[pos] - '0'] == 0)
        {
        	B++;
        	C[guess[pos] - '0'] = 1;
        //	cout<<"adding Cows"<<guess[pos]<<"\n";
        }
        pos++;
        flag = 0;
    }
    static char ret[10] ;
    ret[0] = '\0';
   // cout<<"A is"<<A<<"\n";
    char strA[15];
    sprintf(strA, "%d", A);
    char strB[15];
    sprintf(strB, "%d", B);
    strcat(ret,strA);
    strcat(ret,"A");
    strcat(ret,strB);
    strcat(ret,"B");
    
    
    return ret;  
}


int main()
{
 	//char secret[] = "17556104017908538866216012733562391312370364164753414913099831164577140108378143415376650728444659024878626036827668639994464707182631830178609103014897359209028887381064675156821635355831893967952907338745767322325798116382683509798903437033410293566003780312736165662615106227593457208686194604627517389211700667532015020887634217276619270115660625523450595634639344408561537725929726350375899679273522114374663660280959315338095222404629117922755113821970129005136384076264837579099762208763846744";
    //char guess[] = "07748673844341392912632836731867633214455549725460733060664059885610952387341558665365698113864718504316283529520035129205876687680317811202356924968240314796692330033898560796484937510903644964305613261300188599892908431777611958641457941879064075349399240053023510882695828192479684677396463258994710749877319250148120228914112452137479839822015532295957418917801692714357523787342986086703132050124914770205742214045873846013116608825660200462833288493449631485108536861247630182414800487188388505";
    char secret[] = "1122";
    char guess[] = "2211";
    cout<<strlen(secret)<<"   "<<strlen(guess)<<"\n";
 	//char* b =getHint(secret,guess);
 	// char    abc[27] = ;
    //char    *ptr = abc;
    int a[27] = {0,1,2,3,4,5,6};
    int* ptr2  = a;
    cout<<a<<"\n";
 	return 1;
}