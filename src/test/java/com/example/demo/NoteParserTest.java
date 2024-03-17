package com.example.demo;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.exceptions.NoteFormatException;
import com.example.demo.domain.note_parser.IdAndNote;
import com.example.demo.domain.note_parser.NoteParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (List<IdAndNote> idAndNoteList : tags.values()) {
            if (idAndNoteList.contains(new IdAndNote(ID, NOTE))) {
                int i = idAndNoteList.indexOf(new IdAndNote(ID, NOTE));
                note = idAndNoteList.get(i).note();
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (List<IdAndNote> idAndNoteList : tags.values()) {
            if (idAndNoteList.contains(new IdAndNote(ID, NOTE))) {
                int i = idAndNoteList.indexOf(new IdAndNote(ID, NOTE));
                note = idAndNoteList.get(i).note();
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE);

        String note = "NOTHING";
        for (List<IdAndNote> idAndNoteList : tags.values()) {
            if (idAndNoteList.contains(new IdAndNote(ID, NOTE))) {
                int i = idAndNoteList.indexOf(new IdAndNote(ID, NOTE));
                note = idAndNoteList.get(i).note();
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("잘못된 형식으로 입력 시 예외를 발생시킨다")
    void test2_specialCharacter() {
        final String ID = "1번축사@";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_spaceBar() {
        final String ID = " ";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        Assertions.assertThatThrownBy(() -> noteParser.extractAndSaveNote(tags, "[[" + ID + "]] " + NOTE))
                .isInstanceOf(NoteFormatException.class);
    }

    @Test
    @DisplayName("공백 입력 시 예외를 발생시킨다")
    void test2_nothing() {
        final String ID = "";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + IDS + "]] " + NOTE);

        String note1 = "NOTHING";
        String note2 = "NOTHING";
        for (List<IdAndNote> idAndNoteList : tags.values()) {
            if (idAndNoteList.contains(new IdAndNote(ID1, NOTE))) {
                int i = idAndNoteList.indexOf(new IdAndNote(ID1, NOTE));
                note1 = idAndNoteList.get(i).note();
            }
            if (idAndNoteList.contains(new IdAndNote(ID2, NOTE))) {
                int i = idAndNoteList.indexOf(new IdAndNote(ID2, NOTE));
                note2 = idAndNoteList.get(i).note();
            }
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
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags, "[[" + ID1 + "]] " + NOTE1 + System.lineSeparator()
                + "[[" + ID2 + "]] " + NOTE2 + System.lineSeparator()
                + "[[" + ID3 + "]] " + NOTE3);

        Map<NoteRegex, List<IdAndNote>> expectedTags = new HashMap<>();
        expectedTags.computeIfAbsent(NoteRegex.BARN, k -> new ArrayList<>()).add(new IdAndNote(ID1, NOTE1));
        expectedTags.get(NoteRegex.BARN).add(new IdAndNote(ID2, NOTE2));
        expectedTags.computeIfAbsent(NoteRegex.PEN, k -> new ArrayList<>()).add(new IdAndNote(ID3, NOTE3));

        Assertions.assertThat(tags).isEqualTo(expectedTags);
    }

    @Test
    @DisplayName("다양한 값들을 동시에 저장하면 각자의 반환값을 가진다.")
    void test5() {
        final String ID0 = "0번축사";
        final String ID1 = "1번축사";
        final String ID2 = "2번축사";
        final String ID3 = "3번축사";
        final String ID4 = "3-1";
        final String ID5 = "1111";
        final String ID6 = "2222";
        final String ID7 = "4444";
        final String NOTE1 = "몸살이 났다. 머리가 지끈거린다.";
        final String NOTE2 = "코로나였다. 죽다 살아났다.";
        final String NOTE3 = "5일동안 잠도 제대로 못자고 결국 병원에 가서 수액까지 맞고 괜찮아졌다.";
        final String NOTE4 = "다시 재미있는 코딩을 시작해보자";
        final String NOTE5 = "목은 아직 간지럽지만 말이다.";
        NoteParser noteParser = new NoteParser();
        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        tags = noteParser.extractAndSaveNote(tags,
                "[[" + ID1 + "," + ID2 + "," + ID0 + "]] " + NOTE1 + System.lineSeparator()
                        + "[[" + ID2 + ","+ID3+"]] " + NOTE2 + System.lineSeparator()
                        + "[[" + ID4 + "]] " + NOTE3 + System.lineSeparator()
                        + "[["+ID5+"]] "+NOTE4 + System.lineSeparator()
                        + "[["+ID6+","+ID7+"]] "+NOTE5);

        Map<NoteRegex, List<IdAndNote>> expectedTags = new HashMap<>();
        expectedTags.computeIfAbsent(NoteRegex.BARN, k -> new ArrayList<>()).add(new IdAndNote(ID1, NOTE1));
        expectedTags.get(NoteRegex.BARN).add(new IdAndNote(ID2, NOTE1));
        expectedTags.get(NoteRegex.BARN).add(new IdAndNote(ID0, NOTE1));
        expectedTags.get(NoteRegex.BARN).add(new IdAndNote(ID2, NOTE2));
        expectedTags.get(NoteRegex.BARN).add(new IdAndNote(ID3, NOTE2));
        expectedTags.computeIfAbsent(NoteRegex.PEN, k -> new ArrayList<>()).add(new IdAndNote(ID4, NOTE3));
        expectedTags.computeIfAbsent(NoteRegex.COW, k -> new ArrayList<>()).add(new IdAndNote(ID5, NOTE4));
        expectedTags.get(NoteRegex.COW).add(new IdAndNote(ID6, NOTE5));
        expectedTags.get(NoteRegex.COW).add(new IdAndNote(ID7, NOTE5));

        Assertions.assertThat(tags).isEqualTo(expectedTags);
    }
}