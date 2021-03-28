package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;


@Mapper
public interface FilesMapper {

    // Select a file with a specific file name
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    Files getFile(String fileName);

    // Select all files of a specific user
    @Select("SELECT filename FROM FILES WHERE userid = #{userID}")
    String[] getAllFilesForUserID(Integer userID);

    // Add a new file to the DB
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userID}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileID")
    int insertFile(Files file);

    // Delete a file from the DB
    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    void deleteFile(String fileName);

}
