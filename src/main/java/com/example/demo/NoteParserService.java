package com.example.demo;

import com.example.demo.domain.NoteParser;
import com.example.demo.domain.enums.NoteRegex;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NoteParserService {

    private final NoteParser noteParser;

    public NoteParserService(NoteParser noteParser) {
        this.noteParser = noteParser;
    }

    public HashMap<NoteRegex, HashMap<String, String>> saveContentUseNoteParser(String content){
        HashMap<NoteRegex, HashMap<String, String>> tags = new HashMap<>();
        return noteParser.extractAndSaveNote(tags,content);
    }
}
