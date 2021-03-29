package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
public class FileService {
    private final FilesMapper filesMapper;
    private final UsersMapper usersMapper;


    public FileService(FilesMapper filesMapper, UsersMapper usersMapper) {
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
    }

    public Files getFile(String fileName) {
        return filesMapper.getFile(fileName);
    }

    public String[] getAllFiles (Integer userID) {
        return filesMapper.getAllFilesForUserID(userID);
    }

    public void addFile(MultipartFile multipartFile, String username) throws IOException {
        // Get input stream from multipart file
        InputStream fis = multipartFile.getInputStream();
        // create an output buffer
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        // write data to the output buffer
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        Integer userID      = usersMapper.getUser(username).getUserID();
        String fileName     = multipartFile.getOriginalFilename();
        Integer fileID = 0;
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        byte[] fileData = buffer.toByteArray();

        Files file = new Files(fileID, fileName, contentType,
                fileSize, userID, fileData);
        filesMapper.insertFile(file);

    }


    public void delete(String fileName) {
        filesMapper.deleteFile(fileName);
    }

    public boolean isDuplicate(String fileName, Integer userID) {
        String[] allFileNames = filesMapper.getAllFilesForUserID(userID);
        return Arrays.asList(allFileNames).contains(fileName);
    }


}
