package chatUtils.data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat: classe per la gestione di una singola chat, instesa coma stanza dove 
 * scrivono tutti i client connessi alla stessa.
 * 
 * @author Edoardo Zanoni
 */
public class Chat implements Comparable{
    
    private final String chatName; // Nome della chat
    private final ConcurrentHashMap<Integer, UserData> users; // Client attualmente connessi
    private final List<String> log; // Lo storico della chat
    
    /**
     * Costruttore: ricevendo il nome della chat crea un nuovo <tt>TreeSet</tt> 
     * per contenere gli utenti che saranno presenti nella chat e una nuova 
     * <tt>LinkedList</tt> per contenere tutti i messaggi della chat.
     * 
     * @param chatName <tt>String</tt> che identifica il nome della chat.
     */
    
    public Chat(String chatName) {
        
        this.chatName = chatName;
        this.users = new ConcurrentHashMap();
        this.log = new LinkedList();
    }

    public String getChatName() {
        
        return chatName;
    }

    public ConcurrentHashMap<Integer, UserData> getUsers() {
        
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
    
    public void addUser(UserData userData) {
        
        this.users.put(userData.hashCode(), userData);
    }
    
    public void removeUser(String userName) {
        
        this.users.remove(userName.hashCode());
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
