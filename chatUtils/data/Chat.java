package chatUtils.data;

import chatUtils.data.UserData;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Edoardo Zanoni
 */
public class Chat implements Comparable{
    
    private final String chatName;
    private final Set<UserData> users;
    private final List<String> log;
    
    public Chat(String chatName) {
        
        this.chatName = chatName;
        this.users = new TreeSet();
        this.log = new LinkedList();
    }

    public String getChatName() {
        
        return chatName;
    }

    public Set<UserData> getUsers() {
        
        return users;
    }

    public List<String> getLog() {
        
        return log;
    }
    
    public List<String> getLog(int size) {
        
        return this.log.subList(
                this.log.size()-(size+1),
                this.log.size());
    }
    
    public boolean addUser(UserData userdata) {
        
        return this.users.add(userdata);
    }
    
    public boolean removeUser(String userName) {
        
        return this.users.remove(userName);
    }

    @Override
    public int compareTo(Object o) {
        
        if (o == null) {
            throw new NullPointerException();
        }
        if (o instanceof UserData) {
            
            UserData cast = (UserData) o;
            return this.chatName.compareTo(cast.getUserName());
        } else {
            
            throw new ClassCastException();
        }
    }
    
    @Override
    public int hashCode() {
        
        return this.chatName.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if (o instanceof String) {
            
            String cast = (String) o;
            return this.chatName.equals(cast);
        }
        
        return false;
    }
}
