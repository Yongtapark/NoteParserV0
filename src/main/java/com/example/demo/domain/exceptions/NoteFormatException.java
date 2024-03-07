package com.example.demo.domain.exceptions;

public class NoteFormatException extends AnnounceErrorException {
    private final static String DEFAULT_MESSAGE = "유효하지 않은 노트입니다. 다시 입력해 주세요 = ";

    public NoteFormatException(String message) {
        super(DEFAULT_MESSAGE + message);
    }
}
