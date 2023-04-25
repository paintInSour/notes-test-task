package com.service.notes.service.note;

import com.service.notes.db.entity.note.Note;
import com.service.notes.db.repository.note.NoteRepository;
import com.service.notes.exception.EntityNotFoundException;
import com.service.notes.web.dto.note.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Clock;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final Clock utcClock;

    public Note findByIdAndUserId(String id, Integer userId) {
        return noteRepository.findOneByIdAndUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Not found note by id: %s and userId: %s", id, userId))
                );
    }

    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    public Page<Note> findAllByUserId(Integer userId, Pageable pageable) {
        return noteRepository.findAllByUserId(userId, pageable);
    }

    public Page<Note> findAllBySessionId(String sessionId, Pageable pageable) {
        return noteRepository.findAllBySessionId(sessionId, pageable);
    }

    public Note save(Note note) {
        note.setCreatedDate(utcClock.millis());
        note.setLastModifiedDate(utcClock.millis());
        return noteRepository.save(note);
    }

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(50))
    public Note updateNoteContent(NoteDto noteDto, String id, Integer userId) {
        Note note = this.findByIdAndUserId(id, userId);
        note.setContent(noteDto.getContent());
        note.setLastModifiedDate(utcClock.millis());
        return noteRepository.save(note);
    }

    public void delete(String id, Integer userId) {
        noteRepository.deleteByIdAndUserId(id, userId);
    }


}
