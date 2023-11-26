package com.psr.psr.chat.controller

import com.psr.psr.chat.service.ChatService
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
@Tag(name = "Chat", description = "채팅 API")
@SecurityRequirement(name = "Bearer")
class ChatController(
    private val chatService: ChatService
) {

    /**
     * 채팅방 등록
     */
    @Operation(summary = "채팅방 등록(박소정)", description = "채팅방을 등록한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PostMapping("/rooms/{orderId}")
    fun createChatRoom(@AuthenticationPrincipal userAccount: UserAccount,
                       @Parameter(description = "(Long) 요청 id", example = "1") @PathVariable orderId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(chatService.createChatRoom(userAccount.getUser(), orderId))
    }

    /**
     * 채팅방 나가기
     */
    @Operation(summary = "채팅방 나가기(박소정)", description = "채팅방을 나간다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "해당 채팅방을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PatchMapping("/rooms/{chatRoomId}")
    fun leaveChatRoom(@AuthenticationPrincipal userAccount: UserAccount,
                      @Parameter(description = "(Long) 채팅방 id", example = "1") @PathVariable chatRoomId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(chatService.leaveChatRoom(userAccount.getUser(), chatRoomId));
    }


}