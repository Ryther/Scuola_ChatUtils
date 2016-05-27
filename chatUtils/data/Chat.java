package chatUtils.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Chat: classe per la gestione di una singola chat, instesa coma stanza dove 
 * scrivono tutti i client connessi alla stessa.
 * 
 * @author Edoardo Zanoni
 */
public class Chat implements Comparable{
    
    private final String chatName; // Nome della chat
    private final Set<UserData> users; // Client attualmente connessi
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
        this.users = new TreeSet();
        this.log = new LinkedList();
    }
    
    /**
     * Chat.getChatName: metodo pubblico per avere il nome della chat.
     * 
     * @return <tt>String</tt> che identifica il nome della chat.
     */
    public String getChatName() {
        
        return chatName;
    }
    
    /**
     * Chat.getUsers: metodo pubblico per avere la lista di utenti.
     * 
     * @return <tt>Set</tt> che contiene i nomi degli utenti della chat.
     */
    public Set<UserData> getUsers() {
        
        return users;
    }
    
    /**
     * Chat.getLog: metodo pubblico per avere la <tt>List</tt> di messaggi della
     * chat.
     * 
     * @return <tt>List</tt> di tutti i messaggi sulla chat.
     */
    public List<String> getLog() {
        
        return log;
    }
    
    /**
     * Chat.getLog: metodo pubblico per avere la <tt>List</tt> di ultimi messaggi
     * della chat.
     * 
     * <tt></tt>
     * 
     * @param size dell'attuale log presente sul Client.
     * @return <tt>List</tt> di tutti i messaggi sulla chat.
     */
    public List<String> getLog(int size) {
        
        return this.log.subList(
                this.log.size()-(size+1),
                this.log.size());
    }
    
    /**
     * Chat.addUser: metodo pubblico per aggoingere un utente alla chat.
     * 
     * <tt></tt>
     * @param userData Oggetto con tutti i dati dell'utente.
     * @return <tt>boolean</tt> <i>true</i> se l'inserimento è riuscito 
     * <i>false</i> se l'inserimento è fallito
     */
    public boolean addUser(UserData userData) {
        
        return this.users.add(userData);
    }
    
    /**
     * Chat.removeUser
     * 
     * @param userName
     * @return 
     */
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
