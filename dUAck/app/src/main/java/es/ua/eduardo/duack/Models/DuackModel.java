package es.ua.eduardo.duack.Models;

/**
 * Created by reale on 2/28/2017.
 */

public class DuackModel {
    public String message;
    public boolean isSend;

    public DuackModel(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public DuackModel() {
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
}
