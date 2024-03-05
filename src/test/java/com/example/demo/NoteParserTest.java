package com.example.demo;

import com.example.demo.enums.NoteRegex;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteParserTest {
    @Test
    @DisplayName("특정 문자열을 추출해 regex별로 id, note 로 구분하여 해시맵에 저장한다.")
    void test1() {
        final String ID = "1번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        HashMap<NoteRegex, HashMap<String, String>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[["+ID+"]] "+NOTE);
        String note = "NOTHING";
        for (HashMap<String, String> hashMap : tags.values()) {
            if(hashMap.containsKey(ID)) {
                note = hashMap.get(ID);
            }
        }
        Assertions.assertThat(note).isEqualTo(NOTE);
    }
}