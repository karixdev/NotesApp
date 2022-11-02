package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.note.Note;
import lombok.*;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Folder")
@Table(name = "folder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder {
    @Id
    @SequenceGenerator(
            name = "folder_sequence",
            sequenceName = "folder_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "folder_sequence"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @OneToMany(
            mappedBy = "folder",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Note> notes = new LinkedHashSet<>();

    public void addNote(Note note) {
        if (!this.notes.contains(note)) {
            this.notes.add(note);
            note.setFolder(this);
        }
    }

    public void removeNote(Note note) {
        if (this.notes.contains(note)) {
            this.notes.remove(note);
            note.setFolder(null);
        }
    }
}
