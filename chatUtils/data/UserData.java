package chatUtils.data;

import java.util.Set;
import java.util.TreeSet;
import utils.net.StreamHandler;

/**
 *
 * @author Edoardo Zanoni
 */
public class UserData implements Comparable {
    
    private final String userName;
    private final Set<Chat> chats;
    private StreamHandler streamHandler;

    public UserData(String userName) {
        
        this.userName = userName;
        this.chats = new TreeSet();
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

    public Set<Chat> getChats() {
        
        return chats;
    }
    
    public boolean addChat(Chat chat) {
        
        return this.chats.add(chat);
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