package com.sunwise.convert;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Convert2CUtil {
    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("convert", CLibrary.class);

        void convertFiles(String[] sourcePath, String[] targetPath,int fileCount);
    }
}
