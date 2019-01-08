package apriori;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NaiveBayes {

	int class_label;DT root;String input[][];int no_attr;
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		NaiveBayes dt = new NaiveBayes();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("enter total no of attributes");
		dt.no_attr=sc.nextInt();
		System.out.println("enter class label attribute number");
		dt.class_label=sc.nextInt()-1;
	
		dt.input=dt.take_input();
		System.out.println("enter a sample to predict class");
		String s[]=new String[dt.no_attr-1];
		for(int i=0;i<dt.no_attr-1;i++)
			s[i]=sc.next();
		System.out.println(dt.NB(s));
		sc.close();
		

	}
	
	
	String[][] take_input() throws Exception
	{

		// open file input stream
				BufferedReader reader = new BufferedReader(new FileReader("A:/datasets/trail.txt"));

				List<String> input1 = new ArrayList<>();
				String line = null;
				while ((line = reader.readLine()) != null) {
					input1.add(line);
				}
				
	reader.close();
	String temp[][]=new String[input1.size()][no_attr];
	for(int i=0;i<input1.size();i++)
	{
		String t[]=input1.get(i).split(",");
		for(int j=0;j<no_attr;j++)
		{
			temp[i][j]=t[j];
		}
	}

	return temp;
	

}
	
	List<String> getDistinct(String arr[][], int n,int noa) 
	{ 
	   
		 int count=0;List<String> dis=new ArrayList<String>();
	    // Pick all elements one by one 
	    for (int i = 0; i < n; i++) 
	    { 
	        // Check if the picked element  
	        // is already printed 
	        int j; int y=0;
	        for (j = 0; j < i; j++) 
	        {
	        	if (arr[i][noa].equals(arr[j][noa]))
	        	{
	        		y=1;break;
	        	}
	        }
	             
	  
	        // If not printed earlier,  
	        // then print it 
	        if(y==0)
	        {
	        //if (i == j) 
	        dis.add(arr[i][noa]);
	       
	        }
	    }
	    return dis;
	} 
	 
	 int getCount(String arr[][],int n,int noa,String s)
	 {
		 int c=0;
		 for(int i=0;i<n;i++)
		 {
			 if(arr[i][noa].equals(s))
				 c++;
				 
		 }
		 return c;
	 }

	
String NB(String a[])
{
	List<String> c=getDistinct(input,input.length,class_label);
	double p[]=new double[c.size()];double p1=1,p2=1;float cc=0;float c1=0,c2=0;
			for(int i=0;i<c.size();i++)
			{
				p1=1;p2=1;
				for(int j=0;j<a.length;j++)
				{
					c1=0;c2=0;
					List<String> ia=getDistinct(input,input.length,j);
					
					for(int k=0;k<input.length;k++)
					{
					
						if(input[k][class_label].equals(c.get(i)))
						{
							
							c1++;
							if(a[j].equals(input[k][j]))
								{c2++;
								}
						}
						
						
						
					}
					
					p2= ((c2+1)/(c1+ia.size()));
					
					p1 *= p2;
			}		
										
				
			
				p[i]=p1*(c1/input.length);
				
				
				cc=0;
			}
			
			int max=0;
			for(int i=0;i<c.size();i++)
			{
				
				if(p[i]>p[max])
					{
					max=i;
					
					}
			}
		
		
			return c.get(max);
			
			
}
	
	
	
	
}
