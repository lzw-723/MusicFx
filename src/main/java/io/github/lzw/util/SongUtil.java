package io.github.lzw.util;

import io.github.lzw.bean.Song;
import io.github.lzw.bean.SongS;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class SongUtil {
    public static ArrayList<Song> getSongs(File dir){
        File ser = new File(new File(".").getPath(), "songs.ser");
        System.out.println(ser.length());
        if (ser.exists() && ser.length() > 0) {
            return read(ser);
        }
        else {
            try {
                ser.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Song> list = new ArrayList<>();
        if(dir.exists() && dir.listFiles().length > 0)
        for (File file : dir.listFiles()) {
            String path = file.getAbsolutePath().toLowerCase();
            if (path.endsWith(".mp3") || path.endsWith(".wav") || path.endsWith(".wma"))
                try {
                    list.add(new Song(file.toPath().toUri().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            System.out.println(file);
        }
        save(list, ser);
        return list;
    }

    private static void save(ArrayList<Song> list, File file) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Song2songs(list));
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    private static ArrayList<Song> read(File file){
        ArrayList<SongS> lists = new ArrayList<>();
        try
        {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            lists = (ArrayList<SongS>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return null;
        }
        return SongS2song(lists);
    }
    private static ArrayList<SongS> Song2songs(ArrayList<Song> list){
        ArrayList<SongS> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            lists.add(new SongS(list.get(i)));
        }
        return lists;
    }
    private static ArrayList<Song> SongS2song(ArrayList<SongS> lists){
        ArrayList<Song> list = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            list.add(new Song(lists.get(i)));
        }
        return list;
    }
}
