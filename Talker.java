
import java.util.Scanner;
import net.StreamHandler;

/**
 *
 * @author Edoardo Zanoni
 */
public class Talker implements Runnable {

    public enum TalkerType {
        
        READER, WRITER
    }
    
    private final StreamHandler streamHandler;
    private final TalkerType talkerType;
    private ChatMessage chatMessage;
    
    public Talker(StreamHandler streamHandler, ChatMessage chatMessage) {
        
        this.streamHandler = streamHandler;
        this.chatMessage = chatMessage;
        this.talkerType = TalkerType.WRITER;
    }
    
    public Talker(StreamHandler streamHandler) {
        
        this.streamHandler = streamHandler;
        this.talkerType = TalkerType.READER;
    }
    
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
    
    private void writer(){
        
        Scanner scanner = new Scanner(System.in);
        String message;
        while(!((message = scanner.nextLine()).equals(""))) {
            
            this.chatMessage.setMessage(message);
            this.chatMessage.setDate();
            streamHandler.pushToStream(this.chatMessage);
        }
    }
    
    private void reader() {
        
        while(true) {
            
            ChatMessage incomingMessage;
            incomingMessage = (ChatMessage)streamHandler.pullFromStream();
            System.out.println(incomingMessage.getDate() + " - [" + incomingMessage.getUsername() + "] " + incomingMessage.getMessage());
        }
    }
}
