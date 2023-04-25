package com.service.notes.exception;

public class ServerProcessingException extends RuntimeException{
    public ServerProcessingException(String message){
        super(message);
    }
}
