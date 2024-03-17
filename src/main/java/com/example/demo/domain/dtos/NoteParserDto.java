package com.example.demo.domain.dtos;

import com.example.demo.domain.enums.NoteRegex;
import com.example.demo.domain.note_parser.IdAndNote;
import java.util.List;
import java.util.Map;

public record NoteParserDto(Map<NoteRegex, List<IdAndNote>> tags) {
}
