package chatUtils.data;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import utils.net.StreamHandler;

/**
 *
 * @author Edoardo Zanoni
 */
public class UserData implements Serializable, Comparable {
    
    private static final long serialVersionUID = 1L;
    private final String userName;
    private final ConcurrentHashMap<Integer, Chat> chats;
    private StreamHandler streamHandler;

    public UserData(String userName) {
        
        this.userName = userName;
        this.chats = new ConcurrentHashMap();
    }

    public StreamHandler getStreamHandler() {
        
        return streamHandler;
    }

    public void setStreamHandler(StreamHandler streamHandler) {
        
        this.streamHandler = streamHandler;
    }

    public String getUserName() {
        
        return userName;
    }

    public ConcurrentHashMap<Integer, Chat> getChats() {
        
        return chats;
    }
    
    public void addChat(Chat chat) {
        
        this.chats.put(chat.hashCode(), chat);
    }

    @Override
    public int compareTo(Object o) {
        
        if (o == null) {
            throw new NullPointerException();
        }
        if (o instanceof UserData) {
            
            UserData cast = (UserData) o;
            return this.userName.compareTo(cast.getUserName());
        } else {
            
            throw new ClassCastException();
        }
    }
    
    @Override
    public int hashCode() {
        
        return this.userName.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (o instanceof String) {
            
            String cast = (String) o;
            return this.userName.equals(cast);
        }
        
        return false;
    }
}