package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

    // Select a credential with a specific ID
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialID}")
    Credentials getCredential(Integer credentialID);

    // Select all credentials for a user
    @Select("SELECT url FROM CREDENTIALS WHERE userid = #{userID}")
    String[] getAllCredentialsForUserID(Integer userID);

    // Add a credential to DB
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialID")
    int insertCredential(Credentials credential);

    // Delete a credential from DB
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialID}")
    void deleteCredential(Integer credentialID);

    // Update a credential
    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}," +
            "password = #{password}, username = #{newUserName} WHERE credentialid = #{credentialID}")
    void updateCredential(Integer credentialID, String newUserName, String url, String key, String password);



}
