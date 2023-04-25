package com.service.notes.db.entity.note;

import com.service.notes.web.dto.note.NoteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Document("note")
public class Note {
    @Id
    private String id;
    @Version
    private Long version;

    private Integer userId;
    private String sessionId;
    private String content;
    private Set<Integer> likes;
    private Long createdDate;
    private Long lastModifiedDate;

    public Note(NoteDto dto, Integer userId) {
        this.userId = userId;
        this.content = dto.getContent();
        this.likes = new HashSet<>();
    }

    public Note(NoteDto dto, String sessionId) {
        this.sessionId = sessionId;
        this.content = dto.getContent();
        this.likes = new HashSet<>();
    }
}
