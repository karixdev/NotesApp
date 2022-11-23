package com.github.karixdev.notesapp.validation;

import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.folder.FolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistingFolderValidatorTest {

    @InjectMocks
    ExistingFolderValidator underTest;

    @Mock
    FolderRepository folderRepository;

    Folder folder;

    @BeforeEach
    void setUp() {
        folder = Folder.builder()
                .id(1L)
                .name("Folder")
                .build();
    }

    @Test
    void GivenExistingFolderId_WhenIsValid_ThenReturnsTrue() {
        // Given
        Long id = 1L;

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(folder));

        // When
        boolean result = underTest.isValid(id, null);

        // Then
        assertTrue(result);
    }

    @Test
    void GivenNotExistingFolderId_WhenIsValid_ThenReturnsFalse() {
        // Given
        Long id = 2L;

        when(folderRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        // When
        boolean result = underTest.isValid(id, null);

        // Then
        assertFalse(result);
    }

}
