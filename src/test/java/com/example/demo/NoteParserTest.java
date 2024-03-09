package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import com.example.demo.domain.note_parser.NoteAndId;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import com.example.demo.domain.note_parser.NoteParser;
import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("도메인 테스트")
class NoteParserTest {
    @Test
    @DisplayName("특정 문자열을 추출해 regex 별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1_BARN() {
        final String ID = "1번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        noteContainer = noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        Collection<NoteAndIdList> variableList = noteContainer.values();
        for (NoteAndIdList value : variableList) {
            List<NoteAndId> immutableList = value.getImmutableList();
            for (NoteAndId noteAndId : immutableList) {
                if (noteAndId.id().equals(ID)) {
                    note = noteAndId.note();
                }
            }
        }

        Assertions.assertThat(note).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("특정 문자열을 추출해 regex 별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1_PEN() {
        final String ID = "10-15";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        noteContainer = noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        Collection<NoteAndIdList> variableList = noteContainer.values();
        for (NoteAndIdList value : variableList) {
            List<NoteAndId> immutableList = value.getImmutableList();
            for (NoteAndId noteAndId : immutableList) {
                if (noteAndId.id().equals(ID)) {
                    note = noteAndId.note();
                }
            }
        }
        Assertions.assertThat(note).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("특정 문자열을 추출해 regex 별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1_COW() {
        final String ID = "1015";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        noteContainer = noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        Collection<NoteAndIdList> variableList = noteContainer.values();
        for (NoteAndIdList value : variableList) {
            List<NoteAndId> immutableList = value.getImmutableList();
            for (NoteAndId noteAndId : immutableList) {
                if (noteAndId.id().equals(ID)) {
                    note = noteAndId.note();
                }
            }
        }
        Assertions.assertThat(note).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void test2_justComma() {
        final String ID = "1번축사,";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void test2_specialCharacter() {
        final String ID = "1번축사@";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_spaceBar() {
        final String ID = " ";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_nothing() {
        final String ID = "";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(noteContainer, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("복수로 id값을 입력하면 두 id에 동일한 note 가 저장된다.")
    void test3_twoId_oneNote() {
        final String IDS = "1번축사,2번축사";
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        noteContainer = noteParser.extractAndSaveNote(noteContainer, "[[" + IDS + "]] " + NOTE);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        Collection<NoteAndIdList> variableList = noteContainer.values();
        for (NoteAndIdList value : variableList) {
            List<NoteAndId> immutableList = value.getImmutableList();
            for (NoteAndId noteAndId : immutableList) {
                if (noteAndId.id().equals(ID1)) {
                    note1 = noteAndId.note();
                } else if (noteAndId.id().equals(ID2)) {
                    note2 = noteAndId.note();
                }
            }
        }
        Assertions.assertThat(note1).isEqualTo(NOTE);
        Assertions.assertThat(note2).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("엔터로 구분하여 각각의 id와 노트를 저장한다.")
    void test3_twoId_eachNote() {
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String NOTE1 = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지1";
        final String NOTE2 = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지2";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();
        noteContainer = noteParser.extractAndSaveNote(noteContainer,
                "[[" + ID1 + "]] " + NOTE1 + System.lineSeparator() + "[[" + ID2 + "]] " + NOTE2);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        Collection<NoteAndIdList> variableList = noteContainer.values();
        for (NoteAndIdList value : variableList) {
            List<NoteAndId> immutableList = value.getImmutableList();
            for (NoteAndId noteAndId : immutableList) {
                if (noteAndId.id().equals(ID1)) {
                    note1 = noteAndId.note();
                } else if (noteAndId.id().equals(ID2)) {
                    note2 = noteAndId.note();
                }
            }
        }
        Assertions.assertThat(note1).isEqualTo(NOTE1);
        Assertions.assertThat(note2).isEqualTo(NOTE2);
    }

    @Test
    @DisplayName("같은 NoteRegex에 대해 서로 다른 두 줄이 독립적으로 처리되는지 검증한다.")
    void test5_separateProcessing_forSameNoteRegex() {
        final String ID1 = "1번축사";
        final String NOTE1 = "첫 번째 노트 내용";
        final String NOTE2 = "두 번째 노트 내용";
        NoteParser noteParser = new NoteParser();
        NoteContainer noteContainer = new NoteContainer();

        String combinedNotes = "[[" + ID1 + "]] " + NOTE1 + System.lineSeparator() +
                "[[" + ID1 + "]] " + NOTE2;
        noteContainer = noteParser.extractAndSaveNote(noteContainer, combinedNotes);

        NoteAndIdList notes = noteContainer.getNotes(NoteRegex.BARN);
        Assertions.assertThat(notes.getImmutableList()).hasSize(2);
        Assertions.assertThat(notes.get(0).note()).isEqualTo(NOTE1);
        Assertions.assertThat(notes.get(1).note()).isEqualTo(NOTE2);
    }
}