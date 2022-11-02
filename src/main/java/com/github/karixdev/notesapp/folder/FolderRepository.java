package com.github.karixdev.notesapp.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("""
            SELECT folder
            FROM Folder folder
            WHERE folder.name = :name
            """)
    Optional<Folder> findFolderByName(@Param("name") String name);

}
