package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.folder.Folder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NoteRepositoryTest {

    @Autowired
    NoteRepository underTest;

    @Autowired
    TestEntityManager entityManager;

    List<Note> notes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Folder folder = Folder.builder()
                .name("Folder")
                .build();

        Note note1 = Note.builder()
                .title("Title1")
                .content("Content1")
                .noteColor(NoteColor.GREEN)
                .folder(folder)
                .build();

        Note note2 = Note.builder()
                .title("Title2")
                .content("Content2")
                .noteColor(NoteColor.GREEN)
                .folder(folder)
                .build();

        notes.add(note1);
        notes.add(note2);

        entityManager.persist(folder);
        entityManager.persist(note1);
        entityManager.persist(note2);

        entityManager.flush();
    }

    @Test
    void GivenPageable_WhenFindAllNotes_ThenReturnsCorrectObject() {
        // Given
        PageRequest request = PageRequest.of(0,2);

        // When
        Page<Note> result = underTest.findAllNotes(request);

        // Then
        Page<Note> expected = new PageImpl<>(notes);

        assertEquals(expected.getTotalPages(), result.getTotalPages());
        assertEquals(expected.getTotalElements(), result.getTotalElements());
        assertEquals(expected.getContent(), result.getContent());
    }

}
