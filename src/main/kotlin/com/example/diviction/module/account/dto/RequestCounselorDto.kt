package com.example.diviction.module.account.dto

import com.example.diviction.module.constant.Gender
import org.springframework.web.multipart.MultipartFile
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class RequestCounselorDto (
    var email : String,

    var password : String,

    var name : String,

    var birth : String,

    var address : String,

    @Enumerated(EnumType.STRING)
    var gender : Gender,

    var confirm : Boolean,

    var introduce : String,

    var representative_service : String,

    var activity_area : String,

    var contact_hours : String,

    var contact : String,

    var multipartFile: MultipartFile?
)
