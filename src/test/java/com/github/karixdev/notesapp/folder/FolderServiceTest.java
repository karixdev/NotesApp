package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FolderServiceTest {

    @InjectMocks
    FolderService underTest;

    @Mock
    FolderRepository folderRepository;

    @Test
    void GivenNonExistingFolderId_WhenGetById_ThenThrowsException() {
        // Given
        Long id = 1L;

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> underTest.getById(id));
    }

    @Test
    void GivenExistingFolderId_WhenGetById_ThenReturnsFolderResponse() {
        // Given
        Long id = 1L;

        Folder folder = Folder.builder()
                .id(1L)
                .name("Folder")
                .build();

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(folder));

        // When
        FolderResponse result = underTest.getById(id);

        // Then
        FolderResponse expected = FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .notes(folder.getNotes())
                .build();

        assertEquals(expected, result);
    }

    @Test
    void GivenFolderEntity_WhenMapToFolderResponse_ThenReturnsCorrectDto() {
        // Given
        Folder folder = Folder.builder()
                .id(1L)
                .name("Folder")
                .build();

        // When
        FolderResponse result = underTest.mapFolderToFolderResponse(folder);

        // Then
        FolderResponse expected = FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .notes(folder.getNotes())
                .build();

        assertEquals(expected, result);
    }

}
