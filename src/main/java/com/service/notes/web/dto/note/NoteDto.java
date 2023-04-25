package com.service.notes.web.dto.note;

import com.service.notes.db.entity.note.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private String id;
    private Integer userId;
    private String content;
    private Integer likeCount;
    private Date created;
    private Date modified;

    public NoteDto(Note post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.content = post.getContent();
        this.likeCount = post.getLikes().size();
        this.created = new Date(post.getCreatedDate());
        this.modified = new Date(post.getLastModifiedDate());
    }
}
