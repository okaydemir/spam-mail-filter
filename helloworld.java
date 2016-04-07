import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.freeform.FreeformLayer;
import org.encog.neural.freeform.FreeformNetwork;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.simple.EncogUtility;

/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming. This example shows how to construct an Encog neural network to
 * predict the output from the XOR operator. This example uses backpropagation
 * to train the neural network.
 * 
 * This example attempts to use a minimum of Encog features to create and train
 * the neural network. This allows you to see exactly what is going on. For a
 * more advanced example, that uses Encog factories, refer to the XORFactory
 * example.
 * 
 */
public class helloworld {

	public static final int inputsize = 20;
	public static final String path = "C:/Users/okay/Desktop/paste/encog-java-core-master_2/training/";

	public static double XOR_INPUT[][] = new double[inputsize+1][5]; // = { { 0.0,
															// 0.0,1.5,3.1,0.3
															// }, { 1.0,
															// 0.0,1.1,2,3 },
	// { 0.0, 1.0,1.1,1,-2 }, { 1.0, 1.0,1.1,6,7 } };

	/**
	 * The ideal data necessary for XOR.
	 */
	public static double XOR_IDEAL[][] = new double[inputsize+1][1];// = { { 0.0 }, { 1.0
															// }, { 1.0 }, { 0.0
															// } };
	
	
	
	public static double test_in[][] = new double[10][5]; // = { { 0.0,
	// 0.0,1.5,3.1,0.3
	// }, { 1.0,
	// 0.0,1.1,2,3 },
// { 0.0, 1.0,1.1,1,-2 }, { 1.0, 1.0,1.1,6,7 } };

/**
* The ideal data necessary for XOR.
*/
public static double test_ideal[][] = new double[10][1];// = { { 0.0 }, { 1.0
	// }, { 1.0 }, { 0.0
	// } };
	
	

	public static Map<String, Integer> wordMap;
	public static List<Entry<String, Integer>> list;
	
	public static String spamz;
	public static Map<String, Integer> spamWordMap;
	public static List<Entry<String, Integer>> spamList;
	
	public static Map<String, Integer> getWordCountSpams() {

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedReader br = null;
		Map<String, Integer> spamWordMap = new HashMap<String, Integer>();
			while (spamz != null) {
				StringTokenizer st = new StringTokenizer(spamz, " ");
				while (st.hasMoreTokens()) {
					String tmp1 = st.nextToken();
					String tmp = tmp1.replaceAll("\\W", "");
					if (spamWordMap.containsKey(tmp)) {
						spamWordMap.put(tmp, spamWordMap.get(tmp) + 1);
					} else {
						spamWordMap.put(tmp, 1);
					}
				}
			}
			
		return spamWordMap;
	}

	public static List<Entry<String, Integer>> sortByValueSpams(Map<String, Integer> spamWordMap) {

		Set<Entry<String, Integer>> set = spamWordMap.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		return list;
	}

	public static Map<String, Integer> getWordCount(String fileName) {

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedReader br = null;
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		try {
			fis = new FileInputStream(fileName);
			dis = new DataInputStream(fis);
			br = new BufferedReader(new InputStreamReader(dis));
			String line = null;
			br.readLine();
			br.readLine();
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, " ");
				while (st.hasMoreTokens()) {
					String tmp1 = st.nextToken();
					String tmp = tmp1.replaceAll("\\W", "");
					if (wordMap.containsKey(tmp)) {
						wordMap.put(tmp, wordMap.get(tmp) + 1);
					} else {
						wordMap.put(tmp, 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
			}
		}
		return wordMap;
	}

	public static List<Entry<String, Integer>> sortByValue(Map<String, Integer> wordMap) {

		Set<Entry<String, Integer>> set = wordMap.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		return list;
	}

	public static void checker(String Regex_Pattern, String input) {

		Pattern p = Pattern.compile(Regex_Pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		System.out.println(m.find());

	}

	public static int countPunctuation(String s) {
		int unlemcount = 0, sicount = 0, yu = 0;
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '!':
				unlemcount++;
				break;
			case '?':
				sicount++;
				break;
			case '%':
				yu++;
				break;

			}
		}
		return yu + unlemcount + sicount;
	}

	public static double UpperCaseDensity(String s) {
		int upc = 0;
		int size = s.length();
		for (int i = 0; i < size; i++) {
			if (Character.isUpperCase(s.charAt(i))) {
				upc++;
				
			}

		}
		System.out.println("-----------------------" );
		return (double)(upc) / size;

	}

	public static String SpamWords[] = { "sale", "buy", "free", "sex", "discount"};
	
	public static ArrayList<String> spamss;

