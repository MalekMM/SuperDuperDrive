package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;


@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final UsersMapper usersMapper;

    public CredentialService(CredentialsMapper credentialsMapper, UsersMapper usersMapper) {
        this.credentialsMapper = credentialsMapper;
        this.usersMapper = usersMapper;
    }

    public Credentials getCredential(Integer userID) {
        return credentialsMapper.getCredential(userID);
    }

    public String[] getAllCredentials(Integer userID) {
        return credentialsMapper.getAllCredentialsForUserID(userID);
    }

    public void insertCredential(String url, String username, String key,
                                 String password, Integer userID){
        Credentials credential = new Credentials(0, url, username,
                key, password, userID);
        credentialsMapper.insertCredential(credential);
    }

    public void deleteCredential(Integer credentialID) {
        credentialsMapper.deleteCredential(credentialID);
    }

    public void updateCredential(Integer credentialID, String url,
                                 String key, String password, String username) {
        credentialsMapper.updateCredential(credentialID, username, url, key, password);
    }
}
