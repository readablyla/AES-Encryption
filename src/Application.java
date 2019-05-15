import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * @author Brenden Mayall c3236213
 * @author Leala Darby c3279478
 *
 * Main runnable class for AES implementaion.
 */
public class Application {

	/**
	 * Handles file input and output. Runs AES class.
	 * Usage: java Application inputFile.txt e|d
	 * @param args Array of command line arguments
	 */
	//TODO exception handling
	public static void main(String args[]) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;

		try {
			in = new BufferedReader(new FileReader(args[0]));
			out = new BufferedWriter(new FileWriter("../test_input/output.txt"));
			String firstLine = in.readLine();
			String key = in.readLine();

			if (args[1].equalsIgnoreCase("e")) {            //for encryption

				Long startTime = System.nanoTime();

				//store modified plaintexts and keys
				BitSet[] pi = new BitSet[129];
				BitSet[] ki = new BitSet[129];

				//store number of differing bits after each round, for each version
				double[][] result_pi = new double[11][5];
				double[][] result_ki = new double[11][5];

				//store the round results retuned by the AES class
				String[] p_under_k = new String[11];
				String[] pi_under_k = new String[11];
				String[] p_under_ki = new String[11];

				//compute modified plaintexts
				pi[0] = toBitSet(firstLine);
				for (int i = 1; i < pi.length; i++) {
					pi[i] = (BitSet) pi[0].clone();
					pi[i].flip(i - 1);
				}
				 
				//compute modified keys
				ki[0] = toBitSet(key);
				for (int i = 1; i < ki.length; i++) {
					ki[i] = (BitSet) ki[0].clone();
					ki[i].flip(i - 1);
				}

				for (int i = 0; i < 5; i++) {				//loop for AES version
					AES aes = new AES(firstLine, key, true, i);
					p_under_k = aes.getRoundResult();
					// p under k vs pi under k
					for (int j = 1; j < 129; j++) {			//loop for modified plaintexts
						aes = new AES(toBinaryString(pi[j]), key, true, i);
						pi_under_k = aes.getRoundResult();
						for(int k = 0; k < 11; k++) {		//loop for each round
							BitSet ptemp = toBitSet(p_under_k[k]);
							BitSet pitemp = toBitSet(pi_under_k[k]);
							for(int b = 0; b < 128; b++) {	//loop for each bit of round result
								if (ptemp.get(b) ^ pitemp.get(b)) {
									result_pi[k][i]++;
								}
							}
						}
					}
					// p under k vs p under ki
					for (int j = 1; j < 129; j++) {			//loop for modified keys
						aes = new AES(firstLine, toBinaryString(ki[j]), true, i);
						p_under_ki = aes.getRoundResult();
						for(int k = 0; k < 11; k++) {		//loop for each round
							BitSet ptemp = toBitSet(p_under_k[k]);
							BitSet kitemp = toBitSet(p_under_ki[k]);
							for(int b = 0; b < 128; b++) {	//loop for each bit of round result
								if (ptemp.get(b) ^ kitemp.get(b)) {
									result_ki[k][i]++;
								}
							}
						}
					}
				}

				Long endTime = System.nanoTime();
				Long timeElapsed = endTime - startTime;

				//output
				AES aes = new AES(firstLine, key, true, 0);
				out.write("ENCRYPTION\n");
				out.write("Plaintext P: " + firstLine + "\n");
				out.write("Key K: " + key + "\n");
				out.write("Ciphertext C: " + aes.getRoundResult()[10] + "\n");
				out.write("Running Time: " + String.format("%d", timeElapsed/1000000) + '\n');
				System.out.println();
				out.write("Avalanche:\n");
				//append pi under k results
				out.write("P and Pi under K\n");
				out.write("Round" + '\t' + "AES0" + '\t' + "AES1" + '\t' + "AES2" + '\t' + "AES3" + '\t' + "AES4" + '\n');
				out.flush();
				for (int i = 0; i < 11; i++) {
					out.write(String.valueOf(i) + '\t');
					for (int j = 0; j < 5; j++) {
						out.write('\t' + String.valueOf((int)Math.round(result_pi[i][j]/128)) + '\t');
					}
					out.newLine();
					out.flush();
				}
				//append p under ki results
				out.newLine();
				out.write("P under K and Ki\n");
				out.write("Round" + '\t' + "AES0" + '\t' + "AES1" + '\t' + "AES2" + '\t' + "AES3" + '\t' + "AES4" + '\n');
				out.flush();
				for (int i = 0; i < 11; i++) {
					out.write(String.valueOf(i) + '\t');
					for (int j = 0; j < 5; j++) {
						out.write('\t' + String.valueOf((int)Math.round(result_ki[i][j]/128)) + '\t');
					}
					out.newLine();
					out.flush();
				}

			} else if (args[1].equalsIgnoreCase("d")) {    //for decryption
				AES aes = new AES(firstLine, key, false, 0);

				//output
				out.write("DECRYPTION\n");
				out.write("Ciphertext C: " + firstLine + '\n');
				out.write("Key K: " + key + '\n');
				out.write("Plaintext P: " + aes.getRoundResult()[10] + '\n');
				out.flush();

			} else {
				System.err.println("Correct usage: java Application inputFile.txt e|d");
				System.exit(0);
			}

		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}

	}

	/**
	 * method to convert a string of bits to a bit set
	 * @param binary binary string to convert
	 * @return converted bit set
	 */
	private static BitSet toBitSet(String binary) {
		BitSet bitset = new BitSet(binary.length());
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				bitset.set(i);
			}
		}
		return bitset;
	}

	/**
	 * method to convert bit set to binary string
	 * @param bs bit set to convert
	 * @return converted binary string
	 */
	public static String toBinaryString(BitSet bs) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < bs.length(); i++) {
			buffer.append(bs.get(i) == true ? '1' : '0');
		}
		return String.format("%-128s", buffer.toString()).replace(' ', '0');
	}
}
