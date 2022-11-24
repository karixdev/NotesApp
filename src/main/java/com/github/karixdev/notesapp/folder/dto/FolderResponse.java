package com.github.karixdev.notesapp.folder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.note.Note;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
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

    public FolderResponse(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.notes = folder.getNotes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderResponse that = (FolderResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notes);
    }
}
