import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;

/**
 * Created by djmckee on 18/11/2016.
 */
public class KennelManager {

    private static final KennelManager ourInstance = new KennelManager();
    private final Kennel kennel = KennelFactory.initialiseKennel();

    private KennelManager() {

    }

    public static KennelManager getInstance() {
        return ourInstance;
    }

    public Kennel getKennel() {
        return kennel;
    }


}
