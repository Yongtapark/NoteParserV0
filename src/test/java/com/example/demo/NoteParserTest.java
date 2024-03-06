package com.example.demo;

import com.example.demo.enums.NoteRegex;
import com.example.demo.exceptions.NoteFormatException;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteParserTest {
    @Test
    @DisplayName("특정 문자열을 추출해 regex 별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1() {
        final String ID = "1번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        HashMap<NoteRegex, HashMap<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (HashMap<String, String> hashMap : tags.values()) {
            if (hashMap.containsKey(ID)) {
                note = hashMap.get(ID);
            }
        }
        Assertions.assertThat(note).isEqualTo(NOTE);
    }

    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void name2() {
        final String ID = "1번축사,";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        HashMap<NoteRegex, HashMap<String, String>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("복수로 id값을 입력하면 두 id에 동일한 note 가 저장된다.")
    void name3() {
        final String IDS = "1번축사,2번축사";
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        HashMap<NoteRegex, HashMap<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + IDS + "]] " + NOTE);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        for (HashMap<String, String> hashMap : tags.values()) {
            note1 = hashMap.get(ID1);
            note2 = hashMap.get(ID2);
        }
        Assertions.assertThat(note1).isEqualTo(NOTE);
        Assertions.assertThat(note2).isEqualTo(NOTE);
    }
}