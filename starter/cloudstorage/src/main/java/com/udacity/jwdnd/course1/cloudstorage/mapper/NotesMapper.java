package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    // Select a note with a specific ID
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteID}")
    Notes getNote(Integer noteID);

    // Select all notes for a specific user
    @Select("SELECT notetitle FROM NOTES WHERE userid = #{userID}")
    String[] getAllNotesForUserID(Integer userID);

    // Add a new note to the DB
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "noteID")
    int insertNote(Notes note);

    // Delete a note from the DB
    @Delete("DELETE FROM NOTES WHERE notetitle = #{noteTitle}")
    void deleteNote(String noteTitle);

    // Update a note
    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteID}")
    void updateNote(Integer noteID, String title, String description);


}
