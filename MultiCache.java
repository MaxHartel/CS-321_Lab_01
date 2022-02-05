
public class MultiCache<T>{

    private Cache<T> layer1;
    private Cache<T> layer2;

    public MultiCache(int capacity1, int capacity2){

        layer1 = new Cache<T>(capacity1);
        layer2 = new Cache<T>(capacity2);
        System.out.println("First level cache with " + capacity1 + " entries has been created");
        System.out.println("Second level cache with " + capacity2 + " entries has been created\n");


    }

    public void multiSearch(T element){

        if(layer1.contains(element) != null){

            layer1.incrementHits();
            layer1.move(element);
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

    public double getCombinedHitRate(){

        double hitRate = (layer1.getHits() + layer2.getHits()) / layer1.getAccesses();

        return hitRate;

    }


    public double getCombinedMissRate(){
        double missRate = 1.0 - getCombinedHitRate();

        return missRate;
    }

    public int getTotalHits() {
        return layer1.getHits() + layer2.getHits();
    }

    public int getFirstHits() {
        return layer1.getHits();
    }

    public int getFirstAccesses() {
        return layer1.getAccesses();
    }

    public double getFisrtHitRatio() {
        return layer1.getHitRate();
    }

    public int getSecondaryHits() {
        return layer2.getHits();
    }

    public int getSecondaryAccesses() {
        return layer2.getAccesses();
    }

    public double getSecondaryHitRatio() {
        return layer2.getHitRate();
    }

    public int getTotalAccesses() {
        return layer1.getAccesses() + layer2.getAccesses();
    }

}