package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.dto.FolderRequest;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
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
    void GivenFolderRequest_WhenCreate_ThenReturnsCorrectFolderResponse() {
        // Given
        FolderRequest request = new FolderRequest(folder.getName());

        when(folderRepository.save(any(Folder.class)))
                .thenReturn(folder);

        // When
        FolderResponse result = underTest.create(request);

        // Then
        assertEquals(folderResponse, result);
    }

    @Test
    void GivenInvalidPaginationData_WhenGetAll_ThenThrowsException() {
        // Given
        int page = 0;
        int size = 0;

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.getAll(page, size));
    }

    @Test
    void GivenCorrectPaginationData_WhenGetAll_ThenReturnsCorrectObject() {
        // Given
        int page = 0;
        int size = 1;

        List<Folder> folders = List.of(folder);
        when(folderRepository.findAllPosts(any(Pageable.class)))
                .thenReturn(new PageImpl<>(folders));

        List<FolderResponse> folderResponses = List.of(folderResponse);
        Page<FolderResponse> expected = new PageImpl<>(folderResponses);

        // When
        Page<FolderResponse> result = underTest.getAll(page, size);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void WhenGivenNonExistingFolderId_WhenUpdate_ThenThrowsException() {
        // Given
        Long id = 2L;
        FolderRequest request = new FolderRequest(folder.getName());

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> underTest.update(id, request));
    }

    @Test
    void GivenFolderRequestAndFolderId_WhenUpdate_ThenReturnsCorrectResponse() {
        // Given
        Long id = 1L;
        String newName = "New name";
        FolderRequest request = new FolderRequest(newName);

        Folder updatedFolder = Folder.builder()
                .id(folder.getId())
                .name(newName)
                .notes(folder.getNotes())
                .build();

        folderResponse.setName(newName);

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(folder));

        when(folderRepository.save(any(Folder.class)))
                .thenReturn(updatedFolder);

        // When
        FolderResponse result = underTest.update(id, request);

        // Then
        assertEquals(folderResponse, result);
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
