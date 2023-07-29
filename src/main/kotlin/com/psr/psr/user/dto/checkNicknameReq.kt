package com.psr.psr.user.dto

import com.psr.psr.user.entity.*
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull
import java.util.stream.Collectors


data class checkNicknameReq (
    @field:NotBlank
    val nickname: String
)