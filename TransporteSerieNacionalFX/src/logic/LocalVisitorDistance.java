package logic;

/**
 * Class responsible for establish the relation between the local team, the visitor team and the distance between
 * their stadium
 *
 * @author Rafael Gayoso and Mario Herrera
 */
public class LocalVisitorDistance {

    private int posLocal;//Position of the local team
    private int posVisitor;//Position of the visitor team
    private double distance;//Distance between

    /**
     * Class Constructor
     *
     * @param posLocal   local team position
     * @param posVisitor visitor team position
     * @param distance   distance between stadiums
     */
    public LocalVisitorDistance(int posLocal, int posVisitor, double distance) {
        this.posLocal = posLocal;
        this.posVisitor = posVisitor;
        this.distance = distance;
    }

    /**
     * Method that return the position of the local team
     *
     * @return local team position
     */
    public int getPosLocal() {
        return posLocal;
    }

    /**
     * Method that set the position of the local team
     *
     * @param posLocal
     */
    public void setPosLocal(int posLocal) {
        this.posLocal = posLocal;
    }

    /**
     * Method that return the position of the visitor team
     *
     * @return visitor team position
     */
    public int getPosVisitor() {
        return posVisitor;
    }

    /**
     * Method that set the position of the local team
     *
     * @param posVisitor
     */
    public void setPosVisitor(int posVisitor) {
        this.posVisitor = posVisitor;
    }

    /**
     * Method that return the distance between stadiums
     *
     * @return distance
     */

    public double getDistance() {
        return distance;
    }

    /**
     * Method that set the distance between stadiums
     *
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

}
