package com.psr.psr.cs.dto.response

import com.psr.psr.cs.dto.response.NoticeRes.Companion.toNoticeResHome
import com.psr.psr.cs.entity.Notice
import io.swagger.v3.oas.annotations.media.Schema
import java.util.stream.Collectors

data class NoticeListRes (
    @Schema(description = "공지사항 리스트")
    val noticeLists: List<NoticeRes>?
){
    companion object{
        fun toNoticeListRes(noticeList: List<Notice>?): NoticeListRes {
            if (noticeList!!.isEmpty()) return NoticeListRes(null)
            return NoticeListRes(noticeList.stream().map {
                    n -> NoticeRes(n.id, n.title, n.createdAt)
            }.collect(Collectors.toList()))
        }

        fun toNoticeListResForHomePage(noticeList: List<Notice>?): NoticeListRes {
            return NoticeListRes(
                noticeLists = noticeList?.map { n -> toNoticeResHome(n) }!!.toList()
            )
        }
    }
}