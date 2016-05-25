
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
        while(!((message = scanner.nextLine()).equals("stop"))) {
            
            this.chatMessage.setMessage(message);
            this.chatMessage.setDateTime();
            streamHandler.pushToStream(this.chatMessage);
        }
    }
    
    private void reader() {
        
        ChatMessage incomingMessage;
        while(!((incomingMessage = (ChatMessage)streamHandler.pullFromStream()).getMessage().equals("stop"))) {
            
            System.out.println(incomingMessage.getDateTime() + " - [" + incomingMessage.getUsername() + "] " + incomingMessage.getMessage());
        }
    }
}
