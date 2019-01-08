import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Aprior {

     static Scanner sc = new Scanner(System.in);
    static ArrayList<TreeSet<String>> transactions = new ArrayList<TreeSet<String>>();
     static ArrayList<HashMap<String, Integer>> L = new ArrayList<>();
     static ArrayList<HashMap<String, Integer>> C = new ArrayList<>();
     static int minSup;




    private static void apr() {

       L.add(new HashMap<>());
      C.add(new HashMap<>());
        L.add(findL1());
        //print c1
        for (Map.Entry<String, Integer> entry :   (L.get(L.size() - 1)).entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (int k = 2; !L.get(k - 1).isEmpty(); k++) {
            C.add(nextfreq(L.get(k - 1)));
            System.out.println("C"+k);
            HashMap<String, Integer> l = new HashMap<>();
            for (Map.Entry<String, Integer> c : C.get(C.size() - 1).entrySet()) {
                if (c.getValue() >= minSup) {
                    l.put(c.getKey(), c.getValue());
                }
            }


                L.add(l);
                System.out.println("l" + k);
                for (Map.Entry<String, Integer> entry : (L.get(L.size() - 1)).entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

        }
    }

    private static HashMap<String, Integer> nextfreq(HashMap<String, Integer> l) {
        HashMap<String, Integer> c = new HashMap<>();

        // since the l1 and l2 will be of form 'ABC' 'DCE'
        for (Map.Entry<String, Integer> l1 : l.entrySet()) {
            for (Map.Entry<String, Integer> l2 : l.entrySet()) {
                if (isJoinpossible(l1, l2)) {
                    	String joinedString = join(l1, l2);
                    	prune(joinedString, l);
                        // compute count of joinedString and add to HashMap c in the form of {'ABC' : 3}
                        for (TreeSet<String> transaction : transactions) {
                            StringBuilder sb = new StringBuilder();
                            for (String item : transaction) sb.append(item);
                            String transactionString = sb.toString();
                            if (itemsPresentInTransaction(joinedString, transactionString)) {
                                if (c.keySet().contains(joinedString)) {
                                    c.replace(joinedString, c.get(joinedString) + 1);
                                } else {
                                    c.put(joinedString, 1);
                                }
                            }
                        }
                }
            }
        }
        return c;
    }

    private static boolean itemsPresentInTransaction(String items, String transactionString) {
        String[] itemsList = items.split("");
        String[] transactionItemsList = transactionString.split("");

        return Arrays.asList(transactionItemsList).containsAll(Arrays.asList(itemsList));
    }

    private static boolean prune(String joinedString, HashMap<String, Integer> l) {
        ArrayList<String> allSubsets = new ArrayList<>();
        for (int i = 0; i < joinedString.length(); ++i) {
            if (i + L.indexOf(l) <= joinedString.length()) {
                String subString = joinedString.substring(i, i + L.indexOf(l));
                allSubsets.add(subString);
            }
        }

        for (String subSet : allSubsets) {
            if (!l.keySet().contains(subSet)) {
                return true;
            }
        }
        return false;
    }

    private static String join(Map.Entry<String, Integer> l1, Map.Entry<String, Integer> l2) {
        TreeSet<String> set = new TreeSet<>();
        set.addAll(Arrays.asList(l1.getKey().split("")));
        set.addAll(Arrays.asList(l2.getKey().split("")));
        StringBuilder sb = new StringBuilder();
        for (String item : set) sb.append(item);
        return sb.toString();
    }

    private static boolean isJoinpossible(Map.Entry<String, Integer> l1, Map.Entry<String, Integer> l2) {
        String[] l1Entires = l1.getKey().split("");
        String[] l2Entires = l2.getKey().split("");
        int n = l1Entires.length;
        for (int i = 0; i < n - 1; ++i) {
            if (!l1Entires[i].equals(l2Entires[i])) {
                return false;
            }
        }
        return l1Entires[n - 1].compareTo(l2Entires[n - 1]) < 0;
    }

    private static HashMap<String, Integer> findL1() {
        HashMap<String, Integer> C1 = new HashMap<>();
        for (TreeSet<String> transaction : transactions) {
            for (String item : transaction) {
                if (!C1.containsKey(item)) {
                    C1.put(item, 1);
                } else {
                    C1.put(item, C1.get(item) + 1);
                }
            }
        }

        C.add(C1);
        // Take min support into consideration and remove others
        HashMap<String, Integer> L1 = new HashMap<>();
        for (Map.Entry<String, Integer> entry : C1.entrySet()) {
            if (entry.getValue() >= minSup) {
                L1.put(entry.getKey(), entry.getValue());
            }
        }
        return L1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // read transactions from csv file
        Scanner scan = new Scanner(new File("/home/karthik/Downloads/fptest.txt"));
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] tr = input.split(",");
            TreeSet<String> set = new TreeSet<>(Arrays.asList(tr));
            transactions.add(set);
        }
        L.clear();
        C.clear();
        System.out.print("Enter Minimum Support Count: ");
        minSup = sc.nextInt();
        apr();
    }
}

