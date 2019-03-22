import java.util.Scanner;
import java.lang.Math;
import java.util.*;


public class ManhattenInt
{
 public static int [][] StartPositions = {{1,2,3},{4,5,6},{7,8,0}} ;
 public static int [][] EndPositions = {{1,8,3},{4,5,6},{7,2,0}} ;
 //public static 
 public static void main(String [] args)
 {
	//StartPositions={{1,2,3},{4,5,6},{7,8,0}} ;
	//EndPositions = {{1,2,3},{4,5,6},{8,7,0}} ;
		int  Steps=0;//= Manhatten(start,end);
	Steps=ManhattenInt(StartPositions,EndPositions);
		System.out.print("\n"+Steps);

 }
 
public static int ManhattenInt(int [][] start, int[] [] end) //Take in 2 arrays of start/end positions reutrn the cost of getting there
 {
  
  int steps []=new int[start.length*start.length]; //how many steps each incorrect position needs to move, length = number of items to move
 
	int total=0;
	int count=0;
  //calculating number of steps away each position is
  for(int i=0;i<start.length;i++)
	{
	  for(int j=0;j<end.length;j++)
	  {
			if(start[i][j]==(end[i][j]))//if the say 0,0 matches 0,0 fine
			{
				count++;
			}
			else  // if it doesnt match loop through end matrix to find match 
			{	 
				for(int k=0;k<end.length;k++)
				{
					for(int l=0;l<end.length;l++)
					{
						
						if(start[i][j]==(end[k][l]))
						{
						steps[count]=Math.abs((i - k)+(j - l));
						total+=steps[count];
						count++;
						
						}
					}
				}
			}
		}
	}

  //g(n)+h(n)
  return total;
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
}