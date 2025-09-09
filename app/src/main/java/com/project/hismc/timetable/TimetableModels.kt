package com.project.hismc.timetable

data class TimetableResponse(
    val hisTimetable: List<TimetableSection>?
)

data class TimetableSection(
    val head: List<Any>?,        // 헤드는 필요 없으면 Any로 둬도 됨
    val row: List<TimetableRow>? // 실제 시간표 데이터
)

data class TimetableRow(
    val ATPT_OFCDC_SC_CODE: String?,
    val SD_SCHUL_CODE: String?,
    val SCHUL_NM: String?,
    val AY: String?,
    val SEM: String?,
    val ALL_TI_YMD: String?,      // 날짜
    val DGHT_CRSE_SC_NM: String?,
    val ORD_SC_NM: String?,
    val DDDEP_NM: String?,
    val GRADE: String?,
    val CLRM_NM: String?,
    val CLASS_NM: String?,
    val PERIO: String?,           // 교시
    val ITRT_CNTNT: String?,      // 과목명
    val LOAD_DTM: String?
)
