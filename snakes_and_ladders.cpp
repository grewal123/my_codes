#include<cstdio>
#include<stdlib.h>
#include<iostream>
#include<map>
using namespace std;
class board
{
    public:

 board();
 void play();
 void roll_dice(int &curr,int turn) ;
 int mat[100];
};
board::board()
{
    cout<<"intializing board"<<"\n";
    for(int i =0;i<100;i++)
    mat[i] = 0;
    mat[98] = 28;
    mat[95] = 24;
    mat[92] = 51;
    mat[83] = 19;
    mat[73] = 1;
    mat[69] = 33;
    mat[64] = 36;
    mat[59] = 17;
    mat[55] = 7;
    mat[52] = 11;
    mat[48] = 9;
    mat[46] = 5;
    mat[44] = 22;
    mat[8] = 26;
    mat[21] = 82;
    mat[43] = 77;
    mat[50] = 91;
    mat[54] = 93;
    mat[62] = 96;
    mat[66] = 87;
    mat[80] = 100;
    cout<<"initializing done"<<"\n";
}
void board::roll_dice(int &curr,int turn)
{
	int num;
	num = rand()%6 +1 ;
	if((num + curr) >100)
	return;
	cout<<"Player "<<turn<<" moves from "<<curr<<"to"<<(curr+num)<<"\n";
	curr += num;
	int old = curr;
	if(mat[curr])
		curr = mat[curr];
	if(old<curr)
		cout<<"Player "<<turn<<" hit a ladder from "<<old<<"to"<<curr<<"\n";
	if(curr<old)
		cout<<"Player "<<turn<<" hit a snake from "<<old<<"to"<<curr<<"\n";
}
void board::play()
{	
	int turn=1;
	int curr1 = 0;
	int curr2 = 0;
	while(1)
	{	
		if(turn==1)
		{
			cout<<"player 1 rolls dice"<<"\n";
			
			roll_dice(curr1,1);
			turn =2;
			
	        if(curr1 == 100)
	        {
	        	cout<<"player 1 wins!!!!!"<<"\n";
	        	break;
	        }
		}
		if(turn==2)
		{
			cout<<"player 2 rolls dice"<<"\n";
			roll_dice(curr2,2);
			turn =1;
			
			if(curr2 == 100)
	        {
	        	cout<<"player 2 wins!!!!!"<<"\n";
	        	break;
	        }
		}
	}
	
}
int main()
{
cout<<"lets Play"<<"\n";
board *Board = new board();
Board->play();
return 0;

}
