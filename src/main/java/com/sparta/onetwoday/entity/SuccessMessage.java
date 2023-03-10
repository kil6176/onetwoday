package com.sparta.onetwoday.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SIGN_UP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료 되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"로그인이 완료 되었습니다."),
    USER_INFO_SUCCESS(HttpStatus.OK, "유저정보 불러오기 성공"),
    BOARD_GET_SUCCESS(HttpStatus.OK,"게시물 랜덤 보기 완료"),
    BOARD_POST_SUCCESS(HttpStatus.CREATED, "게시물 작성 완료"),
    BOARD_PUT_SUCCESS(HttpStatus.CREATED,"게시물 수정 완료"),
    BOARD_DELETE_SUCCESS(HttpStatus.CREATED,"게시물 삭제 완료"),
    BOARD_MY_LIST_GET_SUCCESS(HttpStatus.OK,"나의 게시물 보기 완료"),
    BOARD_DETAIL_GET_SUCCESS(HttpStatus.OK,"상세 게시물 보기 완료"),
    COMMENT_POST_SUCCESS(HttpStatus.CREATED,"댓글 작성 완료"),
    COMMENT_DELETE_SUCCESS(HttpStatus.CREATED, "댓글 삭제 완료"),
    LIKE_POST_SUCCESS(HttpStatus.CREATED, "좋아요 등록 완료"),
    LIKE_DELETE_SUCCESS(HttpStatus.CREATED, "좋아요 취소 완료");

    private final HttpStatus httpStatus;
    private final String detail;
}
