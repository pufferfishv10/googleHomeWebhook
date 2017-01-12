package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/01/17.
 */
public class Messages {
    @JsonProperty("messages")
    private List<Message> messages =  new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(Message message) {
        this.messages.add(message);
    }
}
