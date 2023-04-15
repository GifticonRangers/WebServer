package com.capstone.webserver.error;

public enum ErrorCode {
//    Unknown(0),
//
//    Continue(100),
//    SwitchingProtocols(101),
//    Processing(102),
//    EarlyHints(103),
//    Created(201),
//    Accepted(202),
//    NonAuthoritative(203),
//    NoContent(204),
//    ResetContent(205),
//    PartialContent(206),
//    MultiStatus(207),
//    AlreadyReported(208),
//    IMUsed(209),
//    MultipleChoices(300),
//    MovePermanently(301),
//    Found(302),
//    SeeOther(303),
//    NotModified(304),
//    UseProxy(305),
//    SwitchProxy(306),
//    TemporaryRedirect(307),
//    PermanentRedirect(308),
//    PaymentRequired(402),
//    MethodNotAllowed(405),
//    NotAcceptable(406),
//    ProxyAuthenticationRequired(407),
//    RequestTimeout(408),
//    Conflict(409),
//    Gone(410),
//    LengthRequired(411),
//    PreconditionFailed(412),
//    PayloadTooLarge(413),
//    URITooLong(414),
//    UnsupportedMediaType(415),
//    RangeNotSatisfiable(416),
//    ExpectationFailed(417),
//    IMATeapot(418),
//    MisdirectedRequest(421),
//    UnProcessableEntity(422),
//    Locked(423),
//    FailedDependency(424),
//    TooEarly(425),
//    UpgradeRequired(426),
//    PreconditionRequired(428),
//    TooManyRequests(429),
//    RequestHeaderFieldsTooLarge(431),
//    UnavailableForLegalReasons(451),
//    NotImplemented(501),
//    BadGateway(502),
//    ServiceUnavailable(503),
//    GatewayTimeout(504),
//    HTTPVersionNotSupported(505),
//    NotExtended(510),
//    NetworkAuthenticationRequired(511);

    OK(200, "OK", "성공"),

    BadRequest(400, "BadRequest", "잘못된 요청"),
    Unauthorized(401, "Unauthorized", "인증 필요"),
    Forbidden(403, "Forbidden", "권한 없음"),
    NotFound(404, "NotFound", "리소스를 찾을 수 없음"),

    InternalServerError(500, "InternalServerError", "서버 에러");

    
    private final int status;           // Header로 반환할 HTTP Status Code
    private final String code;          // Payload로 반환할 에러 코드
    private final String msg;   // 에러 코드 메시지

    ErrorCode(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}