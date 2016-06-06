package chatUtils.data;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Edoardo Zanoni
 */
public class ObjectObserver implements Observer {

    private static boolean updated = false;
    private Object objectObserved;
    
    public void observe(Observable object) {
        
        object.addObserver(this);
    }
    
    @Override
    public void update(Observable observable, Object object) {
        
        synchronized (this.objectObserved) {
            
            this.objectObserved = observable;
            ObjectObserver.updated = true;
            notifyAll();
        }
    }

    public boolean isUpdated() {
        
        return updated;
    }
    
    public void updatedFalse() {
        
        ObjectObserver.updated = false;
    }

    public Object getObjectObserved() {
        
        return objectObserved;
    }
}
