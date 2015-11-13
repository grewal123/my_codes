// writing on a text file
#include <iostream>
#include <fstream>
using namespace std;

int main () {
  ofstream myfile ("example.txt");
  
  ifstream fin;
fin.open("fakenames.txt", ios::in);

char my_character ;
int number_of_commas = 0;
bool done =false;
int ssn;
	while (!fin.eof() && myfile.is_open()) {
	 if(done==false)
	 { ssn =  rand() % 1200 + 2000;
	  myfile<<"insert into fakessns values(nextval('fakessn_seq'),"; 
	  myfile<<"'"<<ssn<<"'"<<",";
	  done=true;
	 }

	fin.get(my_character);
	
		if (number_of_commas == 8 )
		{
			myfile<<my_character;
		}
		if(my_character==',')
		number_of_commas++;
		if(my_character == '\n')
	{   number_of_commas = 0;
	done = false;
	   
	}
	
	}
	myfile.close();
 /* if (myfile.is_open())
  {
    for (int i = 0; i<1100;i++)
    {
    myfile << "insert into fakerecords values('LisaMConway@fleckens.hu')\n";
    myfile<<number_of_lines;
    
    }
    
  }
  else cout << "Unable to open file";
  */
  return 0;
}