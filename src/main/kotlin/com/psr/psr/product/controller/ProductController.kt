package com.psr.psr.product.controller

import com.psr.psr.global.Constant.SortType.OrderType.RECENT
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.product.dto.request.CreateproductReq
import com.psr.psr.product.dto.request.ReportProductReq
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "상품 API")
@SecurityRequirement(name = "Bearer")
class ProductController(
        private val productService: ProductService
) {

        /**
         * 상품 메인 조회
         */
        @Operation(summary = "상품 메인 조회(박소정)", description = "상품 메인 목록을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 사용자 카테고리입니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @GetMapping()
        fun getProducts(@AuthenticationPrincipal userAccount: UserAccount,
                        @Parameter(description = "(String) 카테고리(null = 관심목록) <br>" +
                                "방송가능 상품소싱 <br> 쇼호스트 구인 <br> 라이브커머스 대행 <br> 라이브커머스 교육 <br> 스마트스토어 런칭 <br> 영상편집 <br> 강사매칭 <br> SNS 마케팅 <br> 홍보물 디자인",) @RequestParam(required = false) category: String,
                        @ParameterObject  @PageableDefault(size = 8) pageable: Pageable): BaseResponse<GetProductsRes> {
                return BaseResponse(productService.getProducts(userAccount.getUser(), category, pageable));
        }

        /**
         * 상품 상세 조회 - 상품
         */
        @Operation(summary = "상품 상세 조회 - 상품(박소정)", description = "상품 상세 조회의 상품을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "404",
                    description = "해당 상품을 찾을 수 없습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @GetMapping("/{productId}")
        fun getProductDetail(@AuthenticationPrincipal userAccount: UserAccount,
                             @Parameter(description = "(Long) 상품 id", example = "1") @PathVariable productId: Long): BaseResponse<GetProductDetailRes> {
                return BaseResponse(productService.getProductDetail(userAccount.getUser(), productId));
        }

        /**
         * 마이 게시글
         */
        @Operation(summary = "마이 게시글(박소정)", description = "나의 게시글을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/myproducts")
        fun getMyProducts(@AuthenticationPrincipal userAccount: UserAccount,
                          @ParameterObject @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetMyProductsRes> {
                return BaseResponse(productService.getMyProducts(userAccount.getUser(), pageable));
        }

        /**
         * 유저 상품 목록
         */
        @Operation(summary = "유저 상품 목록(박소정)", description = "유저의 상품 목록을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @GetMapping("/users/{userId}")
        fun getProductsByUser(@Parameter(description = "(Long) 유저 id", example = "1") @PathVariable userId: Long,
                              @ParameterObject @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetProductsByUserRes> {
                return BaseResponse(productService.getProductsByUser(userId, pageable))
        }

        /**
         * 찜 목록
         */
        @Operation(summary = "찜 목록(박소정)", description = "찜 목록을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/likes")
        fun getLikeProducts(@AuthenticationPrincipal userAccount: UserAccount,
                            @ParameterObject @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetLikeProductsRes> {
                return BaseResponse(productService.getLikeProducts(userAccount.getUser(), pageable))
        }

        /**
         * 상품 신고
         */
        @Operation(summary = "상품 신고(박소정)", description = "상품을 신고한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 신고 카테고리입니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "해당 상품을 찾을 수 없습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                ),
                ApiResponse(
                    responseCode = "409",
                    description = "이미 신고 완료되었습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @PostMapping("/{productId}/report")
        fun reportProduct(@AuthenticationPrincipal userAccount: UserAccount,
                          @Parameter(description = "(Long) 상품 id", example = "1") @PathVariable productId: Long,
                          @RequestBody @Valid request: ReportProductReq): BaseResponse<Unit> {
                val category = ReportCategory.findByValue(request.category)
                return BaseResponse(productService.reportProduct(userAccount.getUser(), productId, category))
        }

        /**
         * 홈 화면 조회 - 상품
         */
        @Operation(summary = "홈 화면 조회 - 상품(박소정)", description = "홈 화면 - 상품을 조회한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/home")
        fun getHomePage(): BaseResponse<GetHomePageRes> {
                return BaseResponse(productService.getHomePage())
        }


        /**
         * 상품 등록
         */
        @Operation(summary = "상품 등록(박소정)", description = "상품을 등록한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "400",
                    description = "상품 카테고리을 선택해주세요. <br> 상품명을 입력해주세요. <br> 상품 가격은 양수이어야 합니다. <br> 상품 설명을 입력해주세요. <br> imgUrl은 null 또는 1~5개이어야 합니다. <br> 올바르지 않은 상품 카테고리입니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @PostMapping("")
        fun createProduct(@AuthenticationPrincipal userAccount: UserAccount,
                          @RequestBody @Valid request: CreateproductReq): BaseResponse<Unit> {
                return BaseResponse(productService.createProduct(userAccount.getUser(), request))
        }

        /**
         * 상품 찜
         */
        @Operation(summary = "상품 찜(박소정)", description = "상품을 찜한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "404",
                    description = "해당 상품을 찾을 수 없습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @PostMapping("/{productId}/likes")
        fun likeProduct(@AuthenticationPrincipal userAccount: UserAccount,
                        @Parameter(description = "(Long) 상품 id", example = "1") @PathVariable productId: Long): BaseResponse<Unit> {
                return BaseResponse(productService.likeProduct(userAccount.getUser(), productId))
        }

        /**
         * 상품 삭제
         */
        @Operation(summary = "상품 삭제(박소정)", description = "상품을 삭제한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                ApiResponse(
                    responseCode = "400",
                    description = "해당 상품을 찾을 수 없습니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "해당 글 작성자가 아닙니다.",
                    content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                )]
        )
        @DeleteMapping("/{productId}")
        fun deleteProduct(@AuthenticationPrincipal userAccount: UserAccount,
                          @Parameter(description = "(Long) 상품 id", example = "1") @PathVariable productId: Long): BaseResponse<Unit> {
                return BaseResponse(productService.deleteProduct(userAccount.getUser(), productId))
        }

        /**
         * 상품 검색
         */
        @Operation(summary = "상품 검색(박소정)", description = "상품을 검색한다.")
        @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/search")
        fun searchProducts(@AuthenticationPrincipal userAccount: UserAccount,
                           @Parameter(description = "(Long) 검색어", example = "목도리") @RequestParam(required = true) keyword: String,
                           @Parameter(description = "(Long) 상품 정렬 (최신순 <br> 인기순)", example = "최신순") @RequestParam(required = false, defaultValue = RECENT) sortType: String,
                           @ParameterObject @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetSearchProducts> {
                return BaseResponse(productService.searchProducts(userAccount.getUser(), keyword, sortType, pageable))
        }

        /**
         * 상품 수정
         */
        @PatchMapping("/{productId}")
        fun modifyProduct(@AuthenticationPrincipal userAccount: UserAccount,
                          @PathVariable productId: Long,
                          @RequestBody @Valid request: CreateproductReq): BaseResponse<Unit> {
                return BaseResponse(productService.modifyProduct(userAccount.getUser(), productId, request))
        }

}