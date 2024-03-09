package com.example.demo;

import com.example.demo.domain.note_parser.NoteContainer;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NoteContainer> parseNote(@RequestBody String content) {
        NoteContainer noteContainer = noteParserService.saveContentUseNoteParser(content);
        return ResponseEntity.ok(noteContainer);
    }
}
