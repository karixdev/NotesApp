package com.github.karixdev.notesapp.note.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import com.github.karixdev.notesapp.note.NoteColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("note_color")
    private NoteColor noteColor;

    @JsonProperty("folder")
    @JsonIgnoreProperties({"notes"})
    private FolderResponse folder;
}
