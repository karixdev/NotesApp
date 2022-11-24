package com.github.karixdev.notesapp.folder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FolderRepositoryTest {

    @Autowired
    private FolderRepository underTest;

    @Autowired
    TestEntityManager entityManager;

    Folder folder;

    @BeforeEach
    void setUp() {
        folder = Folder.builder()
                .name("Folder")
                .build();

        entityManager.persistAndFlush(folder);
    }

    @Test
    void WhenGivenExistingFolderName_WhenFindFolderByName_ThenReturnsCorrectFolder() {
        // Given
        String name = "Folder";

        // When
        Optional<Folder> result = underTest.findFolderByName(name);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void WhenGivenNonExistingFolderName_WhenFindFolderByName_ThenReturnsCorrectFolder() {
        // Given
        String name = "redloF";

        // When
        Optional<Folder> result = underTest.findFolderByName(name);

        // Then
        assertFalse(result.isPresent());
    }

}
