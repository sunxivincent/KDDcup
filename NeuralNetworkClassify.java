/**
 * Compare NeuralNetwork classifier with different hidden node number 
 */
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
 
public class NeuralNetworkClassify {
	static int[] HIDDEN_NODES = {5,10,20,30};
	static int[] SVD_ITERATION = {10,20,30,40,50,60,70};
	static int FOLDS = 5;

	public static void main(String[] args) throws Exception {
		// load training data
		BufferedReader br = new BufferedReader(new FileReader("trainweka.arff"));
		Instances traindata = new Instances(br);
		br.close();
		
		 // setting class attribute
		traindata.setClassIndex(traindata.numAttributes() - 1);
		
		//train classifier by Neural Network, try different hidden nodes, firstly just use one hidden layer
		//for each feature number in SVD matrix, try different hidden nodes n. n = 10~30
		
		for(int i=0; i<SVD_ITERATION.length; i++){
			// remove corresponding range in each run
			Remove rm = new Remove();
			String idxrange = String.valueOf(SVD_ITERATION[i]+1)+"-"+String.valueOf(77);
			rm.setAttributeIndices(idxrange);
			rm.setInputFormat(traindata);
			rm.setInvertSelection(true);
			Instances newdata = Filter.useFilter(traindata, rm);

			for(int j=0; j<HIDDEN_NODES.length; j++){
				newdata.randomize(new Random(10));
			    Evaluation evalAll = new Evaluation(newdata);
				
				//train Neural Network model for different hidden nodes number
				MultilayerPerceptron mlp = new MultilayerPerceptron();
				String hiddenlayer = String.valueOf(HIDDEN_NODES[j]);
				mlp.setHiddenLayers(hiddenlayer);
				//n fold cross validation
				for (int n = 0; n < FOLDS; n++){
					Instances foldtrain = newdata.trainCV(FOLDS, n);
					Instances foldtest = newdata.testCV(FOLDS, n);
					// build and evaluate classifier
					Classifier clsCopy = Classifier.makeCopy(mlp);
				    clsCopy.buildClassifier(foldtrain);
				    evalAll.evaluateModel(clsCopy, foldtest);
					System.out.println(n+" fold done ");
				}
				System.out.println("SVD "+SVD_ITERATION[i]+" "+"hidden nodes "+ HIDDEN_NODES[j]+" done");
						
				// evaluate and write result to file
				File wfile = new File("K"+SVD_ITERATION[i]+"NODE"+HIDDEN_NODES[j]+"L1.txt");
				FileWriter fw = new FileWriter(wfile,true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fw);
    	        bufferWritter.write(evalAll.toSummaryString("=== " + FOLDS + "-fold Cross-validation ===", false));
    	        bufferWritter.close();
			}	
		}
	}
}
