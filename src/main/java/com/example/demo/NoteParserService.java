package com.example.demo;

import com.example.demo.domain.note_parser.NoteContainer;
import com.example.demo.domain.note_parser.NoteParser;
import org.springframework.stereotype.Service;

@Service
public class NoteParserService {

    private final NoteParser noteParser;

    public NoteParserService(NoteParser noteParser) {
        this.noteParser = noteParser;
    }

    public NoteContainer saveContentUseNoteParser(String content) {
        NoteContainer noteContainer = new NoteContainer();
        return noteParser.extractAndSaveNote(noteContainer, content);
    }
}
