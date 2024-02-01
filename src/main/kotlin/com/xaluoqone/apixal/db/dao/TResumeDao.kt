package com.xaluoqone.apixal.db.dao

import com.xaluoqone.apixal.db.table.TResume
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TResumeDao {
    @Select("SELECT * FROM ax_resume")
    suspend fun selectAllResumes(): List<TResume>?
}