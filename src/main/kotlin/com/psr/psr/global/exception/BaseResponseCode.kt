package com.psr.psr.global.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(status: HttpStatus, message: String) {
    SUCCESS(HttpStatus.OK, "요청에 성공했습니다."),

    // token
    NULL_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 값을 입력해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 값입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰 값입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 구조의 토큰 값입니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰 값입니다."),
    BLACKLIST_TOKEN(HttpStatus.FORBIDDEN, "로그아웃 혹은 회원 탈퇴된 유저의 토큰 값입니다."),

    // user
    EXISTS_PHONE(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 휴대폰 번호입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 이메일입니다."),
    EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 닉네임입니다."),
    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일로 가입한 사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "사용자의 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_EID(HttpStatus.NOT_FOUND, "사업자를 찾을 수 없습니다."),
    NOT_EMPTY_EID(HttpStatus.BAD_REQUEST, "사업자 정보를 입력해주세요. "),
    INVALID_EID(HttpStatus.BAD_REQUEST, "정상 사업자가 아닙니다. (휴업자 or 폐업자)"),

    // User - type
    INVALID_USER_TYPE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않은 사용자 역할입니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    INVALID_USER_CATEGORY(HttpStatus.BAD_REQUEST, "올바르지 않은 사용자 카테고리입니다."),

    // User - category
    INVALID_USER_INTEREST_COUNT(HttpStatus.BAD_REQUEST, "사용자 관심 주제는 1개이상, 3개 이하여야하며, 중복된 값이 포함되어 있지 않아야 합니다"),

    // CS - notices
    NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND, "해당 공지사항를 찾을 수 없습니다."),

    // inquiry
    NOT_FOUND_INQUIRY(HttpStatus.NOT_FOUND, "해당 문의를 찾을 수 없습니다."),
    INVALID_INQUIRY_STATUS(HttpStatus.BAD_REQUEST, "올바르지 않은 문의 상태입니다."),
    INQUIRY_ANSWER_ALREADY_COMPLETE(HttpStatus.CONFLICT, "이미 답변 완료된 문의입니다.");

    val status: HttpStatus = status
    val message: String = message
}