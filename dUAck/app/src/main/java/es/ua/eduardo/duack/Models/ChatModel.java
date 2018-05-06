package es.ua.eduardo.duack.Models;

/**
 * Created by reale on 2/28/2017.
 */

public class ChatModel {
    public String message;
    public boolean isSend;
    public int esPregunta; // 0 pregunta, 1 informacion_tipos, 2 informacion_subtipos, 3 nada

    public ChatModel(String message, boolean isSend, int esPregunta) {
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

    public int isPregunta() {
        return esPregunta;
    }

    public void setPregunta(int esPregunta) {
        this.esPregunta = esPregunta;
    }
}
