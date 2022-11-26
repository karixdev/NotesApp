package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.folder.FolderService;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import com.github.karixdev.notesapp.note.dto.NoteRequest;
import com.github.karixdev.notesapp.note.dto.NoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @InjectMocks
    NoteService underTest;

    @Mock
    NoteRepository noteRepository;

    @Mock
    FolderService folderService;

    Folder folder;
    Note note;
    NoteResponse noteResponse;

    @BeforeEach
    void setUp() {
        folder = Folder.builder()
                .id(1L)
                .name("Folder")
                .build();

        note = Note.builder()
                .id(1L)
                .title("Title")
                .content("content")
                .noteColor(NoteColor.GREEN)
                .folder(folder)
                .build();

        folder.setNotes(Set.of(note));

        noteResponse = new NoteResponse(note);
    }

    @Test
    void GivenInvalidPaginationDetails_WhenGetAll_ThenThrowsException() {
        // Given
        int page = 0;
        int size = 0;

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.getAll(page, size));
    }

    @Test
    void GivenValidPaginationDetails_WhenGetAll_ThenReturnsCorrectObject() {
        // Given
        int page = 0;
        int size = 1;

        List<Note> notes = List.of(note);

        when(noteRepository.findAllNotes(any(Pageable.class)))
                .thenReturn(new PageImpl<>(notes));

        when(folderService.mapFolderToFolderResponse(any(Folder.class)))
                .thenReturn(new FolderResponse(folder));

        // When
        Page<NoteResponse> result = underTest.getAll(page, size);

        // Then
        Page<NoteResponse> expected = new PageImpl<>(List.of(noteResponse));

        assertEquals(expected.getTotalPages(), result.getTotalPages());
        assertEquals(expected.getTotalElements(), result.getTotalElements());
        assertEquals(expected.getContent(), result.getContent());
    }

    @Test
    void GivenNotExistingNoteId_WhenGetById_ThenThrowsException() {
        // Given
        Long id = 10L;

        when(noteRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> underTest.getById(id));
    }

    @Test
    void GivenExistingPostId_WhenGetById_ThenReturnsCorrectObject() {
        // Given
        Long id = 1L;

        when(noteRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(note));

        when(folderService.mapFolderToFolderResponse(any(Folder.class)))
                .thenReturn(new FolderResponse(folder));

        // When
        NoteResponse result = underTest.getById(id);

        // Then
        assertEquals(noteResponse, result);
    }

    @Test
    void GivenNoteRequestWithNotExistingFolderId_WhenCreate_ThenThrowsException() {
        // Given
        NoteRequest request = new NoteRequest();
        request.setFolderId(10L);
        request.setNoteColor(NoteColor.GREEN);
        request.setTitle("Title");
        request.setContent("Content");

        when(folderService.getByIdOrThrowException(any(Long.class)))
                .thenThrow(new ResourceNotFoundException("Resource not found"));

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> underTest.create(request));
    }

    @Test
    void GivenValidNoteRequest_WhenCreated_ThenReturnsCorrectObject() {
        // Given
        NoteRequest request = new NoteRequest();
        request.setFolderId(note.getFolder().getId());
        request.setNoteColor(note.getNoteColor());
        request.setTitle(note.getTitle());
        request.setContent(note.getContent());

        when(folderService.getByIdOrThrowException(any(Long.class)))
                .thenReturn(folder);

        when(folderService.mapFolderToFolderResponse(any(Folder.class)))
                .thenReturn(new FolderResponse(folder));

        when(noteRepository.save(any(Note.class)))
                .thenReturn(note);

        // When
        NoteResponse result = underTest.create(request);

        // Then
        assertEquals(noteResponse, result);
    }

}
