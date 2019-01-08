package appp;
import java.util.*;
import java.lang.*;
import java.io.*;

public class kmeans {

	public static void main(String[] args) throws FileNotFoundException 
	{
		// TODO Auto-generated method stub
		
		String location;
		int rows,cols,i,j,c1,c2,sum1=0,dist1,sum2=0,dist2,cnt=0, flag=0;;
		ArrayList<String> input=new ArrayList<>();
		location="F:\\3rd CSE\\data mining\\data2.csv";
		//Scanner s= new Scanner(new BufferedReader(new FileReader(location)));
		File file=new File(location);
		Scanner s=new Scanner(file);
		while(s.hasNextLine())
		{
			input.add(s.nextLine());
		}
		input.remove(0);
		rows=input.size();
		String[] for_col=input.get(0).split(",");
		cols=for_col.length-1;
		String[][] data=new String[rows][cols];
		int[] output=new int[rows];
		//System.out.println(rows+"    "+cols);
		for(i=0;i<rows;i++)
		{
			for_col=input.get(i).split(",");
			for(j=0;j<cols;j++)
			{
				data[i][j]=for_col[j];
			//	System.out.println(i+"    "+j);
			}
		}
		s.close();
		for(i=0;i<rows;i++)
		{
			for(j=0;j<cols;j++)
			{
				System.out.print(data[i][j]+" ");
			}
			System.out.println(" ");
		}
		Random rand=new Random();
		while(true)
		{
		c1=rand.nextInt(24);
		c2=rand.nextInt(24);
		if(c1!=c2)
			break;
		}
		//String[] cen1=input.get(c1).split(",");
		//String[] cen2=input.get(c2).split(",");
		int[] old_centroid1=new int[cols];
		int[] old_centroid2=new int[cols];
		int[] new_centroid1=new int[cols];
		int[] new_centroid2=new int[cols];
		
		for(i=0;i<cols;i++)
		{
			new_centroid1[i]=Integer.parseInt(data[c1][i]);
			new_centroid2[i]=Integer.parseInt(data[c2][i]);
			old_centroid1[i]=0;
			old_centroid2[i]=0;
			
		}
		while(true)
		{
			flag=0;
		for(i=0;i<rows;i++)
		{
			sum1=0;
			sum2=0;
			for(j=0;j<cols;j++)
			{
				dist1=Math.abs(Integer.parseInt(data[i][j])-new_centroid1[j]);
				sum1=sum1+dist1;
				dist2=Math.abs(Integer.parseInt(data[i][j])-new_centroid2[j]);
				sum2=sum2+dist2;
			}
			//System.out.println(sum1+"   "+sum2);
			if(sum1<=sum2)
			{
				output[i]=1;
			}
			else
			{
				output[i]=2;
			}
			
		}
		cnt++;
		//if(cnt==1)
		//{
		//	for(int k=0;k<rows;k++)
			//System.out.println(output[k]);
		//}
		
			for(i=0;i<cols;i++)
			{
				if((old_centroid1[i]==new_centroid1[i])&&(old_centroid2[i]==new_centroid2[i]))
				{
					continue;
				}
				else
				{
					flag=1;
				}
			}
			if(flag==0)
				break;
		
		if(flag==1)
		{
			int ce1,ce2,s1,s2;
			for(i=0;i<cols;i++)
			{
				ce1=0;
				ce2=0;
				s1=0;
				s2=0;
				for(j=0;j<rows;j++)
				{
					if(output[j]==1)
					{
						s1=s1+Integer.parseInt(data[j][i]);
						ce1++;
					}
					if(output[j]==2)
					{
						s2=s2+Integer.parseInt(data[j][i]);
						ce2++;
					}
					
				}
				//System.out.println(ce1+ " "+ce2);
				try
				{
				s1=s1/ce1;
				s2=s2/ce2;
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				old_centroid1[i]=new_centroid1[i];
				old_centroid2[i]=new_centroid2[i];
				new_centroid1[i]=s1;
				new_centroid2[i]=s2;
			}
			
		}

	}
		System.out.println("after applying kmeans");
		for(i=0;i<rows;i++)
		{
			System.out.println("object "+i+" belongs to cluster-"+output[i]);
		}
	}
	

}
