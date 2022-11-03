package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.dto.FolderRequest;
import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import com.github.karixdev.notesapp.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final DtoMapper<FolderRequest, Folder> folderRequestMapper;
    private final DtoMapper<FolderResponse, Folder> folderResponseMapper;

    public FolderResponse create(FolderRequest folderRequest) {
        Folder folder = folderRepository.save(folderRequestMapper.toEntity(folderRequest));

        return folderResponseMapper.toDto(folder);
    }

    public List<FolderResponse> getAll() {
        return folderRepository.findAll()
                .stream()
                .map(folderResponseMapper::toDto)
                .toList();
    }

    public FolderResponse getById(Long id) {
        return folderResponseMapper.toDto(getByIdOrThrowException(id));
    }

    public FolderResponse update(Long id, FolderRequest folderRequest) {
        Folder folder = getByIdOrThrowException(id);
        folder.setName(folderRequest.getName());

        return folderResponseMapper.toDto(folderRepository.save(folder));
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
}
