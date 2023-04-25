package com.service.notes.web.controller.note;

import com.service.notes.db.entity.note.Note;
import com.service.notes.service.note.NoteService;
import com.service.notes.util.AuthUtil;
import com.service.notes.web.dto.note.NoteDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/note")
@RestController
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/{id}")
    public NoteDto getById(@PathVariable @NotBlank String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String auth) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(auth);
        Note note = noteService.findByIdAndUserId(id, userId);
        return new NoteDto(note);
    }

    @GetMapping("/all")
    public Page<NoteDto> getAll(@RequestParam(defaultValue = "${pagination.default.page}") Integer page,
                                @RequestParam(defaultValue = "${pagination.default.size}") Integer size) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Note> notesPage = noteService.findAll(pageRequest);
        List<NoteDto> noteDtoList = notesPage.getContent().stream()
                .map(NoteDto::new).toList();
        return new PageImpl<>(noteDtoList, pageRequest, notesPage.getTotalElements());
    }

    @GetMapping("/user/all")
    public Page<NoteDto> getAllByUserId(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String auth,
                                        @RequestParam(defaultValue = "${pagination.default.page}") Integer page,
                                        @RequestParam(defaultValue = "${pagination.default.size}") Integer size) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(auth);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Note> notesPage = noteService.findAllByUserId(userId, pageRequest);
        List<NoteDto> noteDtoList = notesPage.getContent().stream()
                .map(NoteDto::new).toList();
        return new PageImpl<>(noteDtoList, pageRequest, notesPage.getTotalElements());
    }

    @GetMapping("/{sessionId}/all")
    public Page<NoteDto> getAllByUnauthorizedSession(@PathVariable String sessionId,
                                                     @RequestParam(defaultValue = "${pagination.default.page}") Integer page,
                                                     @RequestParam(defaultValue = "${pagination.default.size}") Integer size) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Note> notesPage = noteService.findAllBySessionId(sessionId, pageRequest);
        List<NoteDto> noteDtoList = notesPage.getContent().stream()
                .map(NoteDto::new).toList();
        return new PageImpl<>(noteDtoList, pageRequest, notesPage.getTotalElements());
    }

    @PostMapping
    public NoteDto create(@RequestBody NoteDto noteDto, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String auth) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(auth);
        Note note = new Note(noteDto, userId);
        note = noteService.save(note);
        return new NoteDto(note);
    }

    // sessionId is special token to allow unauthorized users create notes and save all created notes after authorized.
    @PostMapping("/{sessionId}")
    public NoteDto createForUnauthorized(@RequestBody NoteDto noteDto, @PathVariable String sessionId) {
        Note note = new Note(noteDto, sessionId);
        note = noteService.save(note);
        return new NoteDto(note);
    }

    @PutMapping("/{id}")
    public NoteDto update(@RequestBody NoteDto noteDto, @PathVariable String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String auth) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(auth);
        Note note = noteService.updateNoteContent(noteDto, id, userId);
        return new NoteDto(note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotBlank String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String auth) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(auth);
        noteService.delete(id, userId);
    }
}
