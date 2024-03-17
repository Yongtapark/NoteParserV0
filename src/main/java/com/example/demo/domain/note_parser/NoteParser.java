package com.example.demo.domain.note_parser;


import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.springframework.stereotype.Component;

@Component
public class NoteParser {
    final static int ID_GROUP_INDEX = 1;
    final static int NOTE_GROUP_INDEX = 2;
    final static String ID_DELIMITER = ",";

    public Map<NoteRegex, List<IdAndNote>> extractAndSaveNote(
            Map<NoteRegex, List<IdAndNote>> tags, String content) {
        String[] split = content.split(System.lineSeparator());
        for (String eachContext : split) {
            Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContext);
            if (!extractIdAndNote.matches()) {
                throw new NoteFormatException(content);
            }
            String[] idArray = extractAndSaveToMap(tags, extractIdAndNote);
            checkUnMatched(eachContext, idArray);

        }

        return tags;
    }

    private static String[] extractAndSaveToMap(Map<NoteRegex, List<IdAndNote>> tags, Matcher extractIdAndNote) {
        String ids = extractIdAndNote.group(ID_GROUP_INDEX);
        String note = extractIdAndNote.group(NOTE_GROUP_INDEX);
        String[] idArray = ids.split(ID_DELIMITER);

        for (NoteRegex regex : NoteRegex.values()) {
            Matcher regexMatcher = regex.getCompile().matcher(ids);
            if (regexMatcher.matches()) {
                List<IdAndNote> idAndNotesList = tags.computeIfAbsent(regex,k->new ArrayList<>());

                for (int i = 0; i < idArray.length; i++) {
                    IdAndNote idAndNote = new IdAndNote(idArray[i], note);
                    idAndNotesList.add(idAndNote);
                    idArray[i] = null;
                }
                tags.put(regex, idAndNotesList);
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
