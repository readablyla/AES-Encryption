import java.io.*;

/*
@author Brenden Mayall c3236213
@author Leala Darby

Main runnable class for AES implementaion. 
*/
public class Application {

	/*
	Handles file input and output. Runs AES class.
	Usage: java Application inputFile.txt e|d
	@param args[] Array of command line arguments
	*/
	//TODO exception handling
	public static void main(String args[]) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;

		try {
			in = new BufferedReader(new FileReader(args[0]));
			out = new BufferedWriter(new FileWriter("output.txt"));
			String firstLine = in.readLine();
			String key = in.readLine();

			if(args[1].equalsIgnoreCase("e")) {			//for encryption
				//AES aes = new AES(firstLine, key, 0);
			}
			else if(args[1].equalsIgnoreCase("d")) {	//for decryption
				//AES aes = new AES(firstLine, key, 1);
			}
			else {
				System.err.println("Correct usage: java Application inputFile.txt e|d");
				System.exit(0);
			}
		}
		finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}
	}
}
