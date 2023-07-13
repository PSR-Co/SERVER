package com.psr.psr.cs.repository

import com.psr.psr.cs.entity.Faq
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FaqRepository: JpaRepository<Faq, Long> {
}