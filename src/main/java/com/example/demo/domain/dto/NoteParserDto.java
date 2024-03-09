package com.example.demo.domain.dto;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import java.util.Map;

public class NoteParserDto {
    private final Map<NoteRegex,NoteAndIdList> noteContainerMap;

    public NoteParserDto(NoteContainer noteContainer) {
        this.noteContainerMap = noteContainer.getImmutableMap();
    }

    public Map<NoteRegex, NoteAndIdList> getNoteContainerMap() {
        return noteContainerMap;
    }
}
