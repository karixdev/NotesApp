package com.github.karixdev.notesapp.folder.dto;

import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.utils.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class FolderRequestMapper implements DtoMapper<FolderRequest, Folder> {
    @Override
    public FolderRequest toDto(Folder entity) {
        return new FolderRequest(entity.getName());
    }

    @Override
    public Folder toEntity(FolderRequest dto) {
        return Folder.builder()
                .name(dto.getName())
                .build();
    }
}
