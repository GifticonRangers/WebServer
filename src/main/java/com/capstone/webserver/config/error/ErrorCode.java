package com.capstone.webserver.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    /*
    Unknown(0),

    Continue(100),
    SwitchingProtocols(101),
    Processing(102),
    EarlyHints(103),
    Created(201),
    Accepted(202),
    NonAuthoritative(203),
    NoContent(204),
    ResetContent(205),
    PartialContent(206),
    MultiStatus(207),
    AlreadyReported(208),
    IMUsed(209),
    MultipleChoices(300),
    MovePermanently(301),
    Found(302),
    SeeOther(303),
    NotModified(304),
    UseProxy(305),
    SwitchProxy(306),
    TemporaryRedirect(307),
    PermanentRedirect(308),
    PaymentRequired(402),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    URITooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    IMATeapot(418),
    MisdirectedRequest(421),
    UnProcessableEntity(422),
    Locked(423),
    FailedDependency(424),
    TooEarly(425),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HTTPVersionNotSupported(505),
    NotExtended(510),
    NetworkAuthenticationRequired(511);
     */

    /* 400 BAD_REQUEST */
    BadRequest(BAD_REQUEST, "잘못된 요청"),
    USER_NOT_FOUND(BAD_REQUEST, "일치하는 유저 정보 없음"),
    SUBJECT_NOT_FOUND(BAD_REQUEST, "일치하는 과목 정보 없음"),
    AUDITOR_NOT_FOUND(BAD_REQUEST, "일치하는 수강생 정보 없음"),
    ATTENDANCE_NOT_FOUND(BAD_REQUEST, "일치하는 출석 정보 없음"),

    /* 401 UNAUTHORIZED */
    TOKEN_AUTHENTICATION_FAIL(UNAUTHORIZED, "토큰 인증 실패"),
    TOKEN_REQUIRED_FAIL(UNAUTHORIZED,"토큰 요청 실패"),

    /* 403 FORBIDDEN */
    Forbidden(FORBIDDEN, "권한 없음"),

    /* 404 NOT_FOUND */
    NotFound(NOT_FOUND, "리소스를 찾을 수 없음"),

    /* 500 */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

}