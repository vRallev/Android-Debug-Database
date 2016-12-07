package com.amitshekhar.utils;

import android.content.Context;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright 2016 Evernote Corporation. All rights reserved.
 *
 * Created by rwondratschek on 12/7/16.
 */

public final class FileUtils {

    private FileUtils() {
        // no op
    }

    public static List<String> getAllDirs(Context context) {
        File root = context.getFilesDir().getParentFile();
        String absolutePath = root.getAbsolutePath();

        List<String> result = new ArrayList<>();
        getAllDirs(root, absolutePath.length(), result);

        Collections.sort(result);

        return result;
    }

    private static void getAllDirs(final File dir, final int beginIndex, final List<String> result) {
        File[] files = dir.listFiles();

        boolean addDir = files.length > 0;
        if (addDir) {
            addDir = false;
            for (File file : files) {
                if (file.isFile()) {
                    addDir = true;
                    break;
                }
            }

            if (addDir) {
                result.add(dir.getAbsolutePath().substring(beginIndex));
            }
        }

        for (File file : files) {
            if (file.isDirectory()) {
                getAllDirs(file, beginIndex, result);
            }
        }
    }

    public static List<String> getAllFiles(File dir) {
        List<String> result = new ArrayList<>();

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        for (File file : files) {
            result.add(file.getAbsolutePath());
        }
        return result;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] readFile(String path) {
        FileInputStream stream = null;
        try {
            File file = new File(path);
            stream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            stream.read(data);
            return data;

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return new byte[0];
    }
}
