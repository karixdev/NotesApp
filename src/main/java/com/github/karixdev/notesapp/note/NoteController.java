package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.note.dto.NoteRequest;
import com.github.karixdev.notesapp.note.dto.NoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/collection")
    public Page<NoteResponse> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return noteService.getAll(page, size);
    }

    @GetMapping
    public NoteResponse getById(@RequestParam(name = "id") Long id) {
        return noteService.getById(id);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> create(
            @RequestBody @Valid NoteRequest noteRequest
    ) {
        return new ResponseEntity<>(
                noteService.create(noteRequest),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping
    public Map<String, Boolean> delete(@RequestParam(name = "id") Long id) {
        return noteService.delete(id);
    }

    @PutMapping
    public NoteResponse update(
            @RequestParam(name = "id") Long id,
            @RequestBody @Valid NoteRequest noteRequest
    ) {
        return noteService.update(id, noteRequest);
    }
}
