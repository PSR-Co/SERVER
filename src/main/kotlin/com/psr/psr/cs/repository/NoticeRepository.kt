package com.psr.psr.cs.repository

import com.psr.psr.cs.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository: JpaRepository<Notice, Long> {
    fun findByStatusOrderByCreatedAtDesc(status: String) : List<Notice>?
    fun findByIdAndStatus(id: Long, status: String) : Notice?
    fun findTop3ByStatusOrderByCreatedAtDesc(status: String) : List<Notice>?
}