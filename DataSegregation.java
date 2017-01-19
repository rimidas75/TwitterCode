package sample;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class DataSegregation {
	static File outputFile = null;
	private static BufferedWriter bw;
	static String string1;
	static String string2;
	Date[] dateArray ;
	String[] engArray;
	int[] numArray;
	Map<Date,String>  nmap = new TreeMap<Date,String>(new Comparator<Date>() {
	    public int compare(Date date1, Date date2) {
	        return date2.compareTo(date1);
	    }
	});
	List<String> inputs = new ArrayList<String>();
    public static void main(String args[] ) throws Exception {
    	DataSegregation dataSegregation = new DataSegregation();
    	dataSegregation.fileReader("C:\\Users\\RIMI\\Desktop\\input.txt");
    	dataSegregation.compute();
    }
    
    public void FileWriter() {

		outputFile = new File("output.txt");
		if (!outputFile.exists()) {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {

			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void fileReader(String filePath) {
		
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
//			Scanner in = new Scanner (System.in);
			
			String sCurrentLine = null;
			
			int line_num = 0;
//			while((line_num<=1) || ( /*( null!=(sCurrentLine = in.nextLine()) ) && */((sCurrentLine = in.nextLine()).length() !=0))){
			while ((line_num <= 1) || ((null!= (sCurrentLine = br.readLine())  )  &&  (sCurrentLine.length()!=0))) {
				String lineText = sCurrentLine;
				if(line_num == 0)
				{	
//					lineText = in.nextLine();
					lineText = br.readLine();
					string1 = lineText;
				//System.out.println("string1 = "+ string1);
					}
				else if(line_num==1)
				{
//					sCurrentLine = in.nextLine();
					sCurrentLine = br.readLine();
					//System.out.println("string2 = "+ sCurrentLine);
				}
				else if(line_num > 1)
					{
					
					//System.out.println("---"+lineText+"----");
					if(lineText.length()>0)
					inputs.add(lineText);
					
					};
				line_num++;
			}
			
			createInputArrays(line_num);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void writeToFile(BufferedWriter bw2, String string) {
		try {
			bw.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	private void createInputArrays(int line_num) {
		dateArray = new Date[line_num];
		engArray = new String[line_num];
		numArray = new int[line_num];
		int i = 0;
		for(String x : inputs)
		{
			//System.out.println("x  ==== "+x);
			String[] splitterms = x.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date convertedCurrentDate;
			try {
				convertedCurrentDate = sdf.parse(splitterms[0]);
				engArray[i] = splitterms[1];
				numArray[i] = Integer.parseInt(splitterms[2]);
			
			dateArray[i] = convertedCurrentDate ;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i++;
		}
		
	}

	public void compute()
	{
		//to find input start and end date
		String[] splitdate = string1.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    Date convertedStartDate = null;
	    Date convertedEndDate = null;
		try {
			convertedStartDate = sdf.parse(splitdate[0]);
			convertedEndDate = sdf.parse(splitdate[1]);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int  i = 0;i< dateArray.length;i++)
		{
			
			if(null!= dateArray[i] && dateArray[i].after(convertedStartDate) && dateArray[i].before(convertedEndDate))
			{
				String str = engArray[i] +","+ numArray[i];
				
				
				//System.out.println("date=== "+dateFormatter(dateArray[i]));
				if(!nmap.containsKey(dateFormatter(dateArray[i])))
				{
				nmap.put(dateFormatter(dateArray[i]), str);
				//System.out.println(str);
				}
				else
				{
					String toReplace  = "";
					//to check if that engagement already is considered or not
					if(nmap.get(dateFormatter(dateArray[i])).contains(engArray[i]))
							{
						String sub = "";
						int count = 0 ;
						String h = nmap.get(dateFormatter(dateArray[i]));
						sub = engArray[i] +",";
						System.out.println("sub = " +sub);
						int x = h.indexOf(sub);
						System.out.println("x = " +x);
						
						for(int pos = x+sub.length();pos < h.length();pos++)
						{ System.out.println(pos);
							if(h.charAt(pos)==',' || pos== h.length()-1)
							{
								System.out.println("pos == "+ pos + " "+ h.substring(x+sub.length(),pos+1));
								count = Integer.parseInt(h.substring(x+sub.length(),pos+1));
								System.out.println("count == " +count);
								 str = engArray[i] +","+ (numArray[i]+count);
								 toReplace = sub+count;
							}
						}
							}
					System.out.println(nmap.get(dateFormatter(dateArray[i])));
				str =   (nmap.get(dateFormatter(dateArray[i]))).replaceAll(toReplace, "")+ ","+str ;
				nmap.put(dateFormatter(dateArray[i]), str);
				}
				
			}
		}
		/*Map<Date, String> sortedMap = new TreeMap<Date, String>(nmap);
		sortedMap = new TreeMap<Date, String>();*/
		
		
		
		//Map<Date, Integer> m1 = new TreeMap(m);
	    DateFormat df = new SimpleDateFormat("yyyy-MM");
	   // System.out.println("---------------------------------------------------");
	   for (Entry<Date, String> entry : nmap.entrySet())
	    {
	       System.out.println(df.format(entry.getKey()) +","+ entry.getValue());
	    }
		
	}
	public Date dateFormatter(Date date)
	{
		//System.out.println(date);
	        DateFormat formatter;
	        String str_date;
	        formatter = new SimpleDateFormat("yyyy-MM");
	        str_date = formatter.format(date);
	       // System.out.println("str date === "+ str_date);
	        /*formatter = new SimpleDateFormat("MM/dd/yyyy");
	        System.out.println("Today is " + formatter.format(date));*/
	        try {
				return formatter.parse(str_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		
	}
}
