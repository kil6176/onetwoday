package com.sparta.onetwoday.controller;


import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.Message;
import com.sparta.onetwoday.security.UserDetailsImpl;
import com.sparta.onetwoday.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel/{travelId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<Message> createComment(@PathVariable Long travelId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(travelId, commentRequestDto, userDetails.getUser());
    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Message> deleteComment(@PathVariable(name = "travelId") Long travelId, @PathVariable(name = "commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(travelId, commentId, userDetails.getUser());
    }
}
