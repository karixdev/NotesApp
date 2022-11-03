package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.note.dto.NoteRequest;
import com.github.karixdev.notesapp.note.dto.NoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/collection")
    public List<NoteResponse> getAll() {
        return noteService.getAll();
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
