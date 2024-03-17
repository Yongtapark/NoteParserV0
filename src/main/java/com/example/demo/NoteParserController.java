package com.example.demo;

import com.example.demo.domain.dtos.NoteParserDto;
import com.example.demo.domain.exceptions.NoteFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note-parser/")
public class NoteParserController {

    private final NoteParserService noteParserService;

    public NoteParserController(NoteParserService noteParserService) {
        this.noteParserService = noteParserService;
    }


    @PostMapping("/parse")
    public ResponseEntity<NoteParserDto> parseNote(@RequestBody String content) {
        return ResponseEntity.ok( noteParserService.saveContentUseNoteParser(content));
    }
    @ExceptionHandler(NoteFormatException.class)
    public ResponseEntity<String> handleNoteFormatException(NoteFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
