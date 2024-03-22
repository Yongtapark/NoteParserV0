package com.example.demo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.IdAndNote;
import com.example.demo.domain.dtos.NoteParserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(NoteParserController.class)
class NoteParserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteParserService noteParserService;
    @Test
    @DisplayName("api 응답을 검증한다.")
    void test1() throws Exception {
        final String ID = "1번축사";
        final String NOTE = "1번축사 노트내용";
        String requestBody = "{\"content\":\"[["+ID+"]] "+NOTE+"\"}";

        Map<NoteRegex, List<IdAndNote>> tags = new HashMap<>();
        List<IdAndNote> idAndNoteList = tags.computeIfAbsent(NoteRegex.BARN, k -> new ArrayList<>());
        idAndNoteList.add(new IdAndNote(ID,NOTE));
        tags.put(NoteRegex.BARN,idAndNoteList);
        NoteParserDto expectDto = new NoteParserDto(tags);

        given(noteParserService.saveContentUseNoteParser(anyString())).willReturn(expectDto);

        MvcResult result = mockMvc.perform(post("/api/note-parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        NoteParserDto noteParserDto = objectMapper.readValue(responseBody, NoteParserDto.class);
        Assertions.assertThat(noteParserDto.tags()).isEqualTo(expectDto.tags());

    }
}
