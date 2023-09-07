package com.psr.psr.cs.dto.response

import com.psr.psr.cs.dto.response.NoticeRes.Companion.toNoticeResHome
import com.psr.psr.cs.entity.Notice
import java.util.stream.Collectors

data class NoticeListRes (
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