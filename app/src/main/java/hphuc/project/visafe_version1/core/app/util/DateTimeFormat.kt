package hphuc.project.visafe_version1.core.app.util

enum class DateTimeFormat(val format: String) {
    DDMMYY_HHMM("dd/MM/yyyy HH:mm"),
    DDMMYY_HHMM_NEW("dd/MM/yyyy '-' HH:mm"),
    DDMMYY_HHMMSS("dd/MM/yyyy HH:mm:ss"),
    YYMMDD_HHMM("yyyy/MM/dd HH:mm"),
    HHMM_DDMMYY("HH:mm dd/MM/yyyy"),
    YYMMDD("yyyy/MM/dd"),
    DDMMYYYY("dd/MM/yyyy"),
    DDMM("dd/MM"),
    DATETIME_API("dd/MM/yyyy HH:mm:ss"),
    TIME_API("HH:mm:ss"),
    HHMM("HH:mm"),
    HHMMAA("hh:mm aa"),
    MMSS("mm:ss"),
    YYMMDD_HHMM_AA("dd/MM/yyyy hh:mm aa"),
    YYMMDD_HHMMSS("yyyy/MM/dd HH:mm:ss"),
    YY_MM_DD_HHMMSS("yyyy-MM-dd HH:mm:ss"),
    DATE_TIME_API_WAITER("yyyy/MM/dd HH:mm:ss"),
    DDMMYY("dd/MM/yy"),
    YYYY_MM_DD("yyyy-MM-dd"),
    DD_MM_YYYY("dd-MM-yyyy"),
    DDD_DD_MM("EEEE dd/MM"),
    TIMESTAMP_API1("yyyy-MM-dd'T'HH:mm:ss"),
    TIMESTAMP_API3("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    TIMESTAMP_API6("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
}