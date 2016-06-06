package chatUtils.net;


import chatUtils.data.ChatMessage;
import chatUtils.data.ObjectObserved;
import chatUtils.data.ObjectObserver;
import chatUtils.data.UserData;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.net.SocketChannelHandler;

/**
 * Talker: classe di appoggio per l'esecuzione in loop (polling) di metodi di 
 * invio e ricezione.
 * 
 * @author Edoardo Zanoni
 * @author Riccardo Bertarelli
 */
public class Talker implements Runnable {

    /**
    * TalkerType: enumerazione identificativa della tipologia di polling.
    */
    public enum TalkerType {
        
        READER, WRITER
    }
    
    private final UserData userData; // Mappa per la  gestione degli oggetti da inviare.
    private final ObjectObserved objectObserved; // Oggetto osservabile per verificare cambiamenti in lettura.
    private final ObjectObserver objectObserver; // Oggetto osservabile per verificare cambiamenti in scrittura.
    private final SocketChannelHandler socketChannelHandler; // Handler per la gestione degli stream.
    private final TalkerType talkerType; // Enumeratorre di ripo.
    
    /**
    * Costruttore: inizializza Talker, usando un channel per inviare
    * e ricevere oggetti.
    * 
    * @param userData L'<tt>UserData</tt> contenente le informazioni
    * dell'utente.
     * @param objectObserver L'oggetto osservato per rilevare i cambiamenti
    * in scrittura.
    * @param socketChannelHandler Il <tt>SocketChannelHandler</tt> di
    * connessione al server.
    */
    public Talker(UserData userData, SocketChannelHandler socketChannelHandler, ObjectObserver objectObserver) {
        
        this.userData = userData;
        this.objectObserver = objectObserver;
        this.objectObserved = null;
        this.socketChannelHandler = socketChannelHandler;
        this.talkerType = TalkerType.WRITER;
    }
    
    /**
    * Costruttore: inizializza Talker, usando un channel per inviare
    * e ricevere oggetti.
    * 
    * @param userData L'<tt>UserData</tt> contenente le informazioni
    * dell'utente.
     * @param objectObserved L'oggetto da osservare per rilevare i cambiamenti
    * in lettura.
    * @param socketChannelHandler Il <tt>SocketChannelHandler</tt> di
    * connessione al server.
    */
    public Talker(UserData userData, SocketChannelHandler socketChannelHandler, ObjectObserved objectObserved) {
        
        this.userData = userData;
        this.objectObserved = objectObserved;
        this.objectObserver = null;
        this.socketChannelHandler = socketChannelHandler;
        this.talkerType = TalkerType.READER;
    }
    
    /**
    * Talker.run: metodo lanciato durante la creazione di un nuovo Thread.
    */
    @Override
    public void run() {
        
        switch(talkerType) {
            
            case READER:
                this.reader();
                break;
            case WRITER:
                this.writer();
                break;
        }
    }
    
    /**
     * Talker.writer: metodo privato utilizzato per l'avvio del polling in
     * scrittura, utilizzando il <tt>SocketChannel</tt> ed inviando 
     * l'<tt>Object</tt> impostato come valore della dataMap 
     * all chiave <tt>TalkerType.WRITER</tt>.
     */
    private void writer(){
        
        ChatMessage chatMessage = new ChatMessage(this.userData.getUserName());
        chatMessage.setChatName("t");
        while(true) {
            
            while (!this.objectObserver.isUpdated()) {
                synchronized (this.objectObserver.getObjectObserved()) {
                    try {
                        this.objectObserved.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Talker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            chatMessage.setMessage((String) this.objectObserver.getObjectObserved());
            chatMessage.setDateTime();
            socketChannelHandler.pushToChannel(chatMessage);
        }
    }
    
    /**
     * Talker.reader: metodo privato utilizzato per l'avvio del polling in
     * lettura, utilizzando il <tt>SocketChannel</tt> specificato nel
     * costruttore per ricevere un oggetto e salvarlo nella <tt>dataMap</tt>
     * alla chiave <tt>TalkerType.READER</tt>.
     */
    private void reader() {
        
        ChatMessage incomingMessage;
        while(!((incomingMessage = (ChatMessage)socketChannelHandler.pullFromChannel()).getMessage().equals("stop"))) {
            
            System.out.println(incomingMessage);
        }
    }
}
