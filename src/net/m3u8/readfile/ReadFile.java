package net.m3u8.readfile;

import net.m3u8.dto.FileInfo;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public static List<FileInfo> readfile(String path){
        List<FileInfo> relist = new ArrayList<>();
        try {
            File file = new File(path);
            List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                if(s.startsWith("http")){
                    String url = s;
                    s = list.get(i-1);
                    String[] arr = s.split("group-title=");
                    String name = arr[1].replace("\"","").replace(",","-");
                    System.out.println(name + "      "+url);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setName(name);
                    fileInfo.setUrl(url);
                    relist.add(fileInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return relist;
    }

}
