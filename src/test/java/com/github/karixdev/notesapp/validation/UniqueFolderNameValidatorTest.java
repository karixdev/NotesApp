package com.github.karixdev.notesapp.validation;

import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.folder.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueFolderNameValidatorTest {

    @InjectMocks
    UniqueFolderNameValidator underTest;

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
    void GivenNameOfAlreadyExistingPost_WhenIsValid_ThenReturnsFalse() {
        // Given
        String name = "Folder";

        when(folderRepository.findFolderByName(any(String.class)))
                .thenReturn(Optional.of(folder));

        // When
        boolean result = underTest.isValid(name, null);

        // Then
        assertFalse(result);
    }

    @Test
    void GivenNameOfNotExistingFolder_WhenIsValid_ThenReturnsTrue() {
        // Given
        String name = "redloF";

        when(folderRepository.findFolderByName(any(String.class)))
                .thenReturn(Optional.empty());

        // When
        boolean result = underTest.isValid(name, null);

        // Then
        assertTrue(result);
    }

}
