package com.example.demo.domain.dto;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Map;

public class NoteParserDto {
    private  Map<NoteRegex,NoteAndIdList> noteContainerMap;
    public NoteParserDto(NoteContainer noteContainer) {
        this.noteContainerMap = noteContainer.getImmutableMap();
    }

    public Map<NoteRegex, NoteAndIdList> getNoteContainerMap() {
        return noteContainerMap;
    }

    public NoteParserDto(Map<NoteRegex, NoteAndIdList> noteContainerMap) {
        this.noteContainerMap = noteContainerMap;
    }

    public NoteParserDto() {
    }
}
