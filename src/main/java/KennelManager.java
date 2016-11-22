import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;

/**
 * A singleton created to manage the shared Kennel instance for our dog kennel web app.
 * <p>
 * Created by Dylan McKee on 18/11/2016.
 */
public class KennelManager {

    /**
     * KennelManager singleton instance.
     */
    private static final KennelManager ourInstance = new KennelManager();

    /**
     * The shared Kennel instance, to be accessible throughout the app.
     */
    private final Kennel kennel = KennelFactory.initialiseKennel();

    /**
     * A blank private constructor; ensures singleton pattern is maintained.
     */
    private KennelManager() {

    }

    /**
     * Gets singleton instance.
     *
     * @return the KennelManager singleton instance.
     */
    public static KennelManager getInstance() {
        return ourInstance;
    }

    /**
     * Gets the shared kennel instance, provides better level of abstraction than exposing instance var.
     *
     * @return the app-wide Kennel instance.
     */
    public Kennel getKennel() {
        return kennel;
    }


}
