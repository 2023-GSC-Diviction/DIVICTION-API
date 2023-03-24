package com.example.diviction.module.consulting.repository

import com.example.diviction.module.account.entity.Matching
import com.example.diviction.module.consulting.entity.Consulting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConsultingRepository : JpaRepository<Consulting, Long> {

    fun findAllByMatchingOrderByDateDesc(matching: Matching, pageable: Pageable) : Page<Consulting>?
}
