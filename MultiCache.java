/**
 * Establishes a multi layer cache system
 */
public class MultiCache<T>{

    private Cache<T> layer1;
    private Cache<T> layer2;

    /**
     * Constructor for MultiCache, creates Cache instances
     * @param capacity1
     * @param capacity2
     */
    public MultiCache(int capacity1, int capacity2){

        layer1 = new Cache<T>(capacity1);
        layer2 = new Cache<T>(capacity2);
        System.out.println("First level cache with " + capacity1 + " entries has been created");
        System.out.println("Second level cache with " + capacity2 + " entries has been created\n");


    }

    /**
     * Searches the multi layer cache system and keeps track of search stats for the caches
     * @param element
     */
    public void multiSearch(T element){

        if(layer1.contains(element) != null){

            layer1.incrementHits();
            layer1.move(element);
            layer2.move(element);
        }else{
            if(layer2.contains(element) != null){
                layer2.incrementHits();
                layer2.move(element);
                layer1.addToFront(element);
            }else{
                layer1.addToFront(element);
                layer2.addToFront(element);
            }

        }
    }

    /**
     * returns the combined hit rate for the multi cache system
     * @return
     */
    public double getCombinedHitRate(){

        double hitRate = (double)(layer1.getHits() + layer2.getHits()) / layer1.getAccesses();

        return hitRate;

    }


    /**
     * returns the combined miss rate for the multi cache system
     * @return
     */
    public double getCombinedMissRate(){
        double missRate = 1.0 - getCombinedHitRate();

        return missRate;
    }

    /**
     * returns the combined hits for the multi cache system
     * @return
     */
    public int getTotalHits() {
        return layer1.getHits() + layer2.getHits();
    }

    /**
     * returns the hits for the first cache
     * @return
     */
    public int getFirstHits() {
        return layer1.getHits();
    }

    /**
     * returns the accessess for the first cache
     * @return
     */
    public int getFirstAccesses() {
        return layer1.getAccesses();
    }

    /**
     * returns the hit rate for the first cache
     * @return
     */
    public double getFisrtHitRatio() {
        return layer1.getHitRate();
    }

    /**
     * returns the hits for the second cache
     * @return
     */
    public int getSecondaryHits() {
        return layer2.getHits();
    }

    /**
     * returns the accessess for the second cache
     * @return
     */
    public int getSecondaryAccesses() {
        return layer2.getAccesses();
    }

    /**
     * returns the hit rate for the second cache
     * @return
     */
    public double getSecondaryHitRatio() {
        return layer2.getHitRate();
    }

    /**
     * returns the total accesss for the second cache
     * @return
     */
    public int getTotalAccesses() {
        return layer1.getAccesses() + layer2.getAccesses();
    }

}