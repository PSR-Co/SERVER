package com.psr.psr.order.controller

import com.psr.psr.global.Constant
import com.psr.psr.global.Constant.Order.Order.ORDER
import com.psr.psr.global.Constant.Order.Order.SELL
import com.psr.psr.global.Constant.Order.Order.STATUS
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.order.dto.OrderListRes
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.order.dto.OrderRes
import com.psr.psr.order.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "요청 API")
@SecurityRequirement(name = "Bearer")
class OrderController(
    private val orderService: OrderService
) {
    // 요청하기
    @Operation(summary = "요청하기(박서연)", description = "상품에 대한 요청을 한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "이름을 입력해주세요.<br>" +
                        "이름은 최대 100자입니다.<br>" +
                        "문의사항을 입력해주세요.<br>" +
                        "문의사항은 최대 250자입니다.<br>" +
                        "요청 상세설명을 입력해주세요.<br>" +
                        "요청 상세설명은 최대 250자입니다.<br>" +
                        "상품ID를 입력해주세요.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 상품을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PostMapping
    fun makeOrder(
        @AuthenticationPrincipal userAccount: UserAccount,
        @RequestBody @Valid orderReq: OrderReq
    ): BaseResponse<Unit> {
        if (orderReq.productId == null) return BaseResponse(BaseResponseCode.NULL_PRODUCT_ID)
        // 선택사항 미입력 시 null 처리
        if (orderReq.websiteUrl.isNullOrBlank()) orderReq.websiteUrl = null
        return BaseResponse(orderService.makeOrder(userAccount.getUser(), orderReq))
    }

    // 요청 상세 조회
    @Operation(summary = "요청 상세 조회(박서연)", description = "요청을 상세조회 한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping("/{orderId}")
    fun getOrderDetail(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 요청 Id", example = "1") @PathVariable orderId: Long
    ): BaseResponse<OrderRes> {
        return BaseResponse(orderService.getOrderDetail(userAccount.getUser(), orderId))
    }

    // 요청 목록 조회
    @Operation(summary = "요청 목록 조회(박서연)", description = "요청 목록을 조회 한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "올바르지 않은 요청 타입입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping
    fun getOrderList(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "요청 타입", example = "sell/order") @RequestParam type: String,
        @Parameter(
            description = "요청 상태",
            example = "요청대기/진행중/진행완료/요청취소"
        ) @RequestParam(required = false) status: String?,
        @ParameterObject @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): BaseResponse<Page<OrderListRes>> {
        if (type !in listOf(SELL, ORDER)) return BaseResponse(BaseResponseCode.INVALID_ORDER_TYPE)

        // 전체 요청 상태 조회
        return if (status == null) BaseResponse(orderService.getOrderList(userAccount.getUser(), type, pageable))
        // 요청 상태별 조회
        else BaseResponse(orderService.getOrderListByOrderStatus(userAccount.getUser(), type, status, pageable))
    }

    // 요청 수정
    @Operation(summary = "요청 수정(박서연)", description = "요청을 수정한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "이름을 입력해주세요.<br>" +
                        "이름은 최대 100자입니다.<br>" +
                        "문의사항을 입력해주세요.<br>" +
                        "문의사항은 최대 250자입니다.<br>" +
                        "요청 상세설명을 입력해주세요.<br>" +
                        "요청 상세설명은 최대 250자입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PatchMapping("/{orderId}")
    fun editOrder(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 요청 Id", example = "1") @PathVariable orderId: Long,
        @RequestBody @Valid orderReq: OrderReq
    ): BaseResponse<Unit> {
        if (orderReq.websiteUrl.isNullOrBlank()) orderReq.websiteUrl = null
        return BaseResponse(orderService.editOrder(userAccount.getUser(), orderReq, orderId))
    }

    // 요청 상태 수정
    @Operation(summary = "요청 상태 수정(박서연)", description = "요청 상태를 수정한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "요청 상태를 입력해주세요.<br>" +
                        "올바르지 않은 요청 상태입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PatchMapping("/{orderId}/status")
    fun editOrderStatus(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 요청 Id", example = "1") @PathVariable orderId: Long,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "요청 상태",
            content = [Content(
                examples = [ExampleObject(
                    value = "{" +
                            "\"status\" : \"진행중\"" +
                            "}"
                )], schema = Schema(title = "status", allowableValues = ["진행중", "진행완료", "요청취소"])
            )]

        ) @RequestBody status: Map<String, String>
    ): BaseResponse<Unit> {
        status[Constant.Order.STATUS] ?: return BaseResponse(BaseResponseCode.NULL_ORDER_STATUS)
        return BaseResponse(orderService.editOrderStatus(userAccount.getUser(), status[STATUS]!!, orderId))
    }
}