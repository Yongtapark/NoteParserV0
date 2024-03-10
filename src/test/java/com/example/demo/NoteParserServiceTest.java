package com.example.demo;

import com.example.demo.domain.dto.NoteParserDto;
import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.NoteAndId;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import com.example.demo.domain.exceptions.NoteFormatException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("서비스 테스트")
class NoteParserServiceTest {
    @Autowired
    private NoteParserService noteParserService;

    @Test
    @DisplayName("특정 문자열을 추출해 regex 별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1_BARN() {
        final String ID = "1번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParserDto noteParserDto = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);
        Map<NoteRegex, NoteAndIdList> noteContainer = noteParserDto.getNoteContainerMap();

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
        NoteParserDto noteParserDto = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);
        Map<NoteRegex, NoteAndIdList> noteContainer = noteParserDto.getNoteContainerMap();

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
        NoteParserDto noteParserDto = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);
        Map<NoteRegex, NoteAndIdList> noteContainer = noteParserDto.getNoteContainerMap();

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
        Assertions.assertThatThrownBy(() -> noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }
    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void test2_specialCharacter() {
        final String ID = "1번축사@";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        Assertions.assertThatThrownBy(() -> noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }
    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_spaceBar() {
        final String ID = " ";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        Assertions.assertThatThrownBy(() -> noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }
    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_nothing() {
        final String ID = "";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        Assertions.assertThatThrownBy(() -> noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("복수로 id값을 입력하면 두 id에 동일한 note 가 저장된다.")
    void test3() {
        final String IDS = "1번축사,2번축사";
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParserDto noteParserDto = noteParserService.saveContentUseNoteParser("[[" + IDS + "]] " + NOTE);
        Map<NoteRegex, NoteAndIdList> noteContainer = noteParserDto.getNoteContainerMap();

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
}