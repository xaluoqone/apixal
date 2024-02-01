package com.xaluoqone.apixal.controller

import com.xaluoqone.apixal.beans.AXResume
import com.xaluoqone.apixal.service.ResumeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ResumeController(
    private val resumeService: ResumeService
) {
    @GetMapping("/resumes")
    suspend fun getAllResumes(): List<AXResume> {
        return resumeService.getAllAXResumes()
    }
}