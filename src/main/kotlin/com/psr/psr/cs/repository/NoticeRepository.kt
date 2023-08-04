package com.psr.psr.cs.repository

import com.psr.psr.cs.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository: JpaRepository<Notice, Long> {
    fun findByOrderByCreatedAtDesc() : List<Notice>?
}