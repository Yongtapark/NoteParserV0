package com.example.demo;

import static com.example.demo.enums.NoteRegex.NOTE_FORM;

import com.example.demo.enums.NoteRegex;
import com.example.demo.exceptions.NoteFormatException;
import java.util.HashMap;
import java.util.regex.Matcher;

public class NoteParser {
    public HashMap<NoteRegex, HashMap<String, String>> extractAndSaveNote(
            HashMap<NoteRegex, HashMap<String, String>> tags, String content) {
        Matcher extractIdAndNote = NOTE_FORM.extractString(content);
        if (extractIdAndNote.matches()) {
            String ids = extractIdAndNote.group(1);
            String note = extractIdAndNote.group(2);
            String[] idArray = ids.split(",");

            for (NoteRegex regex : NoteRegex.values()) {
                Matcher regexMatcher = regex.getCompile().matcher(ids);
                if (regexMatcher.matches()) {
                    HashMap<String, String> idAndNoteMap = tags.computeIfAbsent(regex, k -> new HashMap<>());
                    for (int i = 0; i < idArray.length; i++) {
                        idAndNoteMap.put(idArray[i], note);
                        idArray[i] = null;
                    }
                }
            }

            for (int i = 0; i < idArray.length; i++) {
                if (idArray[i] != null) {
                    throw new NoteFormatException(content);
                }
            }

        }
        return tags;
    }
}
