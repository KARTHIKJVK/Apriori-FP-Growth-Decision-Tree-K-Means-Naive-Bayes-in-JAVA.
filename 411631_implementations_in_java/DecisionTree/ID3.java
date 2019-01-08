import java.io.*;
import java.util.*;
import java.lang.*;


public class ID3{

	//command line arguments
    public static void main(String args[]){

        readFile rf = new readFile(args);
        HashMap<String, List<String>> attributes = rf.getAttributes();
        String[][] dataset = rf.getDataset();

        runID3(dataset, attributes, 0);


    }



    public static double entropy(String[] decision_set, List<String> possible_decision_values){


        double[] counts = new double[possible_decision_values.size()];
        double sum = 0.0;

        for(int i = 0; i < decision_set.length ; i++){
            for(int j = 0; j < possible_decision_values.size(); j++){
                if (decision_set[i].equals(possible_decision_values.get(j))){
                    counts[j]++;
                    sum++;
                    break;
                }
            }
        }

        double ent = 0.0;
        for(int i = 0; i < counts.length; i++){
            if (counts[i] == 0) {continue;}
            ent += (-(counts[i]/sum) * Math.log(counts[i]/sum) / Math.log(2));
        }
        return ent;
    }

    public static double infoGain(String[] attribute_set, String[] decision_set,
                                  List<String> possible_attribute_values, List<String> possible_decision_values){


        double IG = entropy(decision_set, possible_decision_values);
        ArrayList<String> subset = new ArrayList<String>();

        for(int i = 0; i < possible_attribute_values.size(); i++){
            subset = new ArrayList<String>();
            for(int j = 1; j < attribute_set.length; j++){
                if (attribute_set[j].equals(possible_attribute_values.get(i))){
                    subset.add(decision_set[j]);
                }
            }
            IG -= ((double)subset.size() / (double)(decision_set.length-1)) * entropy(subset.toArray(new String[subset.size()]), possible_decision_values);
            subset = new ArrayList<String>();
        }

        return IG;
    }

    public static void runID3(
            String[][] dataset, HashMap<String, List<String>> attribute_values, int level){

        if (dataset.length == 0){
            for(int i = 0; i < level; i++){
                System.out.print("   ");
            }
            System.out.println("Failure to classify");
            return;
        }


        if (entropy(getCol(dataset, dataset[0].length-1),
                attribute_values.get("Decision")) == 0.0){
            for(int i = 0; i < level; i++){
                System.out.print("   ");
            }
            System.out.println(dataset[1][dataset[0].length-1]);
            return;
        }

        if (dataset[0].length == 1){
            String dec = mostPopularDecision(getCol(dataset, dataset[0].length-1),
                    attribute_values.get("Decision"));
            for(int i = 0; i < level; i++){
                System.out.print("   ");
            }
            System.out.println(dec);
            return;
        }

        double infogain = -1;
        String split = "";
        int column = 0;
        String att = "";

        /*
         * Calculate the attribute with the most Information Gain
         */
        for(int i = 0; i < (dataset[0].length - 1); i++){
            att = getCol(dataset, i)[0];
            double temp = infoGain(getCol(dataset, i), getCol(dataset, dataset[0].length - 1), attribute_values.get(att), attribute_values.get("Decision"));

            if (temp > infogain){
                infogain = temp;
                split = att;
                column = i;
            }
        }
        for(String value : attribute_values.get(split)){
            ArrayList<String[]> new_dataset = new ArrayList<String[]>();
            new_dataset.add(concatStringArray(Arrays.copyOfRange(dataset[0], 0 , column), Arrays.copyOfRange(dataset[0],column+1,dataset[0].length)));

            for(int i = 1; i < dataset.length; i++){
                if (dataset[i][column].equals(value)){
                    new_dataset.add(concatStringArray(Arrays.copyOfRange(dataset[i], 0 , column), Arrays.copyOfRange(dataset[i],column+1,dataset[i].length)));
                }
            }

            for(int i = 0; i < level; i++){
                System.out.print("   ");
            }
            System.out.println(split + " " + value);

            runID3(arrayListTo2DArray(new_dataset), attribute_values, level+1);
        }

    }


    public static String[][] arrayListTo2DArray(ArrayList<String[]> arraylist){
        /*
         * Converts an ArrayList<String[]> to String[][]
         */

        String[][] array = new String[arraylist.size()][arraylist.get(0).length];

        for(int i = 0; i < arraylist.size(); i++){
            for(int j = 0; j < arraylist.get(i).length; j++){
                array[i][j] = arraylist.get(i)[j];
            }
        }

        return array;
    }


    public static String[] concatStringArray(String[] a, String[] b){
        /*
         * Concatenates b to the ends of a
         */

        String[] c = new String[a.length+b.length];

        for(int i = 0; i < a.length; i++){
            c[i] = a[i];
        }
        for(int i = 0; i < b.length; i++){
            c[i+a.length] = b[i];
        }

        return c;
    }



    public static String[] getCol(String[][] set, int colnum){
        /*
         * Returns the values of a column for a String[][]
         */

        String[] col = new String[set.length];

        for(int i = 0; i < set.length; i++){
            col[i] = set[i][colnum];
        }

        return col;
    }

    public static String mostPopularDecision(String[] decisions,
                                             List<String> decision_values){

        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        int maxCounts = 0;
        String maxKey = "";

        for(int i = 0; i < decision_values.size(); i++){
            counts.put(decision_values.get(i),0);
        }

        for(int i = 0; i < decisions.length; i++){
            Integer temp = counts.get(decisions[i]);
            temp++;
            counts.put(decisions[i],temp);

            if (maxCounts < temp){
                maxCounts = temp;
                maxKey = decisions[i];
            }
        }

        return maxKey;
    }

}
