package edu.buffalo.cse531;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class LCS {
	
	int[][] opt = new int[100][100];
	char[][] optChar = new char[100][100];
	char[] finalCharArr  = new char[100];
	static File outputFile = null;
	private static BufferedWriter bw;
	
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
	public Map<Integer, List<AdjNode>> fileReader(String filePath) {

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

			String sCurrentLine;
			String[] splitTerms;
			int line_num = 0;
		}
	}
	public static void writeToFile(BufferedWriter bw2, String string) {
		try {
			bw.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public enum optChar_Symbols {
		SideUp('s'), Left('l'), Up('u');

	    public char asChar() {
	        return asChar;
	    }

	    private final char asChar;

	    private optChar_Symbols(char asChar) {
	        this.asChar = asChar;
	    }
	}
	public String getLongestSubSequence(String a, String b){
		StringBuilder str = new StringBuilder();
		int n = a.length();
		int m = b.length();
		char[] array_A = a.toCharArray();
		char[] array_B = b.toCharArray();
		 for(int j =0;j<=m; j++)
		 {
			 opt[0][j] = 0;
		 }
		 for(int i = 1;i<=n;i++)
		{
			 opt[i][0] = 0;
			 for(int j =1;j<=m; j++)
			 {
				 if(array_A[i-1]== array_B[j-1])
				 {
					 opt[i][j] = opt[i-1][j-1] +1;
					 optChar[i][j] = optChar_Symbols.SideUp.asChar;
				 }
				 else if(opt[i][j-1] >= opt[i-1][j])
				 {
					 opt[i][j] = opt[i][j-1];
					 optChar[i][j] = optChar_Symbols.Left.asChar;
				 }
				 else
				 {
					 opt[i][j] = opt[i-1][j];
					 optChar[i][j] = optChar_Symbols.Up.asChar;
				 }
			 }
		}
		
		//final traversal
		 int c = 0;
		 int i=a.length();
		 int j=b.length();
		 while(i>=1 && j>=1)
		 {
			 
			 if(optChar[i][j]==optChar_Symbols.SideUp.asChar)
				 {
					//If equal,the add that character to a final rray of characters
					 finalCharArr[c] = array_A[i-1];
					 i--;
					 j--;
					 c++;
				 }
				 else if(optChar[i][j]==optChar_Symbols.Left.asChar)
				 {
					 j--;
				 }
				 else if(optChar[i][j]==optChar_Symbols.Up.asChar)
				 {
					 i--;
				 }
			 
		 }
		
		//reversing the finl charcter rray and building String
		
		 for(i = c-1; i>=0;i--)
			 str.append(finalCharArr[i]);
		 
		return str.toString();
	}

	public static void main(String[] args) {
		LCS lcs  = new LCS();
		System.out.println(lcs.getLongestSubSequence("67gftbac", "452367ppgftadbcdagfhsbb12kk34"));
	}
}