	public static int SpamWordCount(String s) {
		// Hashtable<String, Integer> words = new Hashtable<String, Integer>();
		int count = 0;
		for (int i = 0; i < SpamWords.length; i++) {

			if (wordMap.containsKey(SpamWords[i])) {
				count += wordMap.get(SpamWords[i]);
			}

		}
		return count;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BufferedReader reader = null;
		String line;
		int linenr = 0;

		// checker("\\b[aeiouAEIOU][a-zA-Z]*\\b", line);

		try {
			for (int i = 0; i < inputsize; i++) {
				linenr = 0;
				File file = new File(path+ "mail ("
						+ (int) (i + 1) + ").txt");
				System.out.println(i+1 +"*****************");

				reader = new BufferedReader(new FileReader(file));

				line = reader.readLine();
				System.out.println(line +"*****************");
				int isSpam = Integer.parseInt(line);
				XOR_IDEAL[i][0] = (double)isSpam;// spam(0) or not(1)
				line = reader.readLine();
				XOR_INPUT[i][0] = Integer.parseInt(line.charAt(0) + "" + line.charAt(1));// 08:52
				line = reader.readLine();
				XOR_INPUT[i][1] = countPunctuation(line);// KONU
				String icerik = "";
				while ((line = reader.readLine()) != null) {
					icerik += line;

				}
				System.out.println(icerik);
				
				if(isSpam == 0){
					spamz += icerik;
				}
				
				

				wordMap = getWordCount(path+ "mail ("
						+ (int) (i + 1) + ").txt");
				list = sortByValue(wordMap);
				for(Map.Entry<String, Integer> entry:list){
				 System.out.println(entry.getKey()+" ==== "+entry.getValue());
				 }
				
				XOR_INPUT[i][2] = UpperCaseDensity(icerik);
				XOR_INPUT[i][3] = SpamWordCount(icerik);
				XOR_INPUT[i][4] = (double)(list.get(0).getValue()) / list.size();

			}
			
			


			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			for (int i = inputsize; i <30 ; i++) {
				linenr = 0;
				File file = new File(path+ "mail ("
						+ (int) (i + 1) + ").txt");
				System.out.println(i+1 +"*****************");

				reader = new BufferedReader(new FileReader(file));

				line = reader.readLine();
				System.out.println(line +"*****************");
				int isSpam = Integer.parseInt(line);
				test_ideal[i-inputsize][0] = (double)isSpam;// spam(0) or not(1)
				line = reader.readLine();
				test_in[i-inputsize][0] = Integer.parseInt(line.charAt(0) + "" + line.charAt(1));// 08:52
				line = reader.readLine();
				test_in[i-inputsize][1] = countPunctuation(line);// KONU
				String icerik = "";
				while ((line = reader.readLine()) != null) {
					icerik += line;

				}
				/*System.out.println(icerik);
				
				if(isSpam == 0){
					spamz += icerik;
				}*/
				
				

				wordMap = getWordCount(path+ "mail ("
						+ (int) (i + 1) + ").txt");
				list = sortByValue(wordMap);
				for(Map.Entry<String, Integer> entry:list){
				 System.out.println(entry.getKey()+" ==== "+entry.getValue());
				 }
				
				test_in[i-inputsize][2] = UpperCaseDensity(icerik);
				test_in[i-inputsize][3] = SpamWordCount(icerik);
				test_in[i-inputsize][4] = (double)(list.get(0).getValue()) / list.size();

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

		} catch (IOException ee) {
			ee.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}

		/**
		 * The input necessary for XOR.
		 */

		/**
		 * The main method.
		 * 
		 * @param args
		 *            No arguments are used.
		 */
		// public static void main(final String args[]) {

		// create a neural network, without using a factory
		/*
		 * BasicNetwork network = new BasicNetwork(); network.addLayer(new
		 * BasicLayer(null,true,2)); network.addLayer(new BasicLayer(new
		 * ActivationSigmoid(),true,3)); network.addLayer(new BasicLayer(new
		 * ActivationSigmoid(),false,1)); //network.addLayer(new
		 * BasicLayer(null,true,2));
		 * 
		 * network.getStructure().finalizeStructure(); network.reset();
		 */

		FreeformNetwork network = new FreeformNetwork();
		FreeformLayer inputlayer = network.createInputLayer(5);
		FreeformLayer hiddenlayer = network.createLayer(3);
		FreeformLayer out = network.createOutputLayer(1);
		network.connectLayers(inputlayer, hiddenlayer, new ActivationSigmoid(), 1.0, false);
		network.connectLayers(hiddenlayer, out, new ActivationSigmoid(), 1.0, false);
		network.reset();

		// create training data
		MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
		MLDataSet testSet = new BasicMLDataSet(test_in, test_ideal);

		EncogUtility.trainToError(network, trainingSet, 0.15);
		EncogUtility.evaluate(network, testSet);

		Encog.getInstance().shutdown();

		// train the neural network
		/*
		 * final ResilientPropagation train = new ResilientPropagation(network,
		 * trainingSet);
		 * 
		 * int epoch = 1;
		 * 
		 * do { train.iteration(); System.out.println("Epoch #" + epoch +
		 * " Error:" + train.getError()); epoch++; } while(train.getError() >
		 * 0.01); train.finishTraining();
		 * 
		 * // test the neural network System.out.println(
		 * "Neural Network Results:"); for(MLDataPair pair: trainingSet ) {
		 * final MLData output = network.compute(pair.getInput());
		 * System.out.println(pair.getInput().getData(0) + "," +
		 * pair.getInput().getData(1) + ", actual=" + output.getData(0) +
		 * ",ideal=" + pair.getIdeal().getData(0)); }
		 * 
		 * Encog.getInstance().shutdown();
		 */
	}
}