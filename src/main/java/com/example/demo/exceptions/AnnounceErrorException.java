package com.example.demo.exceptions;

public class AnnounceErrorException extends IllegalArgumentException{
   public AnnounceErrorException(String message) {
       super("[ERROR] " + message);
   }
}
