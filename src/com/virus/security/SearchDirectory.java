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
    private List<String> filters = new ArrayList<>();

    public SearchDirectory()
    {
        initFilter();
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

        System.out.println("디렉토리 검색 시작");
        System.out.println(defaultDirectiory);
        subDirList(defaultDirectiory);
        System.out.println("디렉토리 검색 끝");
    }

    /**
     * 검색후 추가할 파일의 타입반 추출
     */
    private void initFilter()
    {
        filters.add("js");
        filters.add("java");
        filters.add("dat");
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
                if (!file.getName().equals("Guest") && !file.getName().equals("Shared"))
                {
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
//        System.out.println(FilenameUtils.getExtension(file.getName()));
        if(this.filters.contains(FilenameUtils.getExtension(file.getName())))
        {
            return true;
        }
        return false;
    }

}
