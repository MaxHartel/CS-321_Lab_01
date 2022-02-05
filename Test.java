import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Accepts user input and sets up the Cache(s) 
 * Searches the Cache(s) with the user provided input file
 * Displays the results of the searches
 */
public class Test {

    private static int numCaches;
    private static Cache<String> baseCache;
    private static File inputFile;
    private static MultiCache<String> mCache;


    public static void main(String args[]) {

        if(args[0].equals("1") && args.length == 3){

            baseCache = new Cache<>(Integer.parseInt(args[1]));
            System.out.println("First level cache with " + args[1] + " entries has been created");
            inputFile = new File(args[2]);
            numCaches = 1;

        }else if(args[0].equals("2") && args.length == 4){

            mCache = new MultiCache<>(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            inputFile = new File(args[3]);
            numCaches = 2;

        }else{
            throw new InvalidUserInputException("Usage: java Test 1 <cache size> <textfile name>  \nor\n java Test 2 <cache level 1 size> <cache level2 size>");
        }

        try{
        parseFile(inputFile);
        }catch(FileNotFoundException e){
            throw new InvalidUserInputException("Be sure to type your file name correctly! \nUsage: java Test 1 <cache size> <textfile name>  \nor\n java Test 2 <cache level 1 size> <cache level2 size>");
        }

        if(numCaches == 1){
            System.out.println("The number of global refrences: " + baseCache.getAccesses());
            System.out.println("The number of global cache hits: " + baseCache.getHits());
            System.out.println("The global hit ratio: " + baseCache.getHitRate());
            System.out.println("\nThe number of 1st-level refrences: " + baseCache.getAccesses());
            System.out.println("The number of 1st-level cache hits: " + baseCache.getHits());
            System.out.println("The 1st-level cache hit ratio: " + baseCache.getHitRate());
            System.out.println("\nThe number of 2nd-level refrences: 0");
            System.out.println("The number of 2nd-level hits: 0");
            System.out.println("The 2nd-level cache hit ratio: 0");

        }else{
            System.out.println("The number of global refrences: " + mCache.getFirstAccesses());
            System.out.println("The number of global cache hits: " + mCache.getTotalHits());
            System.out.println("The global hit ratio: " + mCache.getCombinedHitRate());
            System.out.println("\nThe number of 1st-level refrences: " + mCache.getFirstAccesses());
            System.out.println("The number of 1st-level cache hits: " + mCache.getFirstHits());
            System.out.println("The 1st-level cache hit ratio: " + mCache.getFisrtHitRatio());
            System.out.println("\nThe number of 2nd-level refrences: " + mCache.getSecondaryAccesses());
            System.out.println("The number of 2nd-level hits: " + mCache.getSecondaryHits());
            System.out.println("The 2nd-level cache hit ratio: " + mCache.getSecondaryHitRatio());
        }

    }

    /**
     * Parses through an input file and searches for the words in the Cache(s)
     * @param file
     * @throws FileNotFoundException
     */

    private static void parseFile(File file) throws FileNotFoundException{
        try {
            // Scan through the file line by line
            BufferedReader scanner = new BufferedReader(new FileReader(file));
            String line = scanner.readLine();
            while (line != null){
                String[] words = line.split("\\s+");

                for (String word : words){
                    if (word.length() > 0){ // Skip empty strings
                        if(numCaches == 1){
                            baseCache.contains(word);
                        }

                        if(numCaches == 2){
                            mCache.multiSearch(word);
                        }
                    }
                }

                line = scanner.readLine();
            }
        } catch (FileNotFoundException e){
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
