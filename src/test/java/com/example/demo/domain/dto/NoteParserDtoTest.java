package com.example.demo.domain.dto;

import static com.example.demo.domain.enums.NoteRegex.BARN;
import static org.assertj.core.api.Assertions.*;

import com.example.demo.NoteParserService;
import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.NoteAndId;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteParserDtoTest {
    @Autowired
    private NoteParserService noteParserService;
    @Test
    @DisplayName("NoteContainer 의 불변성 체크")
    void test1_check_shallow_immunity() {
        final String ID = "1번축사";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        NoteContainer noteContainer = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);

        Map<NoteRegex, NoteAndIdList> immutableMap = noteContainer.getImmutableMap();

        assertThatThrownBy(()->immutableMap.put(BARN,new NoteAndIdList())).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("NoteAndIdList 불변성 체크")
    void test1_check_deep_immunity() {
        final String ID = "1번축사";
        final String TEMP_ID = "테스트 아이디";
        final String NOTE = "오늘 저녁도 카레 샐러드다. 내일은 뭘 먹지";
        final String TEMP_NOTE = "테스트 노트";
        NoteContainer noteContainer = noteParserService.saveContentUseNoteParser("[[" + ID + "]] " + NOTE);

        Map<NoteRegex, NoteAndIdList> immutableMap = noteContainer.getImmutableMap();
        NoteAndIdList noteAndIdList = immutableMap.get(BARN);
        List<NoteAndId> immutableList = noteAndIdList.getImmutableList();

        assertThatThrownBy(()->immutableList.add(new NoteAndId(TEMP_ID,TEMP_NOTE))).isInstanceOf(UnsupportedOperationException.class);
    }
}