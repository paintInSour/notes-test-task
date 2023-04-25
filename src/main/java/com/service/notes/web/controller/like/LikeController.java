package com.service.notes.web.controller.like;

import com.service.notes.service.like.LikeService;
import com.service.notes.util.AuthUtil;
import com.service.notes.web.dto.note.NoteDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RequestMapping("/like")
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/note/{noteId}")
    public NoteDto setLike(@PathVariable @NotBlank String noteId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Integer userId = AuthUtil.getUserIdFromAuthHeader(authHeader);
        return likeService.setLike(noteId, userId);
    }
}
