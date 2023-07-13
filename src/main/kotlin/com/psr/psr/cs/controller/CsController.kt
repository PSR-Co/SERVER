package com.psr.psr.cs.controller

import com.psr.psr.cs.service.CsService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cs")
class CsController(
        private val csService: CsService
) {
}