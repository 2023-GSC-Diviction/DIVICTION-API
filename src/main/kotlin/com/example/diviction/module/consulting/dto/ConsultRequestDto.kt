package com.example.diviction.module.consulting.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ConsultRequestDto(
    val matchingId : Long,
    val content : String,
    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date : LocalDate
)
