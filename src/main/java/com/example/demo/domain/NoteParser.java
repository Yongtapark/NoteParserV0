package com.example.demo.domain;

import static com.example.demo.domain.enums.NoteRegex.NOTE_FORM;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
@Component
public class NoteParser {
    public Map<NoteRegex, Map<String, String>> extractAndSaveNote(
            Map<NoteRegex, Map<String, String>> tags, String content) {
        Matcher extractIdAndNote = NOTE_FORM.extractString(content);
        if (extractIdAndNote.matches()) {
            String ids = extractIdAndNote.group(1);
            String note = extractIdAndNote.group(2);
            String[] idArray = ids.split(",");

            for (NoteRegex regex : NoteRegex.values()) {
                Matcher regexMatcher = regex.getCompile().matcher(ids);
                if (regexMatcher.matches()) {
                    Map<String, String> idAndNoteMap = tags.computeIfAbsent(regex, k -> new HashMap<>());
                    for (int i = 0; i < idArray.length; i++) {
                        idAndNoteMap.put(idArray[i], note);
                        idArray[i] = null;
                    }
                    tags.put(regex, Collections.unmodifiableMap(idAndNoteMap));
                }
            }

            checkUnMatched(content, idArray);
        }else{
            throw new NoteFormatException(content);
        }
        return Map.copyOf(tags);
    }

    private static void checkUnMatched(String content, String[] idArray) {
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null) {
                throw new NoteFormatException(content);
            }
        }
    }
}
