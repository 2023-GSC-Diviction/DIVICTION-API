package com.example.diviction.module.account.dto

data class CounselorDetailInfoUpdateRequestDto(
    var id: Long,

    var introduce : String?,

    var representative_service : String?,

    var activity_area : String?,

    var contact_hours : String?,

    var contact : String?
)
