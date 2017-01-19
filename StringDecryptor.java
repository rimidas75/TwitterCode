
public class StringDecryptor {

	static String decrypt(String encrypted_message) {
		
		char msg[] = encrypted_message.toCharArray();
		int msgAscii[] = new int[msg.length];
		for(int j = 0;j< msg.length;j++)
		{
			//System.out.println(msg[j]);
			msgAscii[j] = msg[j];
			//System.out.println(msg[j] + " ---- " +msgAscii[j]);
			
		}
			
		
		String key = "2512208";
		long[] keyArray = new long[key.length()];
		long lKey = Long.parseLong(key);
		//System.out.println(lKey);
		long keyVal = lKey;
		int arrayLen = keyArray.length;
		while(keyVal > 0 && arrayLen >0)
		{
			long x  = keyVal % 10;
			keyArray[arrayLen -1] = x;
			arrayLen --;
			keyVal = keyVal/10;
		}
		
		arrayLen = keyArray.length;
		for (int i = 0;i<keyArray.length;i++)
		{
			
			//System.out.println(keyArray[i]);
		}
		int c = 0,a= 0;
		while(c< msg.length)
		{
			if((msgAscii[c]>=65 && msgAscii[c]<=91) || (msgAscii[c]>=97 && msgAscii[c] <= 122))
			{int  z = (int) keyArray[a % arrayLen];
			msgAscii[c] = (int) (msgAscii[c]- z);
			System.out.print(z + "--- " +msgAscii[c]  + "  ");
			if((msgAscii[c] <65) || ( msgAscii[c] <97 && msgAscii[c] > 91))
				msgAscii[c] = 26+ msgAscii[c];
			//System.out.print(msgAscii[c] + "\n");
			a++;
			}
			c++;
		}
		String str = "";
		for(int x  = 0 ;x< c;x++)
		{
			int i = msgAscii[x];
			str = str + Character.toString((char)i);
			
		}
		
		//System.out.println(str);
		return encrypted_message;


    }
	
	public static void main(String[] args) {
		StringDecryptor s  = new StringDecryptor();
		s.decrypt("Otjfvknou kskgnl, K mbxg iurtsvcnb ksgq hoz atv.Atvt hrqgse , Cnikg");
	}
}
