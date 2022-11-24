package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import org.junit.jupiter.api.BeforeEach;
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

    Folder folder;
    FolderResponse folderResponse;

    @BeforeEach
    void setUp() {
        folder = Folder.builder()
                .id(1L)
                .name("Folder")
                .build();

        folderResponse = FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .notes(folder.getNotes())
                .build();
    }

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

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(folder));

        // When
        FolderResponse result = underTest.getById(id);

        // Then
        assertEquals(folderResponse, result);
    }

    @Test
    void GivenFolderEntity_WhenMapToFolderResponse_ThenReturnsCorrectDto() {
        // When
        FolderResponse result = underTest.mapFolderToFolderResponse(folder);

        // Then
        assertEquals(folderResponse, result);
    }

}
