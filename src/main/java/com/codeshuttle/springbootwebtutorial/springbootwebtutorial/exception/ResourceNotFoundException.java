package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }


}
