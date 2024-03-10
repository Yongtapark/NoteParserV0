package com.example.demo.domain.note_parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
public class NoteAndIdList {
    List<NoteAndId> noteAndIds;

    public NoteAndIdList() {
        this.noteAndIds = new ArrayList<>();
    }

    public NoteAndIdList(List<NoteAndId> immutableNoteAndIds) {
        this.noteAndIds = immutableNoteAndIds;
    }

    public NoteAndId get(int index){
        return noteAndIds.get(index);
    }
    public void add(NoteAndId noteAndId){
        noteAndIds.add(noteAndId);
    }

    public List<NoteAndId> getImmutableList(){
       return List.copyOf(noteAndIds);
    }

    @JsonIgnore
    public NoteAndIdList getImmutableNoteAndIdList(){
        return new NoteAndIdList(getImmutableList());
    }
}
