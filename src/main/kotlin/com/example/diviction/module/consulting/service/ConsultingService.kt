package com.example.diviction.module.consulting.service

import com.example.diviction.module.account.repository.MatchRepository
import com.example.diviction.module.account.repository.MemberRepository
import com.example.diviction.module.consulting.dto.ConsultRequestDto
import com.example.diviction.module.consulting.dto.ConsultResponseDto
import com.example.diviction.module.consulting.dto.ConsultUpdateDto
import com.example.diviction.module.consulting.entity.Consulting
import com.example.diviction.module.consulting.repository.ConsultingRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConsultingService(
    private val consultingRepository: ConsultingRepository,
    private val matchRepository: MatchRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun saveConsultingLog(consultingRequestDto: ConsultRequestDto)
    {
        val matching = matchRepository.findById(consultingRequestDto.matchingId).get()

        val consultLog = Consulting(
            matching,consultingRequestDto.content,consultingRequestDto.date
        )

        consultingRepository.save(consultLog)

    }

    @Transactional
    fun getConsultingLogByPatientId(patient_id : Long) : List<ConsultResponseDto>
    {
        var member = memberRepository.getById(patient_id)
        val match = matchRepository.findByPatient(member!!).get()

        var page = consultingRepository.findAllByMatchingOrderByDateDesc(match, Pageable.ofSize(10))

        var list = page?.content ?: null

        var result = ArrayList<ConsultResponseDto>()

        if(list==null)
        {
            return result
        }

        list.stream().forEach {
            result.add(it.toResponseDto())
        }

        return result
    }
    @Transactional
    fun getConsultingLogByPatientEmail(patient_email : String) : List<ConsultResponseDto>
    {
        val member = memberRepository.getByEmail(patient_email)
        val match = matchRepository.findByPatient(member!!).get()

        var page = consultingRepository.findAllByMatchingOrderByDateDesc(match, Pageable.ofSize(10))

        var list = page?.content ?: null

        var result = ArrayList<ConsultResponseDto>()

        if(list==null)
        {
            return result
        }

        list.stream().forEach {
            result.add(it.toResponseDto())
        }

        return result
    }

    @Transactional
    fun updateConsultLog(id: Long, consultUpdateDto: ConsultUpdateDto)
    {
        var consultLog = consultingRepository.getById(id)

        consultLog.content = consultUpdateDto.content
        consultLog.date = consultUpdateDto.date

        consultingRepository.save(consultLog)
    }

    fun Consulting.toResponseDto() = ConsultResponseDto(
        matching.id!!,content,date
    )

}
