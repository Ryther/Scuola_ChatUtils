package chatUtils.net;


import chatUtils.data.ChatMessage;
import chatUtils.data.UserData;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
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
    private final Map<TalkerType, Object> dataMap; // Mappa per la  gestione degli oggetti da inviare.
    private final SocketChannelHandler socketChannelHandler; // Handler per la gestione degli stream.
    private final TalkerType talkerType; // Enumeratorre di ripo.
    
    /**
    * Costruttore: inizializza Talker, usando un channel per inviare
    * e ricevere oggetti.
    * 
    * @param userData L'<tt>UserData</tt> contenente le informazioni
    * dell'utente.
    * @param dataMap Una <tt>ConcurentHashMap<TalkerType, Object></tt> 
    * contenente due valori, entrambi con chiave di tipo <tt>TalkerType</tt>
    * e valori di tipo <tt>Object</tt>.
    * @param socketChannelHandler Il <tt>SocketChannelHandler</tt> di
    * connessione al server.
    * @param talkerType La tipologia di <tt>Runnable</tt> che si vuole creare,
    * con stati selezionabili tra: {@link TalkerType}.
    */
    public Talker(UserData userData, Map<TalkerType, Object> dataMap,SocketChannelHandler socketChannelHandlerù, TalkerType talkerType) {
        
        this.userData = userData;
        this.dataMap = new ConcurrentHashMap();
        this.socketChannelHandler = socketChannelHandlerù;
        this.talkerType = talkerType;
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
        
        Scanner scanner = new Scanner(System.in);
        String message;
        ChatMessage chatMessage = new ChatMessage(userData.getUserName());
        chatMessage.setChatName("t");
        while(!((message = scanner.nextLine()).equals("stop"))) {
            
            chatMessage.setMessage(message);
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
