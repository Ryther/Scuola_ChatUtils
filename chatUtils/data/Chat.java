package chatUtils.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chat: classe per la gestione di una singola chat, instesa coma stanza dove 
 * scrivono tutti i client connessi alla stessa.
 * 
 * @author Edoardo Zanoni
 */
public class Chat implements Serializable, Comparable{
    
    private static final long serialVersionUID = 1L;
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
     * @return <tt>ConcurrentHashMap</tt> che contiene i nomi degli utenti della
     * chat.
     */
    public ConcurrentHashMap<Integer, UserData> getUsers() {
        
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
     * @param size dell'attuale log presente sul Client.
     * @return <tt>List</tt> di tutti i messaggi sulla chat.
     */
    public List<String> getLog(int size) {
        
        return this.log.subList(
                this.log.size()-(size+1),
                this.log.size());
    }
    
    /**
     * Chat.addUser: metodo pubblico per aggiungere un utente alla chat.
     * 
     * @param userData Oggetto con tutti i dati dell'utente.
     */
    public void addUser(UserData userData) {
        
        this.users.put(userData.hashCode(), userData);
    }
    
    /**
     * Chat.removeUser: metodo pubblico per rimuovere un utente alla chat.
     * 
     * @param userName Stringa contenente il nome del'urente da rimuovere.
     */
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