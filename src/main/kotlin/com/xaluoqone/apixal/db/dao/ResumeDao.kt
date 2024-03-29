package com.xaluoqone.apixal.db.dao

import com.xaluoqone.apixal.db.table.TResume
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ResumeDao {
    @Select("select * from ax_resume")
    fun selectAll(): List<TResume>?

    @Insert("insert into ax_resume value (#{id},#{name},#{url})")
    fun insertOne(resume: TResume): Int
}