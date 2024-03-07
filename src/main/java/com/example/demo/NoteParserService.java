package com.example.demo;

import com.example.demo.domain.NoteParser;
import com.example.demo.domain.enums.NoteRegex;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class NoteParserService {

    private final NoteParser noteParser;

    public NoteParserService(NoteParser noteParser) {
        this.noteParser = noteParser;
    }

    public Map<NoteRegex, Map<String, String>> saveContentUseNoteParser(String content) {
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        return noteParser.extractAndSaveNote(tags, content);
    }
}
