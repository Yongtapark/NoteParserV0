package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import java.util.Map;
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
    public ResponseEntity<Map<NoteRegex, Map<String, String>> > parseNote(@RequestBody String content) {
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser(content);
        return ResponseEntity.ok(tags);
    }
}
