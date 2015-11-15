#include<iostream>
#include<regex>
using namespace std;
int main()
{ 
	string deleteQuery = "^delete from ([a-zA-Z0-9]*)\\ *((where) (.*))*";
	string conditionQuery = "((.*))\\ *(and)\\ *(.*)";
	regex deletePattern(deleteQuery, regex_constants::icase);
	regex conditionPattern(conditionQuery, regex_constants::icase);
	cmatch attributes;
	string query = "DELETE FROM students WHERE id=3 and name = varinder";
	if(regex_match(query.c_str(),attributes,deletePattern))
	{
	  cout<<"0 "<< attributes[0]<<endl;
	  cout<<"1 "<< attributes[1]<<endl;
	  cout<<"2 "<< attributes[2]<<endl;
	  cout<<"3 "<< attributes[3]<<endl;
	  cout<<"4 "<< attributes[4]<<endl;
	  cout<<"5 "<< attributes[5]<<endl;
	  cout<<"6 "<< attributes[6]<<endl;
	  cout<<"7 "<< attributes[7]<<endl;
		
	}
	string str = attributes[4];
	if(regex_match(str.c_str(),attributes,conditionPattern))
	{
	  cout<<"0 "<< attributes[0]<<endl;
	  cout<<"1 "<< attributes[1]<<endl;
	  cout<<"2 "<< attributes[2]<<endl;
	  cout<<"3 "<< attributes[3]<<endl;
	  cout<<"4 "<< attributes[4]<<endl;
		
	}
	
return 1;
}



