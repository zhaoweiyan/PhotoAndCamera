package com.mygit.photoandcamera.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2017/3/30.
 */

public class SDUtils {

    private static File file;
    private static String RootName = "MyGit";
    private static String CorpDirName = "crop";
    public static String AppDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + RootName + File.separator;
    public static String AppCorpDirPath = AppDirPath + CorpDirName + File.separator;

    public static String copyToSD(Context context, int ID, String name) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            String path = android.os.Environment.getExternalStorageDirectory()
                    .getPath();
            path = path + "/logo";

            String dbPathAndName = path + "/" + name;

            file = new File(path);

            if (file.exists() == false) {
                file.mkdir();
            }

            File dbFile = new File(dbPathAndName);
            if (!dbFile.exists()) {
                is = context.getResources().openRawResource(
                        ID);
                fos = new FileOutputStream(dbFile);

                byte[] buffer = new byte[8 * 1024];// 8K
                while (is.read(buffer) > 0)// >
                {
                    fos.write(buffer);
                }
            }
            return dbPathAndName;
        } catch (Exception e) {
            return "";
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
