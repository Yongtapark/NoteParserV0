package com.example.demo;

import static com.example.demo.enums.NoteRegex.NOTE_FORM;

import com.example.demo.enums.NoteRegex;
import java.util.HashMap;
import java.util.regex.Matcher;

public class NoteParser {
    public HashMap<NoteRegex,HashMap<String,String>> extractAndSaveNote(HashMap<NoteRegex,HashMap<String,String>> tags, String content){
        Matcher extractIdAndNote = NOTE_FORM.extractString(content);
        if(extractIdAndNote.matches()){
            String ids = extractIdAndNote.group(1);
            String note = extractIdAndNote.group(2);
            for(NoteRegex regex : NoteRegex.values()){
                Matcher regexMatcher = regex.getCompile().matcher(ids);
                if(regexMatcher.matches()){
                    String[] idArray = ids.split(",");
                    HashMap<String, String> idAndNoteMap = tags.computeIfAbsent(regex,k->new HashMap<>());
                    for (String id : idArray) {
                        idAndNoteMap.put(id,note);
                    }
                }
            }
        }
        return tags;
    }
}
