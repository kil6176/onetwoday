package com.sparta.onetwoday.service;


import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.TravelResponseDto;
import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.jwt.JwtUtil;
import com.sparta.onetwoday.repository.CommentRepository;
import com.sparta.onetwoday.repository.TravelRepository;
import com.sparta.onetwoday.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.onetwoday.entity.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;


    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long travelId, CommentRequestDto commentRequestDto, User user) {

            Travel travel = travelRepository.findById(travelId).orElseThrow(
                    () -> new IllegalArgumentException(BOARD_DOES_NOT_EXIEST.getMessage())
            );

            Comment comment = commentRepository.save(new Comment(commentRequestDto, travel, user));

            return new CommentResponseDto(comment);
        }
    //댓글 삭제
    public List<CommentResponseDto> deleteComment(Long travelId, Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException(COMMENT_DOES_NOT_EXIEST.getMessage())
        );
        if (hasAuthority(user, comment)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException(ILLEGAL_ACCESS_UPDATE_OR_DELETE.getMessage());
        }

        return getCommentList(travelId);
    }

    //댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentList(Long travelId) {
        List<Comment> comments = commentRepository.findByTravelIdOrderByCreatedAtDesc(travelId);
        List<CommentResponseDto> responseDtos = new ArrayList<>();

        if(!comments.isEmpty()) {
            for(Comment comment : comments) {
                responseDtos.add(new CommentResponseDto(comment));
            }
        }

        return responseDtos;
    }
    //권한 확인하기
    public boolean hasAuthority(User user, Comment comment) {
        return user.getId().equals(comment.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }
}