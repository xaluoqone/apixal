package com.xaluoqone.apixal.service

import com.xaluoqone.apixal.beans.AXResume
import com.xaluoqone.apixal.db.dao.ResumeDao
import com.xaluoqone.apixal.db.table.TResume
import com.xaluoqone.apixal.exts.save
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.sql.SQLException

@Service
class ResumeService(
    private val resumeDao: ResumeDao
) {
    suspend fun getAllAXResumes(): List<AXResume> {
        val res = resumeDao.selectAll() ?: listOf()
        return res.map {
            AXResume(id = it.id, name = it.name, url = it.url)
        }
    }

    suspend fun saveResume(file: MultipartFile): TResume {
        val fileName = file.originalFilename!!
        val remoteUrl = file.save(savePath = "files/resumes/")
        return insertResume(name = fileName, url = remoteUrl)
    }

    suspend fun saveResume(inputStream: InputStream, fileName: String): TResume {
        val remoteUrl = inputStream.save("", "files/resumes/")
        return insertResume(name = fileName, url = remoteUrl)
    }

    private suspend fun insertResume(name: String, url: String): TResume {
        val resume = TResume(name = name, url = url)
        if (resumeDao.insertOne(resume) < 1) {
            throw SQLException("数据插入异常：${resume}")
        }
        return resume
    }
}