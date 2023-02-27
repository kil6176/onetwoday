package com.sparta.onetwoday.controller;


import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.entity.Message;
import com.sparta.onetwoday.security.UserDetailsImpl;
import com.sparta.onetwoday.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/travel/{travelId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<Message> createComment(@PathVariable Long travelId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Message message = new Message(true,"댓글 작성 성공", commentService.createComment(travelId, commentRequestDto, userDetails.getUser()));
        return ResponseEntity.ok(message);

    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Message> deleteComment(@PathVariable(name = "travelId") Long travelId, @PathVariable(name = "commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, "댓글 삭제 성공", commentService.deleteComment(travelId, commentId, userDetails.getUser()));
        return ResponseEntity.ok(message);
    }
}
