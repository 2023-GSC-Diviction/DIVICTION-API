package com.example.diviction.module.consulting.entity

import com.example.diviction.module.account.entity.Matching
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Getter
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Getter
@Table(name ="consulting")
class Consulting(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id", nullable = false)
    val matching : Matching,

    @field: NotBlank
    var content : String,

    @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var date : LocalDate
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

}
