package com.example.demo.domain.exceptions;

public class AnnounceErrorException extends IllegalArgumentException{
   public AnnounceErrorException(String message) {
       super("[ERROR] " + message);
   }
}
