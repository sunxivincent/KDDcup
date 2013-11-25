import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;


public class preprocess {
	private static String inputfile = "/home/sunxi/Desktop/mlproject/phy_test.dat";
	private static String outputfile = "/home/sunxi/Desktop/mlproject/phy_test_p.dat";
	
	public static void main(String[] args) throws Exception{
		// sum of following coloums
		int col1 = 1;	
		BufferedReader br = new BufferedReader(new FileReader(inputfile));
        String line = br.readLine();
        
        FileWriter fw = new FileWriter(outputfile,true);
        while (line!=null){
        	String[] str = line.split("\t+");
        	for (int i=0; i< str.length; i++){
        		if(i==col1 && str[i].equals("?")){
        			Random rd = new Random();
        			str[i] = String.valueOf(rd.nextInt(2)); // random return 0 and 1 to replace the ? symbol	
        		}
        		fw.write(str[i]+" ");
        	}
        	fw.write("\n");
        	line = br.readLine();
        }
        br.close();
        fw.close();
	}
}
