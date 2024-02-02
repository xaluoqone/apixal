package com.xaluoqone.apixal.controller

import com.xaluoqone.apixal.beans.AXResume
import com.xaluoqone.apixal.db.table.TResume
import com.xaluoqone.apixal.service.ResumeService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin
@RequestMapping("resume")
@RestController
class ResumeController(
    private val resumeService: ResumeService,
) {
    @GetMapping("/all")
    suspend fun getAllResumes(): List<AXResume> {
        return resumeService.getAllAXResumes()
    }

    @PostMapping("/upload")
    suspend fun postResumeFile(@RequestParam("file") file: MultipartFile): TResume {
        if (file.isEmpty) {
            throw IllegalArgumentException("The file is empty!")
        }
        return resumeService.saveResume(file)
    }

    @PostMapping("/uploadOne", consumes = [MediaType.APPLICATION_PDF_VALUE])
    suspend fun uploadResumeFile(request: HttpServletRequest): TResume {
        return resumeService.saveResume(request.inputStream, "${System.currentTimeMillis()}.pdf")
    }
}