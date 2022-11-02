package com.github.karixdev.notesapp.folder.dto;

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
    private Set<Note> notes;
}
