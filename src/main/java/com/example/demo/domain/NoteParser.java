package com.example.demo.domain;

import static com.example.demo.domain.enums.NoteRegex.NOTE_FORM;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
@Component
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

            checkUnMatched(content, idArray);
        }else{
            throw new NoteFormatException(content);
        }
        return (HashMap<NoteRegex, HashMap<String, String>>) Map.copyOf(tags);
    }

    private static void checkUnMatched(String content, String[] idArray) {
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null) {
                throw new NoteFormatException(content);
            }
        }
    }
}
