package com.example.yubbi.services.faq.service

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListOfOneResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import com.example.yubbi.services.faq.domain.Faq
import com.example.yubbi.services.faq.repository.FaqRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FaqServiceImpl(private val faqRepository: FaqRepository) : FaqService {

    @Transactional(readOnly = true)
    override fun getFaqList(page: Int, size: Int, word: String?): FaqListResponseDto {

        val pageRequest = PageRequest.of(page - 1, size, Sort.Direction.ASC, "faqId")

        val pageOfFaqs = if (word == null) {
            faqRepository.getPageOfFaqs(pageRequest)
        } else {
            faqRepository.getPageOfFaqsWithWord(pageRequest, word)
        }

        val faqListOfOneResponseDto = pageOfFaqs.map { faq ->
            FaqListOfOneResponseDto(
                faq.getFaqId()!!,
                faq.getQuestion()!!,
                faq.getAnswer()!!
            )
        }.toList()

        return FaqListResponseDto(pageOfFaqs.totalPages, page, faqListOfOneResponseDto)
    }

    override fun createFaq(adminFaqCreateRequestDto: AdminFaqCreateRequestDto, creator: Member): AdminFaqCreateResponseDto {

        val faq = Faq(
            adminFaqCreateRequestDto.question,
            adminFaqCreateRequestDto.answer,
            creator
        )

        val createdFaq = faqRepository.save(faq)

        return AdminFaqCreateResponseDto(createdFaq.getFaqId()!!)
    }
}
