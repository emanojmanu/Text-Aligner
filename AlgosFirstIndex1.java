import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AlgosFirstIndex1 {

	ArrayList<String> inp;
	int m;
	int lengths[];
	int LengthsFromStart[];
	public AlgosFirstIndex1(ArrayList<String> i,int m)
	{
		inp=i;
		this.m=m;
		lengths=new int[i.size()];
		LengthsFromStart=new int[i.size()]; //cummulative lengths of all the words from the starting
     	//	Iterator<String> iter=i.iterator();
		for(int j=0;j<i.size();j++) 
		{
			lengths[j] = i.get(j).length();
			if(j!=0)
			{
				LengthsFromStart[j]=LengthsFromStart[j-1]+lengths[j];
			}
			else
			{
				LengthsFromStart[j]=lengths[j];
			}
		}
	}//0......last index words need to be  allocated

	public int penaltyCalculate(int LastIndex) {
		// base case
		// int baseindex=m-LastIndex-LengthsFromStart[LastIndex]
		// L[baseindex]=0; for all 0<i<baseinex
		int[] L = new int[this.inp.size()];
		int basebreakpoint = 0;  //index of the word from end which can fit in the last line 
		                            //basebreakpoint,basebreakpoint+1,......n-1
		// System.out.println("inside penalty calc");
		int ii = this.inp.size()-1;
		/*int l=1;*/
		int dummy=m;
		while (ii>=0&&dummy-1 - lengths[ii] > 0)//dummy-{space of one word}-length of that word
		{
				dummy=dummy - 1 - lengths[ii];
				basebreakpoint = ii;
				ii--;
			//l++;
		}
		// for()
		//basebreakpoint--;
		L[LastIndex-1] = 0;//not needed
		int penalty = 0;
		int[] penaltyarra=new int[inp.size()];//stores the minimum penalty of all the words as starting index in some line
		int[] penaltyspaces=new int[inp.size()];//
		int[] numberOfWords=new int[inp.size()];//number of words that fit in a line with numberOfWords[i] as starting word 
		// penalty=individulaPenalty(startIndex, end)
		int parent[]=new int[inp.size()]; //{k+1} //parent[i] gives the starting word's index of the next line with ith word as current line starting index   
		for (int i = LastIndex-2; i >=0; i--) {//lastindex-2
			
			L[i] = Integer.MAX_VALUE;
			int min = L[i];
			for (int k = i; k <LastIndex-1; k++) 
			{
				int leftOver;
				leftOver = L[k+1];
				int l1=LengthsFromStart[k];
				int l2=LengthsFromStart[i];
				int pl=l1-l2;	
				if (m - (k - i) - (LengthsFromStart[k] - LengthsFromStart[i]+lengths[i]) >= 0)
				{//condition for fitting
					penalty = individulaPenalty(i, k,basebreakpoint);
					// returns the penalty for the line from i...k words
					
					if (min>=penalty + leftOver)
					{
						min = penalty + leftOver; 
						parent[i]=k+1;//next line can start at k+1
						penaltyarra[i]=penalty;// penalty of line with i as starting 
						penaltyspaces[i]=(int)Math.cbrt(penalty);// penalty spaces needed in line with i...k
						numberOfWords[i]=k-i+1;// nu of words in line i...k
					}	
				}
				else{
					break;
				}
				
			}
		//	int hk=(int) Math.cbrt(27);
		/*	if(i==10)
			{
				System.out.println();
			}
			*/
			L[i] = min;
		}
		System.out.println(L[0]);
		//System.out.println();
		//
		int index=0,jl=0;
		while(index<=LastIndex-1)
		{
			/*if(jl==5)
			{
				System.out.print("");
			}*/
			System.out.println();	
			int par=parent[index];// to print the last line words
			if(index>=basebreakpoint)
			{
				while(index<=LastIndex-1)
				{
				System.out.print(this.inp.get(index)+" ");
				index++;
				}
				break;
			}
			int numberOfWordstoadjust=numberOfWords[index];// to  number of words to adjust 
			int penaltySPacesto=penaltyspaces[index];//to add the number of spaces
			int quotient=0;//Interval where a space can be added
			boolean flagQuotient1=true;
		if(penaltySPacesto!=0)
		{
			quotient=(numberOfWordstoadjust-1)/penaltySPacesto;
		}
		/*else{
		    index=par;
		    System.out.print(this.inp.get(index));
				index++;
				wordcount++;
				if(index<par)
				{
					System.out.print(" ");
				}
			continue;
		}*/
			int addedSpaces=0;
			int wordcount=0;
			while(index<par)//=>
			{
				System.out.print(this.inp.get(index));
				index++;
				wordcount++;
				if(index<par)
				{
					System.out.print(" ");
				}
				// to handle only required spaces are added and at at wordcount%quotient places only added
				if(penaltySPacesto!=1&&addedSpaces<penaltySPacesto&&quotient!=0&&wordcount%quotient==0)
				{
					if(quotient!=1)
					{
					System.out.print("+");
					addedSpaces++;
					}
					else{// to handle when u have mmore spaces factor as penalty then available spaces
						if(flagQuotient1)
						{// add the sapces alternatively
						System.out.print("+");
						addedSpaces++;
						flagQuotient1=false;
						}
						else{
							flagQuotient1=true;
						}
					}
				}
				else if(penaltySPacesto==1&&addedSpaces<penaltySPacesto&&quotient!=0&&(par-(index-wordcount))/2==wordcount)
				{
					System.out.print("+");
					addedSpaces++;
				}
				else if(addedSpaces<penaltySPacesto&&quotient==0){
					// when number of spaces to add> number of words
					//System.out.println("inside crazy loop");
					int numberofSpacesateachslot=(penaltySPacesto/(numberOfWordstoadjust-1))+1;
					int counter=0;
					while(counter<numberofSpacesateachslot&&addedSpaces<penaltySPacesto)
					{
						System.out.print("+");
						counter++;
						addedSpaces++;
					}
				}
			}
			
			
			
			/*int kl=0;
			while(kl<penaltySPacesto)
			{
				System.out.print("*");
				kl++;
			}*/
			jl++;
			//System.out.println("lines "+jl);		
			//System.out.println();
			//index=par+1;//--	
		}
		/*if(index>=basebreakpoint)
		{
			
			//break;
		}*/
		System.out.println();
		/*int sum=penaltyarra[0];
		int i=parent[0];
		while(i!=0)
		{
			sum=sum+penaltyarra[i];
			i=parent[i];
		}*/
	//	System.out.println("v xv" +sum);
		//  int index=LastIndex-1;
		return L[0];
	}
	public int individulaPenalty(int startIndex,int end,int breakbase)
	{		
		int wordsLength=0;
		int counter=startIndex;		//for all words that can fit in to last line
		if(startIndex>=breakbase)//base case
		{
			return 0;
		}/*
		while(counter<=end)
		{
			wordsLength=wordsLength+this.inp.get(counter).length();
		    counter++;
		}
		*/
		
		wordsLength=LengthsFromStart[end] - LengthsFromStart[startIndex]+lengths[startIndex];
		
		int penalty=m+startIndex-end-wordsLength;
		penalty=(int) Math.pow(penalty, 3);
		return penalty;
	}
	  public static void main(String[] args) throws FileNotFoundException {
			Scanner in;
			int M = 80;
			ArrayList<String> input=new ArrayList<>();
			File g=new File(".");
		//	System.out.println("File in ");
			if (args.length > 0) {
			    File inputFile = new File(args[0]);
			//    System.out.println(args[0]+" file"+inputFile.canRead());
			    in = new Scanner(inputFile)/*.useDelimiter("\t")*/;
			    if (args.length > 1)//125 27 64 64
			    { M = Integer.parseInt(args[1]); }
			} else {
			    in = new Scanner(System.in)/*.useDelimiter("\t")*/;
			}
		//	System.out.println(1);
			while(in.hasNext())
			{
				String h=in.next();
				input.add(h);
			/*if(!h.equals("end"))
				{
				input.add(h);
				}
				else{
					break;
				}*/
			}
			//System.out.println("started");
			AlgosFirstIndex1 al=new AlgosFirstIndex1(input, M);
			int ip=al.penaltyCalculate(input.size());
			//System.out.println("outpt   "+ip);
	  }
}
