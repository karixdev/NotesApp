package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.folder.Folder;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Note")
@Table(name = "note")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {
    @Id
    @SequenceGenerator(
            name = "note_sequence",
            sequenceName = "note_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "note_sequence"
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "content",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "note_color",
            nullable = false
    )
    private NoteColor noteColor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "folder_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "folder_id_fk"
            )
    )
    private Folder folder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(title, note.title) && Objects.equals(content, note.content) && noteColor == note.noteColor && Objects.equals(folder, note.folder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, noteColor, folder);
    }
}
