package com.service.notes.service.like;

import com.service.notes.db.entity.note.Note;
import com.service.notes.exception.EntityNotFoundException;
import com.service.notes.exception.ServerProcessingException;
import com.service.notes.service.note.NoteService;
import com.service.notes.web.dto.note.NoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {
    private final NoteService noteService;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(65))
    public NoteDto setLike(String noteId, Integer userId) {
        try {
            reentrantLock.lock();
            Note note = noteService.findByIdAndUserId(noteId, userId);
            Set<Integer> likes = note.getLikes();

            if (!likes.contains(userId)) {
                likes.add(userId);
            } else {
                likes.remove(userId);
            }

            note = noteService.save(note);
            return new NoteDto(note);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerProcessingException(String.format(
                    "Error on change like state on note: %s for userId: %s", noteId, userId
            ));
        } finally {
            reentrantLock.unlock();
        }
    }

}
