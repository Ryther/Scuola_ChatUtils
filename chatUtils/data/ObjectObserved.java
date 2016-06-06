package chatUtils.data;

import java.util.Observable;

/**
 *
 * @author Edoardo Zanoni
 */
public class ObjectObserved extends Observable{
    
    private static Object objectObserved;
    
    public void setObjectObserved(Object object) {
        
        synchronized(this) {
            ObjectObserved.objectObserved = object;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized Object getObjectObserved() {
        
        return objectObserved;
    }
}
