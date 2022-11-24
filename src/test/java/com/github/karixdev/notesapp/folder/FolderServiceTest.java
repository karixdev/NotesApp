package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FolderServiceTest {

    @InjectMocks
    FolderService underTest;

    @Mock
    FolderRepository folderRepository;

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
