package chatUtils.net;


import chatUtils.data.ChatMessage;
import java.util.Scanner;
import utils.net.StreamHandler;

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
    
    private final StreamHandler streamHandler; // Handler per la gestione degli stream.
    private final TalkerType talkerType; // Enumeratorre di ripo.
    private ChatMessage chatMessage; // Classe di gestione dei messaggi.
    
    /**
    * Costruttore: inizializza Talker come TalkerType.WRITER, usando lo stream
    * per inviare un oggetto chatMessage.
    * 
    * @param streamHandler Lo StreamHandler da utilizzare per inviare.
    * @param chatMessage L'oggetto ChatMessage da gestire.
    */
    public Talker(StreamHandler streamHandler, ChatMessage chatMessage) {
        
        this.streamHandler = streamHandler;
        this.chatMessage = chatMessage;
        this.talkerType = TalkerType.WRITER;
    }
    
    /**
    * Costruttore: inizializza Talker come TalkerType.READER, usando lo stream
    * per ricevere un oggetto di tipo ChatMessage.
    * 
    * @param streamHandler Lo StreamHandler da utilizzare per ricevere.
    */
    public Talker(StreamHandler streamHandler) {
        
        this.streamHandler = streamHandler;
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
     * scrittura, utilizzando lo StreamHandler ed il ChatMessage specificati
     * nel costruttore per inviare messaggi sullo stream.
     */
    private void writer(){
        
        Scanner scanner = new Scanner(System.in);
        String message;
        while(!((message = scanner.nextLine()).equals("stop"))) {
            
            this.chatMessage.setMessage(message);
            this.chatMessage.setDateTime();
            streamHandler.pushToStream(this.chatMessage);
        }
    }
    
    /**
     * Talker.reader: metodo privato utilizzato per l'avvio del polling in
     * lettura, utilizzando lo StreamHandler specificato nel costruttore per 
     * ricevere un oggetto ChatMessage e mostrarlo sulla console.
     */
    private void reader() {
        
        ChatMessage incomingMessage;
        while(!((incomingMessage = (ChatMessage)streamHandler.pullFromStream()).getMessage().equals("stop"))) {
            
            System.out.println(incomingMessage.getDateTime() + " - [" + incomingMessage.getUsername() + "] " + incomingMessage.getMessage());
        }
    }
}
