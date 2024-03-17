package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

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
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);

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
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);

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
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);

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
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser("[[" + IDS + "]] " + NOTE);


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
    @DisplayName("해시맵을 불변객체로 반환한다")
    void test4() {
        final String IDS = "1번축사,2번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        Map<NoteRegex, Map<String, String>> tags = noteParserService.saveContentUseNoteParser("[[" + IDS + "]] " + NOTE);
        Map<String, String> unmodifiableMap = tags.get(NoteRegex.BARN);
        Assertions.assertThatThrownBy(()->unmodifiableMap.put("tmp","temp")).isInstanceOf(UnsupportedOperationException.class);


    }
}