package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import com.github.karixdev.notesapp.folder.dto.FolderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderResponse create(FolderRequest folderRequest) {
        Folder folder = folderRepository.save(
                Folder.builder()
                        .name(folderRequest.getName())
                        .build());

        return mapFolderToFolderResponse(folder);
    }

    public List<FolderResponse> getAll() {
        return folderRepository.findAll()
                .stream()
                .map(this::mapFolderToFolderResponse)
                .toList();
    }

    public FolderResponse getById(Long id) {
        return mapFolderToFolderResponse(getByIdOrThrowException(id));
    }

    public FolderResponse update(Long id, FolderRequest folderRequest) {
        Folder folder = getByIdOrThrowException(id);
        folder.setName(folderRequest.getName());

        return mapFolderToFolderResponse(folderRepository.save(folder));
    }

    public Map<String, Boolean> delete(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Folder with provided id does not exist");
                });

        folderRepository.delete(folder);

        return Map.of("success", Boolean.TRUE);
    }

    public Folder getByIdOrThrowException(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Folder with provided id does not exist");
                });
    }

    public FolderResponse mapFolderToFolderResponse(Folder folder) {
        return FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .notes(folder.getNotes())
                .build();
    }
}
