package com.example.demo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.dto.NoteParserDto;
import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.NoteAndId;
import com.example.demo.domain.note_parser.NoteAndIdList;
import com.example.demo.domain.note_parser.NoteContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteParserController.class)
class NoteParserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    NoteParserService noteParserService;


    @Test
    @DisplayName("문자열을 입력하면 NoteParserDto 형태로 응답한다")
    void testReturnNoteParserDto() throws Exception {
        String context = "[[1번축사]] 몸살이 났다. 머리가 지끈거린다.";
        final String ID = "1번축사";
        final String NOTE = "몸살이 났다. 머리가 지끈거린다.";
        NoteAndIdList noteAndIdList = new NoteAndIdList();
        noteAndIdList.add(new NoteAndId("1번축사", "몸살이 났다. 머리가 지끈거린다."));
        NoteContainer noteContainer = new NoteContainer();
        noteContainer.putNotes(NoteRegex.BARN, noteAndIdList);
        NoteParserDto expectedDto = new NoteParserDto(noteContainer);

        given(noteParserService.saveContentUseNoteParser(anyString())).willReturn(expectedDto);
        String responseDto = mockMvc.perform(post("/api/note-parser/parse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"[[1번축사]] 몸살이 났다. 머리가 지끈거린다.\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        NoteParserDto responseNoteParserDto = objectMapper.readValue(responseDto, NoteParserDto.class);


        Assertions.assertThat(expectedDto.getNoteContainerMap().get(NoteRegex.BARN).getImmutableList())
                .isEqualTo(responseNoteParserDto.getNoteContainerMap().get(NoteRegex.BARN).getImmutableList());

    }
}