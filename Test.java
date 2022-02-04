import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Test {

    private static int numCaches;
    private static Cache<String> baseCache;
    private static File inputFile;
    private static MultiCache<String> mCache;


    public static void main(String args[]) {

        if(args[0].equals("1") && args.length == 3){

            baseCache = new Cache<>(Integer.parseInt(args[1]));
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
            System.out.println("The number of global refrences: " + mCache.getTotalAccesses());
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

    private static void parseFile(File file) throws FileNotFoundException{
        Scanner fileScan = new Scanner(file);

        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);


            if(numCaches == 1){
                    
                while (lineScan.hasNext()) {
                    baseCache.contains(lineScan.next());
                }
            }

            if(numCaches == 2){
                while (lineScan.hasNext()) {
                    mCache.multiSearch(lineScan.next());
                }
            }

        }
    }
}
    
