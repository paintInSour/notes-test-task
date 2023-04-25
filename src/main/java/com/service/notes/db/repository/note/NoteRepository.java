package com.service.notes.db.repository.note;

import com.service.notes.db.entity.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    Optional<Note> findOneByIdAndUserId(String id, Integer userId);

    Page<Note> findAllByUserId(Integer userId, Pageable pageable);
    Page<Note> findAllBySessionId(String sessionId, Pageable pageable);

    void deleteByIdAndUserId(String id, Integer userId);

}
