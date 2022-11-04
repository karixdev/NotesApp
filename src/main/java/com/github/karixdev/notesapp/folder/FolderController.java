package com.github.karixdev.notesapp.folder;

import com.github.karixdev.notesapp.folder.dto.FolderResponse;
import com.github.karixdev.notesapp.folder.dto.FolderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/collection")
    public Page<FolderResponse> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return folderService.getAll(page, size);
    }

    @GetMapping
    public FolderResponse getById(@RequestParam(name = "id") Long id) {
        return folderService.getById(id);
    }

    @PostMapping
    public ResponseEntity<FolderResponse> create(
            @RequestBody @Valid FolderRequest newFolderRequest
    ) {
        return new ResponseEntity<>(
                folderService.create(newFolderRequest),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<FolderResponse> update(
            @RequestParam(name = "id") Long id,
            @RequestBody @Valid FolderRequest folderRequest
    ) {
        return new ResponseEntity<>(
                folderService.update(id, folderRequest),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public Map<String, Boolean> delete(@RequestParam(name = "id") Long id) {
        return folderService.delete(id);
    }
}
