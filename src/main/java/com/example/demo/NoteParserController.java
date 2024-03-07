package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/note-parser/")
public class NoteParserController {

    private final NoteParserService noteParserService;

    public NoteParserController(NoteParserService noteParserService) {
        this.noteParserService = noteParserService;
    }


    @PostMapping("/parse")
    public ResponseEntity<HashMap<NoteRegex, HashMap<String, String>> > parseNote(@RequestBody String content) {
        HashMap<NoteRegex, HashMap<String, String>> tags = noteParserService.saveContentUseNoteParser(content);
        return ResponseEntity.ok(tags);
    }
}
