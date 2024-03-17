package com.example.demo.domain.note_parser;


import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import org.springframework.stereotype.Component;

@Component
public class NoteParser {
    final static int GROUP_ONE = 1;
    final static int GROUP_TWO = 2;
    final static String SPLIT_DELIMITER = ",";
    public Map<NoteRegex, Map<String, String>> extractAndSaveNote(
            Map<NoteRegex, Map<String, String>> tags, String content) {
        String[] split = content.split(System.lineSeparator());
        for (String eachContext : split) {
            Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContext);
            if (extractIdAndNote.matches()) {
                String[] idArray = extractAndSaveToMap(tags, extractIdAndNote);
                checkUnMatched(content, idArray);
            } else {
                throw new NoteFormatException(content);
            }
        }

        return tags;
    }

    private static String[] extractAndSaveToMap(Map<NoteRegex, Map<String, String>> tags, Matcher extractIdAndNote) {
        String ids = extractIdAndNote.group(GROUP_ONE);
        String note = extractIdAndNote.group(GROUP_TWO);
        String[] idArray = ids.split(SPLIT_DELIMITER);

        for (NoteRegex regex : NoteRegex.values()) {
            Matcher regexMatcher = regex.getCompile().matcher(ids);
            if (regexMatcher.matches()) {
                Map<String, String> idAndNoteMap = tags.computeIfAbsent(regex, k -> new HashMap<>());
                for (int i = 0; i < idArray.length; i++) {
                    idAndNoteMap.put(idArray[i], note);
                    idArray[i] = null;
                }
                tags.put(regex, idAndNoteMap);
            }
        }
        return idArray;
    }

    private static void checkUnMatched(String content, String[] idArray) {
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null) {
                throw new NoteFormatException(content);
            }
        }
    }
}
