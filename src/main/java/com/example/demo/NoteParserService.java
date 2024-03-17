package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.IdAndNote;
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

    public Map<NoteRegex, List<IdAndNote>> saveContentUseNoteParser(String content) {
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        return noteParser.extractAndSaveNote(tags, content);
    }
}
