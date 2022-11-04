package com.github.karixdev.notesapp.note;

import com.github.karixdev.notesapp.exception.ResourceNotFoundException;
import com.github.karixdev.notesapp.folder.Folder;
import com.github.karixdev.notesapp.folder.FolderService;
import com.github.karixdev.notesapp.note.dto.NoteRequest;
import com.github.karixdev.notesapp.note.dto.NoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderService folderService;

    public Page<NoteResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Note> notes = noteRepository.findAll(pageRequest);

        return notes.map(this::mapNoteToNoteResponse);
    }

    public NoteResponse getById(Long id) {
        return mapNoteToNoteResponse(getByIdOrThrowException(id));
    }

    public NoteResponse create(NoteRequest noteRequest) {
        Folder folder = folderService.getByIdOrThrowException(noteRequest.getFolderId());
        Note note = noteRepository.save(
                Note.builder()
                        .title(noteRequest.getTitle())
                        .content(noteRequest.getContent())
                        .noteColor(noteRequest.getNoteColor())
                        .folder(folder)
                        .build()
        );

        return mapNoteToNoteResponse(note);
    }

    public Map<String, Boolean> delete(Long id) {
        Note note = getByIdOrThrowException(id);
        noteRepository.delete(note);

        return Map.of("success", Boolean.TRUE);
    }

    public NoteResponse update(Long id, NoteRequest noteRequest) {
        Note note = getByIdOrThrowException(id);
        Folder folder = folderService.getByIdOrThrowException(noteRequest.getFolderId());

        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setNoteColor(noteRequest.getNoteColor());
        note.setFolder(folder);

        noteRepository.save(note);

        return mapNoteToNoteResponse(note);
    }

    public Note getByIdOrThrowException(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Note with provided id does not exist");
                });
    }

    public NoteResponse mapNoteToNoteResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .noteColor(note.getNoteColor())
                .folder(folderService.mapFolderToFolderResponse(note.getFolder()))
                .build();
    }
}
