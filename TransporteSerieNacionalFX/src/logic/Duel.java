package logic;

/**
 * Class responsible for representations of the duel between two teams
 *
 * @author Rafael Gayoso and Mario Herrera
 */
public class Duel {

    private String local;//Name of the local team
    private String visitor;//Name of the visitor team

    /**
     * Class constructor
     *
     * @param local   name of the local team
     * @param visitor name of the visitor team
     */
    public Duel(String local, String visitor) {
        this.local = local;
        this.visitor = visitor;
    }

    /**
     * Return the local team name
     *
     * @return local
     */
    public String getLocal() {
        return local;
    }

    /**
     * Set the local team name
     *
     * @param local
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Return the visitor team name
     *
     * @return visitor
     */
    public String getVisitor() {
        return visitor;
    }

    /**
     * Set the visitor team name
     *
     * @param visitor
     */
    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }
}
