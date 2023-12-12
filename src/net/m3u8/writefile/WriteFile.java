package net.m3u8.writefile;

import net.m3u8.utils.MyUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WriteFile {

    public static void writefile(String path,String s){
        try {
            File file = new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            Files.write(file.toPath(), s.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            Files.write(file.toPath(), "\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
