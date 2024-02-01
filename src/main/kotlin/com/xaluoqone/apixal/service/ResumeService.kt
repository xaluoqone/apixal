package com.xaluoqone.apixal.service

import com.xaluoqone.apixal.beans.AXResume
import com.xaluoqone.apixal.db.dao.TResumeDao
import org.springframework.stereotype.Service

@Service
class ResumeService(
    private val tResumeDao: TResumeDao
) {
    suspend fun getAllAXResumes(): List<AXResume> {
        val res = tResumeDao.selectAllResumes() ?: listOf()
        return res.map {
            AXResume(id = it.id!!, name = it.name, url = it.url)
        }
    }
}