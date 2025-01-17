package org.invest.exeption;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
