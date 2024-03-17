package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.IdAndNote;
import com.example.demo.domain.dtos.NoteParserDto;
import com.example.demo.domain.note_parser.NoteParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class NoteParserService {

    private final NoteParser noteParser;

    public NoteParserService(NoteParser noteParser) {
        this.noteParser = noteParser;
    }

    public NoteParserDto saveContentUseNoteParser(String content) {
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        return new NoteParserDto(noteParser.extractAndSaveNote(tags, content));
    }
}
