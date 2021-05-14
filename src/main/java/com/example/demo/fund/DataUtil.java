package com.example.demo.fund;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataUtil {
    public static Entity get(String fileUrl) throws IOException {
        File file = new File(fileUrl);
        StringBuffer stringBuffer = new StringBuffer();
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] temp = new char[1024];
            int byteread = 0;
            while ((byteread = reader.read(temp)) != -1) {
                stringBuffer.append(temp);
                temp = new char[1024];
            }
            Gson gson = new Gson();
            Entity entity = gson.fromJson(stringBuffer.toString().trim(), Entity.class);
            return entity;
        }finally {
            if (null!=reader) reader.close();
        }
    }
}
