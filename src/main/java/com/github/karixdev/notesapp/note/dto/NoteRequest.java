package com.github.karixdev.notesapp.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.notesapp.note.NoteColor;
import com.github.karixdev.notesapp.validation.ExistingFolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    @JsonProperty("title")
    @Size(min = 2, max = 255)
    private String title;

    @JsonProperty("content")
    @Size(min = 5)
    private String content;

    @JsonProperty("note_color")
    private NoteColor noteColor;

    @JsonProperty("folder_id")
    @ExistingFolder
    private Long folderId;
}
