package com.github.karixdev.notesapp.folder.dto;

import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.utils.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class FolderResponseMapper implements DtoMapper<FolderResponse, Folder> {
    @Override
    public FolderResponse toDto(Folder entity) {
        return FolderResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .notes(entity.getNotes())
                .build();
    }

    @Override
    public Folder toEntity(FolderResponse dto) {
        return Folder.builder()
                .id(dto.getId())
                .name(dto.getName())
                .notes(dto.getNotes())
                .build();
    }
}
