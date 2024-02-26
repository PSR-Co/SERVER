# (주)피에스알 라이브커머스 매칭 시스템
<br>

![psr-1](https://github.com/PSR-Co/SERVER/assets/90203250/62617bc8-47ab-4dfa-86ae-92a1a2c60cd0)
<br>

## Tech Stack
### Backend
<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logoColor=white"> <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> 

### DB
<img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/jasypt-0769AD?style=for-the-badge&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> 

### CI/CD
<img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/docker hub-2496ED?style=for-the-badge&logo=docker&logoColor=white">

### Develop Tool
<img src="https://img.shields.io/badge/intelliJ-000000?style=for-the-badge&logo=intellij idea&logoColor=white"> <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> 

### Etc.
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"/> <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white"/>
<br>

## Project Architecture
<details>
<summary>CI/CD</summary>
    
<img width="782" alt="스크린샷 2023-09-06 오후 7 21 59" src="https://github.com/IceButler/IceButler_Server/assets/90022940/4db4fce7-c681-424c-a0e3-e8a2661379b3">
</details>

<br>

## Project Structure

<details>
<summary>Details</summary>

```jsx
│       │   │   │   └── com
│       │   │   │       └── psr
│       │   │   │           └── psr
│       │   │   │               ├── PsrApplication.class
│       │   │   │               ├── PsrApplicationKt.class
│       │   │   │               ├── chat
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── ChatController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── request
│       │   │   │               │   │   │   └── ChatMessageReq.class
│       │   │   │               │   │   └── response
│       │   │   │               │   │       ├── ChatMessageRes$Companion.class
│       │   │   │               │   │       ├── ChatMessageRes.class
│       │   │   │               │   │       ├── ChatMessagesRes$Companion.class
│       │   │   │               │   │       ├── ChatMessagesRes.class
│       │   │   │               │   │       ├── ChatRoomRes$Companion.class
│       │   │   │               │   │       ├── ChatRoomRes.class
│       │   │   │               │   │       ├── GetChatMessagesRes$Companion.class
│       │   │   │               │   │       ├── GetChatMessagesRes.class
│       │   │   │               │   │       ├── GetChatRoomsRes$Companion.class
│       │   │   │               │   │       └── GetChatRoomsRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── ChatMessage$Companion.class
│       │   │   │               │   │   ├── ChatMessage.class
│       │   │   │               │   │   ├── ChatRoom$Companion.class
│       │   │   │               │   │   └── ChatRoom.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── ChatMessageCustom.class
│       │   │   │               │   │   ├── ChatMessageRepository.class
│       │   │   │               │   │   ├── ChatMessageRepositoryImpl.class
│       │   │   │               │   │   ├── ChatRoomCustom.class
│       │   │   │               │   │   ├── ChatRoomRepository.class
│       │   │   │               │   │   └── ChatRoomRepositoryImpl.class
│       │   │   │               │   └── service
│       │   │   │               │       └── ChatService.class
│       │   │   │               ├── cs
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── CsController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   └── response
│       │   │   │               │   │       ├── FaqListRes$Companion.class
│       │   │   │               │   │       ├── FaqListRes.class
│       │   │   │               │   │       ├── FaqRes$Companion.class
│       │   │   │               │   │       ├── FaqRes.class
│       │   │   │               │   │       ├── NoticeListRes$Companion.class
│       │   │   │               │   │       ├── NoticeListRes.class
│       │   │   │               │   │       ├── NoticeRes$Companion.class
│       │   │   │               │   │       └── NoticeRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── Faq.class
│       │   │   │               │   │   ├── FaqType$Companion.class
│       │   │   │               │   │   ├── FaqType.class
│       │   │   │               │   │   └── Notice.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── FaqRepository.class
│       │   │   │               │   │   └── NoticeRepository.class
│       │   │   │               │   └── service
│       │   │   │               │       └── CsService.class
│       │   │   │               ├── global
│       │   │   │               │   ├── Constant$JWT$JWT.class
│       │   │   │               │   ├── Constant$JWT.class
│       │   │   │               │   ├── Constant$NotiSentence$NotiSentence.class
│       │   │   │               │   ├── Constant$NotiSentence.class
│       │   │   │               │   ├── Constant$Order$Order.class
│       │   │   │               │   ├── Constant$Order.class
│       │   │   │               │   ├── Constant$REPORT$REPORT.class
│       │   │   │               │   ├── Constant$REPORT.class
│       │   │   │               │   ├── Constant$SortType$OrderType.class
│       │   │   │               │   ├── Constant$SortType.class
│       │   │   │               │   ├── Constant$UserEID$UserEID.class
│       │   │   │               │   ├── Constant$UserEID.class
│       │   │   │               │   ├── Constant$UserPhone$UserPhone.class
│       │   │   │               │   ├── Constant$UserPhone.class
│       │   │   │               │   ├── Constant$UserStatus$UserStatus.class
│       │   │   │               │   ├── Constant$UserStatus.class
│       │   │   │               │   ├── Constant.class
│       │   │   │               │   ├── config
│       │   │   │               │   │   ├── JasyptConfig.class
│       │   │   │               │   │   ├── JwtSecurityConfig.class
│       │   │   │               │   │   ├── OpenEntityManagerConfig.class
│       │   │   │               │   │   ├── QueryDslConfig.class
│       │   │   │               │   │   ├── RedisConfig.class
│       │   │   │               │   │   ├── SwaggerConfig.class
│       │   │   │               │   │   └── WebSecurityConfig.class
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── globalController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── BaseResponse$Companion.class
│       │   │   │               │   │   └── BaseResponse.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── BaseEntity.class
│       │   │   │               │   │   ├── ReportCategory$Companion.class
│       │   │   │               │   │   └── ReportCategory.class
│       │   │   │               │   ├── entityListener
│       │   │   │               │   │   ├── BeanUtils$Companion.class
│       │   │   │               │   │   ├── BeanUtils.class
│       │   │   │               │   │   ├── ProductEntityListener.class
│       │   │   │               │   │   ├── ReviewEntityListener.class
│       │   │   │               │   │   └── UserEntityListener.class
│       │   │   │               │   ├── exception
│       │   │   │               │   │   ├── BaseException.class
│       │   │   │               │   │   ├── BaseResponseCode.class
│       │   │   │               │   │   └── ExceptionHandler.class
│       │   │   │               │   ├── jwt
│       │   │   │               │   │   ├── JwtFilter.class
│       │   │   │               │   │   ├── UserAccount.class
│       │   │   │               │   │   ├── UserDetailsServiceImpl.class
│       │   │   │               │   │   ├── dto
│       │   │   │               │   │   │   ├── TokenDto$Companion.class
│       │   │   │               │   │   │   └── TokenDto.class
│       │   │   │               │   │   ├── exception
│       │   │   │               │   │   │   ├── JwtAccessDeniedHandler.class
│       │   │   │               │   │   │   └── JwtAuthenticationEntryPoint.class
│       │   │   │               │   │   └── utils
│       │   │   │               │   │       └── JwtUtils.class
│       │   │   │               │   └── resolver
│       │   │   │               │       ├── EnumType.class
│       │   │   │               │       ├── EnumValid.class
│       │   │   │               │       ├── EnumValidator.class
│       │   │   │               │       └── EnumValue.class
│       │   │   │               ├── inquiry
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── InquiryController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── InquiryAnswerReq.class
│       │   │   │               │   │   ├── InquiryListRes$Companion.class
│       │   │   │               │   │   ├── InquiryListRes.class
│       │   │   │               │   │   ├── InquiryReq.class
│       │   │   │               │   │   ├── InquiryRes$Companion.class
│       │   │   │               │   │   └── InquiryRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── Inquiry$Companion.class
│       │   │   │               │   │   ├── Inquiry.class
│       │   │   │               │   │   ├── InquiryStatus$Companion.class
│       │   │   │               │   │   └── InquiryStatus.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   └── InquiryRepository.class
│       │   │   │               │   └── service
│       │   │   │               │       └── InquiryService.class
│       │   │   │               ├── notification
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── NotificationController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── Data$Companion.class
│       │   │   │               │   │   ├── Data.class
│       │   │   │               │   │   ├── FcmMessage$Companion.class
│       │   │   │               │   │   ├── FcmMessage.class
│       │   │   │               │   │   ├── Message$Companion.class
│       │   │   │               │   │   ├── Message.class
│       │   │   │               │   │   ├── NotiList.class
│       │   │   │               │   │   ├── Notification$Companion.class
│       │   │   │               │   │   ├── Notification.class
│       │   │   │               │   │   └── NotificationListRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── NotificationType.class
│       │   │   │               │   │   ├── PushNotification$Companion.class
│       │   │   │               │   │   └── PushNotification.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── NotificationCustom.class
│       │   │   │               │   │   ├── NotificationRepository.class
│       │   │   │               │   │   └── NotificationRepositoryImpl.class
│       │   │   │               │   └── service
│       │   │   │               │       └── NotificationService.class
│       │   │   │               ├── order
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── OrderController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── OrderListRes$Companion.class
│       │   │   │               │   │   ├── OrderListRes.class
│       │   │   │               │   │   ├── OrderReq.class
│       │   │   │               │   │   ├── OrderRes$Companion.class
│       │   │   │               │   │   └── OrderRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── Order$Companion.class
│       │   │   │               │   │   ├── Order.class
│       │   │   │               │   │   ├── OrderStatus$Companion.class
│       │   │   │               │   │   └── OrderStatus.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── OrderCustom.class
│       │   │   │               │   │   ├── OrderRepository.class
│       │   │   │               │   │   └── OrderRepositoryImpl.class
│       │   │   │               │   └── service
│       │   │   │               │       └── OrderService.class
│       │   │   │               ├── product
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── ProductController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── request
│       │   │   │               │   │   │   ├── CreateproductReq.class
│       │   │   │               │   │   │   └── ReportProductReq.class
│       │   │   │               │   │   └── response
│       │   │   │               │   │       ├── GetHomePageRes$Companion.class
│       │   │   │               │   │       ├── GetHomePageRes.class
│       │   │   │               │   │       ├── GetLikeProductsRes$Companion.class
│       │   │   │               │   │       ├── GetLikeProductsRes.class
│       │   │   │               │   │       ├── GetMyProductsRes$Companion.class
│       │   │   │               │   │       ├── GetMyProductsRes.class
│       │   │   │               │   │       ├── GetProductDetailRes$Companion.class
│       │   │   │               │   │       ├── GetProductDetailRes.class
│       │   │   │               │   │       ├── GetProductsByUserRes$Companion.class
│       │   │   │               │   │       ├── GetProductsByUserRes.class
│       │   │   │               │   │       ├── GetProductsRes.class
│       │   │   │               │   │       ├── GetSearchProducts$Companion.class
│       │   │   │               │   │       ├── GetSearchProducts.class
│       │   │   │               │   │       ├── MainProduct$Companion.class
│       │   │   │               │   │       ├── MainProduct.class
│       │   │   │               │   │       ├── MainTopProduct$Companion.class
│       │   │   │               │   │       ├── MainTopProduct.class
│       │   │   │               │   │       ├── MyProduct$Companion.class
│       │   │   │               │   │       ├── MyProduct.class
│       │   │   │               │   │       ├── PopularProductDetail.class
│       │   │   │               │   │       └── ProductDetail.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── Product$Companion.class
│       │   │   │               │   │   ├── Product.class
│       │   │   │               │   │   ├── ProductImg$Companion.class
│       │   │   │               │   │   ├── ProductImg.class
│       │   │   │               │   │   ├── ProductLike$Companion.class
│       │   │   │               │   │   ├── ProductLike.class
│       │   │   │               │   │   ├── ProductReport$Companion.class
│       │   │   │               │   │   └── ProductReport.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── ProductCustom.class
│       │   │   │               │   │   ├── ProductImgRepository.class
│       │   │   │               │   │   ├── ProductLikeRepository.class
│       │   │   │               │   │   ├── ProductReportRepository.class
│       │   │   │               │   │   ├── ProductRepository.class
│       │   │   │               │   │   └── ProductRepositoryImpl.class
│       │   │   │               │   └── service
│       │   │   │               │       └── ProductService.class
│       │   │   │               ├── review
│       │   │   │               │   ├── controller
│       │   │   │               │   │   └── ReviewController.class
│       │   │   │               │   ├── dto
│       │   │   │               │   │   ├── GetProductDetailRes$Companion.class
│       │   │   │               │   │   ├── GetProductDetailRes.class
│       │   │   │               │   │   ├── ReviewDetailTop$Companion.class
│       │   │   │               │   │   ├── ReviewDetailTop.class
│       │   │   │               │   │   ├── ReviewListRes$Companion.class
│       │   │   │               │   │   ├── ReviewListRes.class
│       │   │   │               │   │   ├── ReviewReq.class
│       │   │   │               │   │   ├── ReviewRes$Companion.class
│       │   │   │               │   │   └── ReviewRes.class
│       │   │   │               │   ├── entity
│       │   │   │               │   │   ├── Review$Companion.class
│       │   │   │               │   │   ├── Review.class
│       │   │   │               │   │   ├── ReviewImg$Companion.class
│       │   │   │               │   │   ├── ReviewImg.class
│       │   │   │               │   │   ├── ReviewReport$Companion.class
│       │   │   │               │   │   └── ReviewReport.class
│       │   │   │               │   ├── repository
│       │   │   │               │   │   ├── ReviewImgRepository.class
│       │   │   │               │   │   ├── ReviewReportRepository.class
│       │   │   │               │   │   └── ReviewRepository.class
│       │   │   │               │   └── service
│       │   │   │               │       └── ReviewService.class
│       │   │   │               └── user
│       │   │   │                   ├── controller
│       │   │   │                   │   └── UserController.class
│       │   │   │                   ├── dto
│       │   │   │                   │   ├── UserInterestDto.class
│       │   │   │                   │   ├── UserInterestListDto$Companion.class
│       │   │   │                   │   ├── UserInterestListDto.class
│       │   │   │                   │   ├── eidReq
│       │   │   │                   │   │   ├── Business.class
│       │   │   │                   │   │   ├── BusinessListReq$Companion.class
│       │   │   │                   │   │   ├── BusinessListReq.class
│       │   │   │                   │   │   ├── BusinessListRes.class
│       │   │   │                   │   │   ├── BusinessRes.class
│       │   │   │                   │   │   └── BusinessStatusRes.class
│       │   │   │                   │   ├── phoneReq
│       │   │   │                   │   │   ├── MessageReq.class
│       │   │   │                   │   │   ├── SMSReq$Companion.class
│       │   │   │                   │   │   └── SMSReq.class
│       │   │   │                   │   ├── request
│       │   │   │                   │   │   ├── ChangePasswordReq.class
│       │   │   │                   │   │   ├── CheckNicknameReq.class
│       │   │   │                   │   │   ├── FindIdReq.class
│       │   │   │                   │   │   ├── FindPwReq.class
│       │   │   │                   │   │   ├── LoginReq.class
│       │   │   │                   │   │   ├── ProfileReq.class
│       │   │   │                   │   │   ├── ResetPasswordReq.class
│       │   │   │                   │   │   ├── SendSmsReq.class
│       │   │   │                   │   │   ├── SignUpReq.class
│       │   │   │                   │   │   ├── UserEidReq.class
│       │   │   │                   │   │   └── ValidPhoneReq.class
│       │   │   │                   │   └── response
│       │   │   │                   │       ├── EmailRes$Companion.class
│       │   │   │                   │       ├── EmailRes.class
│       │   │   │                   │       ├── MyPageInfoRes$Companion.class
│       │   │   │                   │       ├── MyPageInfoRes.class
│       │   │   │                   │       ├── PostNotiRes$Companion.class
│       │   │   │                   │       ├── PostNotiRes.class
│       │   │   │                   │       ├── ProfileRes$Companion.class
│       │   │   │                   │       └── ProfileRes.class
│       │   │   │                   ├── entity
│       │   │   │                   │   ├── BusinessInfo$Companion.class
│       │   │   │                   │   ├── BusinessInfo.class
│       │   │   │                   │   ├── Category$Companion.class
│       │   │   │                   │   ├── Category.class
│       │   │   │                   │   ├── Provider.class
│       │   │   │                   │   ├── Type$Companion.class
│       │   │   │                   │   ├── Type.class
│       │   │   │                   │   ├── User$Companion.class
│       │   │   │                   │   ├── User.class
│       │   │   │                   │   ├── UserInterest$Companion.class
│       │   │   │                   │   └── UserInterest.class
│       │   │   │                   ├── repository
│       │   │   │                   │   ├── BusinessInfoRepository.class
│       │   │   │                   │   ├── UserInterestRepository.class
│       │   │   │                   │   └── UserRepository.class
│       │   │   │                   ├── service
│       │   │   │                   │   ├── RedisService.class
│       │   │   │                   │   └── UserService.class
│       │   │   │                   └── utils
│       │   │   │                       └── SmsUtils.class
│       │   │   └── test
│       │   └── stubs
│       │       ├── main
│       │       │   ├── com
│       │       │   │   └── psr
│       │       │   │       └── psr
│       │       │   │           ├── PsrApplication.java
│       │       │   │           ├── PsrApplication.kapt_metadata
│       │       │   │           ├── PsrApplicationKt.java
│       │       │   │           ├── PsrApplicationKt.kapt_metadata
│       │       │   │           ├── chat
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── ChatController.java
│       │       │   │           │   │   └── ChatController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── request
│       │       │   │           │   │   │   ├── ChatMessageReq.java
│       │       │   │           │   │   │   └── ChatMessageReq.kapt_metadata
│       │       │   │           │   │   └── response
│       │       │   │           │   │       ├── ChatMessageRes.java
│       │       │   │           │   │       ├── ChatMessageRes.kapt_metadata
│       │       │   │           │   │       ├── ChatMessagesRes.java
│       │       │   │           │   │       ├── ChatMessagesRes.kapt_metadata
│       │       │   │           │   │       ├── ChatRoomRes.java
│       │       │   │           │   │       ├── ChatRoomRes.kapt_metadata
│       │       │   │           │   │       ├── GetChatMessagesRes.java
│       │       │   │           │   │       ├── GetChatMessagesRes.kapt_metadata
│       │       │   │           │   │       ├── GetChatRoomsRes.java
│       │       │   │           │   │       └── GetChatRoomsRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── ChatMessage.java
│       │       │   │           │   │   ├── ChatMessage.kapt_metadata
│       │       │   │           │   │   ├── ChatRoom.java
│       │       │   │           │   │   └── ChatRoom.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── ChatMessageCustom.java
│       │       │   │           │   │   ├── ChatMessageCustom.kapt_metadata
│       │       │   │           │   │   ├── ChatMessageRepository.java
│       │       │   │           │   │   ├── ChatMessageRepository.kapt_metadata
│       │       │   │           │   │   ├── ChatMessageRepositoryImpl.java
│       │       │   │           │   │   ├── ChatMessageRepositoryImpl.kapt_metadata
│       │       │   │           │   │   ├── ChatRoomCustom.java
│       │       │   │           │   │   ├── ChatRoomCustom.kapt_metadata
│       │       │   │           │   │   ├── ChatRoomRepository.java
│       │       │   │           │   │   ├── ChatRoomRepository.kapt_metadata
│       │       │   │           │   │   ├── ChatRoomRepositoryImpl.java
│       │       │   │           │   │   └── ChatRoomRepositoryImpl.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── ChatService.java
│       │       │   │           │       └── ChatService.kapt_metadata
│       │       │   │           ├── cs
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── CsController.java
│       │       │   │           │   │   └── CsController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   └── response
│       │       │   │           │   │       ├── FaqListRes.java
│       │       │   │           │   │       ├── FaqListRes.kapt_metadata
│       │       │   │           │   │       ├── FaqRes.java
│       │       │   │           │   │       ├── FaqRes.kapt_metadata
│       │       │   │           │   │       ├── NoticeListRes.java
│       │       │   │           │   │       ├── NoticeListRes.kapt_metadata
│       │       │   │           │   │       ├── NoticeRes.java
│       │       │   │           │   │       └── NoticeRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── Faq.java
│       │       │   │           │   │   ├── Faq.kapt_metadata
│       │       │   │           │   │   ├── FaqType.java
│       │       │   │           │   │   ├── FaqType.kapt_metadata
│       │       │   │           │   │   ├── Notice.java
│       │       │   │           │   │   └── Notice.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── FaqRepository.java
│       │       │   │           │   │   ├── FaqRepository.kapt_metadata
│       │       │   │           │   │   ├── NoticeRepository.java
│       │       │   │           │   │   └── NoticeRepository.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── CsService.java
│       │       │   │           │       └── CsService.kapt_metadata
│       │       │   │           ├── global
│       │       │   │           │   ├── Constant.java
│       │       │   │           │   ├── Constant.kapt_metadata
│       │       │   │           │   ├── config
│       │       │   │           │   │   ├── JasyptConfig.java
│       │       │   │           │   │   ├── JasyptConfig.kapt_metadata
│       │       │   │           │   │   ├── JwtSecurityConfig.java
│       │       │   │           │   │   ├── JwtSecurityConfig.kapt_metadata
│       │       │   │           │   │   ├── OpenEntityManagerConfig.java
│       │       │   │           │   │   ├── OpenEntityManagerConfig.kapt_metadata
│       │       │   │           │   │   ├── QueryDslConfig.java
│       │       │   │           │   │   ├── QueryDslConfig.kapt_metadata
│       │       │   │           │   │   ├── RedisConfig.java
│       │       │   │           │   │   ├── RedisConfig.kapt_metadata
│       │       │   │           │   │   ├── SwaggerConfig.java
│       │       │   │           │   │   ├── SwaggerConfig.kapt_metadata
│       │       │   │           │   │   ├── WebSecurityConfig.java
│       │       │   │           │   │   └── WebSecurityConfig.kapt_metadata
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── globalController.java
│       │       │   │           │   │   └── globalController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── BaseResponse.java
│       │       │   │           │   │   └── BaseResponse.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── BaseEntity.java
│       │       │   │           │   │   ├── BaseEntity.kapt_metadata
│       │       │   │           │   │   ├── ReportCategory.java
│       │       │   │           │   │   └── ReportCategory.kapt_metadata
│       │       │   │           │   ├── entityListener
│       │       │   │           │   │   ├── BeanUtils.java
│       │       │   │           │   │   ├── BeanUtils.kapt_metadata
│       │       │   │           │   │   ├── ProductEntityListener.java
│       │       │   │           │   │   ├── ProductEntityListener.kapt_metadata
│       │       │   │           │   │   ├── ReviewEntityListener.java
│       │       │   │           │   │   ├── ReviewEntityListener.kapt_metadata
│       │       │   │           │   │   ├── UserEntityListener.java
│       │       │   │           │   │   └── UserEntityListener.kapt_metadata
│       │       │   │           │   ├── exception
│       │       │   │           │   │   ├── BaseException.java
│       │       │   │           │   │   ├── BaseException.kapt_metadata
│       │       │   │           │   │   ├── BaseResponseCode.java
│       │       │   │           │   │   ├── BaseResponseCode.kapt_metadata
│       │       │   │           │   │   ├── ExceptionHandler.java
│       │       │   │           │   │   └── ExceptionHandler.kapt_metadata
│       │       │   │           │   ├── jwt
│       │       │   │           │   │   ├── JwtFilter.java
│       │       │   │           │   │   ├── JwtFilter.kapt_metadata
│       │       │   │           │   │   ├── UserAccount.java
│       │       │   │           │   │   ├── UserAccount.kapt_metadata
│       │       │   │           │   │   ├── UserDetailsServiceImpl.java
│       │       │   │           │   │   ├── UserDetailsServiceImpl.kapt_metadata
│       │       │   │           │   │   ├── dto
│       │       │   │           │   │   │   ├── TokenDto.java
│       │       │   │           │   │   │   └── TokenDto.kapt_metadata
│       │       │   │           │   │   ├── exception
│       │       │   │           │   │   │   ├── JwtAccessDeniedHandler.java
│       │       │   │           │   │   │   ├── JwtAccessDeniedHandler.kapt_metadata
│       │       │   │           │   │   │   ├── JwtAuthenticationEntryPoint.java
│       │       │   │           │   │   │   └── JwtAuthenticationEntryPoint.kapt_metadata
│       │       │   │           │   │   └── utils
│       │       │   │           │   │       ├── JwtUtils.java
│       │       │   │           │   │       └── JwtUtils.kapt_metadata
│       │       │   │           │   └── resolver
│       │       │   │           │       ├── EnumType.java
│       │       │   │           │       ├── EnumType.kapt_metadata
│       │       │   │           │       ├── EnumValid.java
│       │       │   │           │       ├── EnumValid.kapt_metadata
│       │       │   │           │       ├── EnumValidator.java
│       │       │   │           │       ├── EnumValidator.kapt_metadata
│       │       │   │           │       ├── EnumValue.java
│       │       │   │           │       └── EnumValue.kapt_metadata
│       │       │   │           ├── inquiry
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── InquiryController.java
│       │       │   │           │   │   └── InquiryController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── InquiryAnswerReq.java
│       │       │   │           │   │   ├── InquiryAnswerReq.kapt_metadata
│       │       │   │           │   │   ├── InquiryListRes.java
│       │       │   │           │   │   ├── InquiryListRes.kapt_metadata
│       │       │   │           │   │   ├── InquiryReq.java
│       │       │   │           │   │   ├── InquiryReq.kapt_metadata
│       │       │   │           │   │   ├── InquiryRes.java
│       │       │   │           │   │   └── InquiryRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── Inquiry.java
│       │       │   │           │   │   ├── Inquiry.kapt_metadata
│       │       │   │           │   │   ├── InquiryStatus.java
│       │       │   │           │   │   └── InquiryStatus.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── InquiryRepository.java
│       │       │   │           │   │   └── InquiryRepository.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── InquiryService.java
│       │       │   │           │       └── InquiryService.kapt_metadata
│       │       │   │           ├── notification
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── NotificationController.java
│       │       │   │           │   │   └── NotificationController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── Data.java
│       │       │   │           │   │   ├── Data.kapt_metadata
│       │       │   │           │   │   ├── FcmMessage.java
│       │       │   │           │   │   ├── FcmMessage.kapt_metadata
│       │       │   │           │   │   ├── Message.java
│       │       │   │           │   │   ├── Message.kapt_metadata
│       │       │   │           │   │   ├── NotiList.java
│       │       │   │           │   │   ├── NotiList.kapt_metadata
│       │       │   │           │   │   ├── Notification.java
│       │       │   │           │   │   ├── Notification.kapt_metadata
│       │       │   │           │   │   ├── NotificationListRes.java
│       │       │   │           │   │   └── NotificationListRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── NotificationType.java
│       │       │   │           │   │   ├── NotificationType.kapt_metadata
│       │       │   │           │   │   ├── PushNotification.java
│       │       │   │           │   │   └── PushNotification.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── NotificationCustom.java
│       │       │   │           │   │   ├── NotificationCustom.kapt_metadata
│       │       │   │           │   │   ├── NotificationRepository.java
│       │       │   │           │   │   ├── NotificationRepository.kapt_metadata
│       │       │   │           │   │   ├── NotificationRepositoryImpl.java
│       │       │   │           │   │   └── NotificationRepositoryImpl.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── NotificationService.java
│       │       │   │           │       └── NotificationService.kapt_metadata
│       │       │   │           ├── order
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── OrderController.java
│       │       │   │           │   │   └── OrderController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── OrderListRes.java
│       │       │   │           │   │   ├── OrderListRes.kapt_metadata
│       │       │   │           │   │   ├── OrderReq.java
│       │       │   │           │   │   ├── OrderReq.kapt_metadata
│       │       │   │           │   │   ├── OrderRes.java
│       │       │   │           │   │   └── OrderRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── Order.java
│       │       │   │           │   │   ├── Order.kapt_metadata
│       │       │   │           │   │   ├── OrderStatus.java
│       │       │   │           │   │   └── OrderStatus.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── OrderCustom.java
│       │       │   │           │   │   ├── OrderCustom.kapt_metadata
│       │       │   │           │   │   ├── OrderRepository.java
│       │       │   │           │   │   ├── OrderRepository.kapt_metadata
│       │       │   │           │   │   ├── OrderRepositoryImpl.java
│       │       │   │           │   │   └── OrderRepositoryImpl.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── OrderService.java
│       │       │   │           │       └── OrderService.kapt_metadata
│       │       │   │           ├── product
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── ProductController.java
│       │       │   │           │   │   └── ProductController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── request
│       │       │   │           │   │   │   ├── CreateproductReq.java
│       │       │   │           │   │   │   ├── CreateproductReq.kapt_metadata
│       │       │   │           │   │   │   ├── ReportProductReq.java
│       │       │   │           │   │   │   └── ReportProductReq.kapt_metadata
│       │       │   │           │   │   └── response
│       │       │   │           │   │       ├── GetHomePageRes.java
│       │       │   │           │   │       ├── GetHomePageRes.kapt_metadata
│       │       │   │           │   │       ├── GetLikeProductsRes.java
│       │       │   │           │   │       ├── GetLikeProductsRes.kapt_metadata
│       │       │   │           │   │       ├── GetMyProductsRes.java
│       │       │   │           │   │       ├── GetMyProductsRes.kapt_metadata
│       │       │   │           │   │       ├── GetProductDetailRes.java
│       │       │   │           │   │       ├── GetProductDetailRes.kapt_metadata
│       │       │   │           │   │       ├── GetProductsByUserRes.java
│       │       │   │           │   │       ├── GetProductsByUserRes.kapt_metadata
│       │       │   │           │   │       ├── GetProductsRes.java
│       │       │   │           │   │       ├── GetProductsRes.kapt_metadata
│       │       │   │           │   │       ├── GetSearchProducts.java
│       │       │   │           │   │       ├── GetSearchProducts.kapt_metadata
│       │       │   │           │   │       ├── MainProduct.java
│       │       │   │           │   │       ├── MainProduct.kapt_metadata
│       │       │   │           │   │       ├── MainTopProduct.java
│       │       │   │           │   │       ├── MainTopProduct.kapt_metadata
│       │       │   │           │   │       ├── MyProduct.java
│       │       │   │           │   │       ├── MyProduct.kapt_metadata
│       │       │   │           │   │       ├── PopularProductDetail.java
│       │       │   │           │   │       ├── PopularProductDetail.kapt_metadata
│       │       │   │           │   │       ├── ProductDetail.java
│       │       │   │           │   │       └── ProductDetail.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── Product.java
│       │       │   │           │   │   ├── Product.kapt_metadata
│       │       │   │           │   │   ├── ProductImg.java
│       │       │   │           │   │   ├── ProductImg.kapt_metadata
│       │       │   │           │   │   ├── ProductLike.java
│       │       │   │           │   │   ├── ProductLike.kapt_metadata
│       │       │   │           │   │   ├── ProductReport.java
│       │       │   │           │   │   └── ProductReport.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── ProductCustom.java
│       │       │   │           │   │   ├── ProductCustom.kapt_metadata
│       │       │   │           │   │   ├── ProductImgRepository.java
│       │       │   │           │   │   ├── ProductImgRepository.kapt_metadata
│       │       │   │           │   │   ├── ProductLikeRepository.java
│       │       │   │           │   │   ├── ProductLikeRepository.kapt_metadata
│       │       │   │           │   │   ├── ProductReportRepository.java
│       │       │   │           │   │   ├── ProductReportRepository.kapt_metadata
│       │       │   │           │   │   ├── ProductRepository.java
│       │       │   │           │   │   ├── ProductRepository.kapt_metadata
│       │       │   │           │   │   ├── ProductRepositoryImpl.java
│       │       │   │           │   │   └── ProductRepositoryImpl.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── ProductService.java
│       │       │   │           │       └── ProductService.kapt_metadata
│       │       │   │           ├── review
│       │       │   │           │   ├── controller
│       │       │   │           │   │   ├── ReviewController.java
│       │       │   │           │   │   └── ReviewController.kapt_metadata
│       │       │   │           │   ├── dto
│       │       │   │           │   │   ├── GetProductDetailRes.java
│       │       │   │           │   │   ├── GetProductDetailRes.kapt_metadata
│       │       │   │           │   │   ├── ReviewDetailTop.java
│       │       │   │           │   │   ├── ReviewDetailTop.kapt_metadata
│       │       │   │           │   │   ├── ReviewListRes.java
│       │       │   │           │   │   ├── ReviewListRes.kapt_metadata
│       │       │   │           │   │   ├── ReviewReq.java
│       │       │   │           │   │   ├── ReviewReq.kapt_metadata
│       │       │   │           │   │   ├── ReviewRes.java
│       │       │   │           │   │   └── ReviewRes.kapt_metadata
│       │       │   │           │   ├── entity
│       │       │   │           │   │   ├── Review.java
│       │       │   │           │   │   ├── Review.kapt_metadata
│       │       │   │           │   │   ├── ReviewImg.java
│       │       │   │           │   │   ├── ReviewImg.kapt_metadata
│       │       │   │           │   │   ├── ReviewReport.java
│       │       │   │           │   │   └── ReviewReport.kapt_metadata
│       │       │   │           │   ├── repository
│       │       │   │           │   │   ├── ReviewImgRepository.java
│       │       │   │           │   │   ├── ReviewImgRepository.kapt_metadata
│       │       │   │           │   │   ├── ReviewReportRepository.java
│       │       │   │           │   │   ├── ReviewReportRepository.kapt_metadata
│       │       │   │           │   │   ├── ReviewRepository.java
│       │       │   │           │   │   └── ReviewRepository.kapt_metadata
│       │       │   │           │   └── service
│       │       │   │           │       ├── ReviewService.java
│       │       │   │           │       └── ReviewService.kapt_metadata
│       │       │   │           └── user
│       │       │   │               ├── controller
│       │       │   │               │   ├── UserController.java
│       │       │   │               │   └── UserController.kapt_metadata
│       │       │   │               ├── dto
│       │       │   │               │   ├── UserInterestDto.java
│       │       │   │               │   ├── UserInterestDto.kapt_metadata
│       │       │   │               │   ├── UserInterestListDto.java
│       │       │   │               │   ├── UserInterestListDto.kapt_metadata
│       │       │   │               │   ├── eidReq
│       │       │   │               │   │   ├── Business.java
│       │       │   │               │   │   ├── Business.kapt_metadata
│       │       │   │               │   │   ├── BusinessListReq.java
│       │       │   │               │   │   ├── BusinessListReq.kapt_metadata
│       │       │   │               │   │   ├── BusinessListRes.java
│       │       │   │               │   │   ├── BusinessListRes.kapt_metadata
│       │       │   │               │   │   ├── BusinessRes.java
│       │       │   │               │   │   ├── BusinessRes.kapt_metadata
│       │       │   │               │   │   ├── BusinessStatusRes.java
│       │       │   │               │   │   └── BusinessStatusRes.kapt_metadata
│       │       │   │               │   ├── phoneReq
│       │       │   │               │   │   ├── MessageReq.java
│       │       │   │               │   │   ├── MessageReq.kapt_metadata
│       │       │   │               │   │   ├── SMSReq.java
│       │       │   │               │   │   └── SMSReq.kapt_metadata
│       │       │   │               │   ├── request
│       │       │   │               │   │   ├── ChangePasswordReq.java
│       │       │   │               │   │   ├── ChangePasswordReq.kapt_metadata
│       │       │   │               │   │   ├── CheckNicknameReq.java
│       │       │   │               │   │   ├── CheckNicknameReq.kapt_metadata
│       │       │   │               │   │   ├── FindIdReq.java
│       │       │   │               │   │   ├── FindIdReq.kapt_metadata
│       │       │   │               │   │   ├── FindPwReq.java
│       │       │   │               │   │   ├── FindPwReq.kapt_metadata
│       │       │   │               │   │   ├── LoginReq.java
│       │       │   │               │   │   ├── LoginReq.kapt_metadata
│       │       │   │               │   │   ├── ProfileReq.java
│       │       │   │               │   │   ├── ProfileReq.kapt_metadata
│       │       │   │               │   │   ├── ResetPasswordReq.java
│       │       │   │               │   │   ├── ResetPasswordReq.kapt_metadata
│       │       │   │               │   │   ├── SendSmsReq.java
│       │       │   │               │   │   ├── SendSmsReq.kapt_metadata
│       │       │   │               │   │   ├── SignUpReq.java
│       │       │   │               │   │   ├── SignUpReq.kapt_metadata
│       │       │   │               │   │   ├── UserEidReq.java
│       │       │   │               │   │   ├── UserEidReq.kapt_metadata
│       │       │   │               │   │   ├── ValidPhoneReq.java
│       │       │   │               │   │   └── ValidPhoneReq.kapt_metadata
│       │       │   │               │   └── response
│       │       │   │               │       ├── EmailRes.java
│       │       │   │               │       ├── EmailRes.kapt_metadata
│       │       │   │               │       ├── MyPageInfoRes.java
│       │       │   │               │       ├── MyPageInfoRes.kapt_metadata
│       │       │   │               │       ├── PostNotiRes.java
│       │       │   │               │       ├── PostNotiRes.kapt_metadata
│       │       │   │               │       ├── ProfileRes.java
│       │       │   │               │       └── ProfileRes.kapt_metadata
│       │       │   │               ├── entity
│       │       │   │               │   ├── BusinessInfo.java
│       │       │   │               │   ├── BusinessInfo.kapt_metadata
│       │       │   │               │   ├── Category.java
│       │       │   │               │   ├── Category.kapt_metadata
│       │       │   │               │   ├── Provider.java
│       │       │   │               │   ├── Provider.kapt_metadata
│       │       │   │               │   ├── Type.java
│       │       │   │               │   ├── Type.kapt_metadata
│       │       │   │               │   ├── User.java
│       │       │   │               │   ├── User.kapt_metadata
│       │       │   │               │   ├── UserInterest.java
│       │       │   │               │   └── UserInterest.kapt_metadata
│       │       │   │               ├── repository
│       │       │   │               │   ├── BusinessInfoRepository.java
│       │       │   │               │   ├── BusinessInfoRepository.kapt_metadata
│       │       │   │               │   ├── UserInterestRepository.java
│       │       │   │               │   ├── UserInterestRepository.kapt_metadata
│       │       │   │               │   ├── UserRepository.java
│       │       │   │               │   └── UserRepository.kapt_metadata
│       │       │   │               ├── service
│       │       │   │               │   ├── RedisService.java
│       │       │   │               │   ├── RedisService.kapt_metadata
│       │       │   │               │   ├── UserService.java
│       │       │   │               │   └── UserService.kapt_metadata
│       │       │   │               └── utils
│       │       │   │                   ├── SmsUtils.java
│       │       │   │                   └── SmsUtils.kapt_metadata
│       │       │   └── error
│       │       │       └── NonExistentClass.java
│       │       └── test
│       └── test
├── build.gradle.kts
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── scripts
│   └── deploy.sh
├── settings.gradle.kts
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com
    │   │       └── psr
    │   │           └── psr
    │   │               ├── chat
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── cs
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── global
    │   │               │   ├── config
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── entityListener
    │   │               │   ├── exception
    │   │               │   ├── jwt
    │   │               │   │   ├── dto
    │   │               │   │   ├── exception
    │   │               │   │   └── utils
    │   │               │   └── resolver
    │   │               ├── inquiry
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── notification
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── order
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── product
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── review
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── entity
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               └── user
    │   │                   ├── controller
    │   │                   ├── dto
    │   │                   ├── entity
    │   │                   ├── repository
    │   │                   ├── service
    │   │                   └── utils
    │   └── resources
    └── test
        └── kotlin
            └── com
                └── psr
                    └── psr
                        └── PsrApplicationTests.kt

```
<br>
</details>
<br><br>


## DB 
<details>
<summary>ERD</summary>
    
<img width="821" alt="스크린샷 2023-09-06 오후 7 29 42" src="https://github.com/PSR-Co/SERVER/assets/90203250/3d84ca43-613c-4e7f-a6e1-cb5e593598cc">

<br>
</details>
<br><br>

<br>

## Commit/PR Convention
**Commit**
```
#1 feat: 일정 등록 API 추가
```
- #이슈번호 타입: 커밋 설명
<br>

**Pull Request**
```
[feat] 기본 프로젝트 설정
```
- [브랜치명]  설명
<br>

## Branch Strategy
- main
    - 배포 이력 관리 목적
- develop
    - feature 병합용 브랜치
    - 배포 전 병합 브랜치
- feat
    - develop 브랜치를 베이스로 API 별로 feat 브랜치 생성해 개발
- test
    - 테스트가 필요한 코드용 브랜치
- fix
    - 배포 후 버그 발생 시 버그 수정 
<br>

- feature branch의 경우, 기능명/이슈번호-기능설명 형태로 작성
```md
feat/#1-project-setting
```
<br>
<br>

## Member
|[박서연](https://github.com/psyeon1120)|[박소정](https://github.com/sojungpp)|[장채은](https://github.com/chaerlo127)|
|:---:|:---:|:---:|
|<img src="https://github.com/psyeon1120.png" width="180" height="180" >|<img src="https://github.com/sojungpp.png" width="180" height="180" >|<img src="https://github.com/chaerlo127.png" width="180" height="180" >|
| **Backend Developer** | **Project Manager <br> Backend Developer**| **Backend Developer** |
