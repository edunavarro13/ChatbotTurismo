package es.ua.eduardo.duack.Models;

/**
 * Created by reale on 2/28/2017.
 */

public class ChatModel {
    public String message;
    public boolean isSend;
    public boolean esPregunta;

    public ChatModel(String message, boolean isSend, boolean esPregunta) {
        this.message = message;
        this.isSend = isSend;
        this.esPregunta = esPregunta;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isPregunta() {
        return esPregunta;
    }

    public void setPregunta(boolean esPregunta) {
        this.esPregunta = esPregunta;
    }
}
