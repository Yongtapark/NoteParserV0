package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import com.example.demo.domain.note_parser.NoteParser;
import java.util.HashMap;
import java.util.Map;
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
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (Map<String, String> hashMap : tags.values()) {
            if (hashMap.containsKey(ID)) {
                note = hashMap.get(ID);
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
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (Map<String, String> hashMap : tags.values()) {
            if (hashMap.containsKey(ID)) {
                note = hashMap.get(ID);
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
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (Map<String, String> hashMap : tags.values()) {
            if (hashMap.containsKey(ID)) {
                note = hashMap.get(ID);
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
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void test2_specialCharacter() {
        final String ID = "1번축사@";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_spaceBar() {
        final String ID = " ";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_nothing() {
        final String ID = "";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("복수로 id값을 입력하면 두 id에 동일한 note 가 저장된다.")
    void test3() {
        final String IDS = "1번축사,2번축사";
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + IDS + "]] " + NOTE);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        for (Map<String, String> hashMap : tags.values()) {
            note1 = hashMap.get(ID1);
            note2 = hashMap.get(ID2);
        }
        Assertions.assertThat(note1).isEqualTo(NOTE);
        Assertions.assertThat(note2).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("서로 다른 regex 를 동시에 저장하면 각자의 반환값을 가진다.")
    void test4() {
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String ID3 = "3-1";
        final String NOTE1 = "몸살이 났다. 머리가 지끈거린다.";
        final String NOTE2 = "로나였다. 죽다 살아났다.";
        final String NOTE3 = "다시 재미있는 코딩을 시작해보자";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, Map<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID1 + "]] " + NOTE1 + System.lineSeparator()
                + "[[" + ID2 + "]] " + NOTE2 + System.lineSeparator()
                + "[[" + ID3 + "]] " + NOTE3);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        String note3 = "NOTHING";
        note1 = tags.get(NoteRegex.BARN).get(ID1);
        note2 = tags.get(NoteRegex.BARN).get(ID2);
        note3 = tags.get(NoteRegex.PEN).get(ID3);
        Assertions.assertThat(note1).isEqualTo(NOTE1);
        Assertions.assertThat(note2).isEqualTo(NOTE2);
        Assertions.assertThat(note3).isEqualTo(NOTE3);
    }
}