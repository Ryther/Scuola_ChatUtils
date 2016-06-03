package chatUtils.data;

import utils.data.CalendarUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edoardo Zanoni
 */
public class ChatMessage implements Serializable {
    
    private static final long serialVersionUID = 2L;
    private final String username;
    private String chatName;
    private LocalDateTime dateTime;
    private String message;

    public ChatMessage(String username) {
        
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setDateTime() {
        
        this.dateTime = LocalDateTime.now();
    }
    
    public void setDateTime(String dateTime) {
        
        try {
            this.dateTime = CalendarUtils.stringToDate(dateTime, Consts.dateFormat);
        } catch (ParseException ex) {
            Logger.getLogger(ChatMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDateTime() {
        
        try {
            return CalendarUtils.dateToString(this.dateTime, Consts.dateFormat);
        } catch (ParseException ex) {
            Logger.getLogger(ChatMessage.class.getName()).log(Level.SEVERE, null, ex);
            return "[ERR] DateError";
        }
    }

    public String getMessage() {
        
        return message;
    }

    public void setMessage(String message) {
        
        this.message = message;
    }
    
    @Override
    public String toString() {
        
        StringBuilder result = new StringBuilder();
        result.append(this.getDateTime())
                .append(" [")
                .append(this.getUsername())
                .append("] ")
                .append(this.getMessage());
        return result.toString();
    }
}
