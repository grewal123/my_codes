// writing on a text file
#include <iostream>
#include <string>
#include <fstream>
#include<vector>
#include<map>
#include<stack>
using namespace std;
string trimSpaces(string input)
{
	cout<<"reached trim"<<endl;
	string str = input;
	string whiteSpaces = ("\t\f\n\v\r");
	str.erase(str.find_last_not_of(whiteSpaces)+1);
	str.erase(0, str.find_first_not_of(whiteSpaces));
	cout<<"reached end of trim"<<endl;
	return str;
}

vector<string> splitWord(string str, string splitter)
{
	cout<<"reached start of split"<<endl;
	vector<string> internal;
	size_t index;
	while((index=str.find(splitter))!=string::npos) {
		internal.push_back(trimSpaces(str.substr(0,index)));
		str = str.substr(index+splitter.size(), str.size());
	}
	internal.push_back(trimSpaces(str));
	cout<<"reached end of splitword"<<endl;
	return internal;
	
}
vector<string> getPostfix(string whereClause)
{
	vector<string> tokens = splitWord(whereClause," ");
	for(int i = 0;i<tokens.size();i++)
	cout<<tokens[i]<<endl;
	stack<string> opStack;
	vector<string> postFix;
	map<string,int> opMap;
	
	opMap["/"]  = 5;
	opMap["*"]  = 5;
	opMap["+"]  = 4;
	opMap["-"]  = 4;
	opMap["="]  = 3;
	opMap[">"]  = 3;
	opMap["<"]  = 3;
	opMap["NOT"]  = 2;
	opMap["AND"]  = 1;
	opMap["OR"]  = 0;
	map<string,int>::iterator it;
	cout<<"entering loop"<<endl;
	for(int i=0;i<tokens.size();i++)
	{
		it = opMap.find(tokens[i]);
		if(it==opMap.end() && tokens[i]!="(" && tokens[i]!= ")")
		{
			postFix.push_back(tokens[i]);
		}
		else if(tokens[i]=="(")
			{
				opStack.push(tokens[i]);
			}
		else if(it != opMap.end())
		{
			while(opStack.size()>0  && opStack.top()!="(")
			{
				string topOp = opStack.top();
				if(opMap[tokens[i]]<opMap[topOp])
				{
					postFix.push_back(opStack.top());
					opStack.pop();
				}
				else
				break;
			}
			opStack.push(tokens[i]);
		}
		else if(tokens[i]==")")
			{
				while ((opStack.size() > 0) && opStack.top()!="(")
	            {
					postFix.push_back(opStack.top());
					opStack.pop();
	            }
	            if (opStack.size() > 0)
	                opStack.pop(); // popping out the left brace '('				
			}
		
	}
	cout<<"end of for loop"<<endl;
	while(opStack.size() > 0)
			{
				postFix.push_back(opStack.top());
				opStack.pop();	
			}
	
	return postFix;
	
}
	
int main () {
  
  string whereClause = "a + b * c - d";
  
  vector<string> res  = getPostfix(whereClause);
  
  for(int i=0;i<res.size();i++)
  cout<<res[i]<<endl;
  
  return 0;
}