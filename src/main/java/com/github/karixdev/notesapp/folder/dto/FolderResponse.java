package com.github.karixdev.notesapp.folder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.karixdev.notesapp.note.Note;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderResponse {
    private Long id;
    private String name;

    @JsonIgnoreProperties({
            "folder",
            "content",
            "noteColor"
    })
    @Builder.Default
    private Set<Note> notes = new LinkedHashSet<>();
}
