package com.example.demo.domain.note_parser;


import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import java.util.regex.Matcher;
import org.springframework.stereotype.Component;

@Component
public class NoteParser {
    public NoteContainer extractAndSaveNote(
            NoteContainer noteContainer, String content) {
        NoteAndIdList noteAndIdList = new NoteAndIdList();
        String[] split = content.split(System.lineSeparator());
        for (String eachContent : split) {
            Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
            if (extractIdAndNote.matches()) {
                String ids = extractIdAndNote.group(1);
                String note = extractIdAndNote.group(2);
                String[] idArray = ids.split(",");

                findEachRegexAndSaveNotes(noteContainer, ids, idArray, noteAndIdList, note);
                checkDismatchIds(eachContent, idArray);
            } else {
                throw new NoteFormatException(content);
            }
        }
        return noteContainer;
    }

    private static void findEachRegexAndSaveNotes(NoteContainer noteContainer, String ids, String[] idArray,
                                                  NoteAndIdList noteAndIdList, String note) {
        for (NoteRegex regex : NoteRegex.values()) {
            Matcher regexMatcher = regex.getCompile().matcher(ids);
            saveIfRegexMatch(noteContainer, idArray, noteAndIdList, note, regex, regexMatcher);
        }
    }

    private static void saveIfRegexMatch(NoteContainer noteContainer, String[] idArray, NoteAndIdList noteAndIdList,
                                         String note, NoteRegex regex, Matcher regexMatcher) {
        if (regexMatcher.matches()) {
            for (int i = 0; i < idArray.length; i++) {
                noteAndIdList.add(new NoteAndId(idArray[i], note));
                idArray[i] = null;
            }
            noteContainer.putNotes(regex, noteAndIdList);
        }
    }

    private static void checkDismatchIds(String eachContent, String[] idArray) {
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] != null) {
                throw new NoteFormatException(eachContent);
            }
        }
    }
}
