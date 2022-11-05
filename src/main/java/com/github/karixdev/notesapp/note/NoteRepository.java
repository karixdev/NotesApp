package com.github.karixdev.notesapp.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = """
                SELECT note
                FROM Note note
                LEFT JOIN FETCH note.folder
            """,
            countQuery = """
                SELECT COUNT(note)
                FROM Note note
            """
    )
    Page<Note> findAllNotes(Pageable pageable);

}
