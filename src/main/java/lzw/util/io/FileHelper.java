package lzw.util.io;

import java.io.*;

public class FileHelper {
    public static void read(String path, FileReaderCallBack cb) {
        read(new File(path), cb);
    }

    public static void read(final File file, final FileReaderCallBack callback) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    InputStream is = new FileInputStream(file);
                    try {
                        byte[] buffer = new byte[(int) file.length()];
                        is.read(buffer);
                        callback.onBack(buffer);
                    } catch (IOException e) {
                        callback.onError(e);
                    }
                } catch (FileNotFoundException e) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public static void write(String path, byte[] res, FileWriterCallBack cb) {
        write(new File(path), res, cb);
    }

    public static void write(final File file, final byte[] res, final FileWriterCallBack callback) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    if (!file.exists()) file.createNewFile();
                    FileOutputStream output = new FileOutputStream(file);
                    try {
                        output.write(res);
                        output.close();
                    } catch (Exception e) {
                        callback.onError(e);
                    }

                } catch (Exception e) {
                    callback.onError(e);
                }
            }

        };
        runnable.run();
    }

    public interface FileReaderCallBack {
        void onBack(byte[] bk);

        void onError(Exception e);
    }

    public interface FileWriterCallBack {
        void onBack();

        void onError(Exception e);
    }
}
