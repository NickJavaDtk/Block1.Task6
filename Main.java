package Study.streamIO.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static int i = 0;

    public static void main(String[] args) {
        GameProgress archer = new GameProgress(12, 13, 17, 24.2);
        GameProgress warrior = new GameProgress(22, 9, 17, 7.1);
        GameProgress wizard = new GameProgress(15, 18, 17, 17.6);
        String path = "D:/Users/GitTest/Study/Block1/streamIO/Games/savegames/save";
        String pathZip = "D:/Users/GitTest/Study/Block1/streamIO/Games/savegames/save.zip";
        String pathDirectory = "D:/Users/GitTest/Study/Block1/streamIO/Games/savegames/";
        List<GameProgress> listGames = new ArrayList<>( );
        listGames.add(archer);
        listGames.add(warrior);
        listGames.add(wizard);

        for (GameProgress listGame : listGames) {
            saveGame(listGame, Main.getGenerationString(path));
        }

        addSlowInitialization(pathZip, pathDirectory);
        deleteSaveFiles(pathDirectory);
    }

    public static void saveGame(GameProgress gameProgress, String path) {
        try (FileOutputStream fos = new FileOutputStream(path, true);
             ObjectOutputStream ous = new ObjectOutputStream(fos)) {
            ous.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace( );
        }

    }

    public static void zipFiles(String pathDirectory, ZipOutputStream zios) {
        File listFile = new File(pathDirectory);
        List<String> fileList = Arrays.asList(listFile.list( ));
        for (String s : fileList) {
            if (!s.endsWith(".zip")) {
                String sTmp = pathDirectory + s;
                try (FileInputStream fis = new FileInputStream(sTmp)) {
                    ZipEntry zipEntry = new ZipEntry(s);
                    zios.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available( )];
                    fis.read(buffer);
                    zios.write(buffer);
                    zios.closeEntry( );
                } catch (FileNotFoundException e) {
                    e.printStackTrace( );
                } catch (IOException e) {
                    e.printStackTrace( );
                }
            }

        }
    }

    public static String getGenerationString(String getPath) {
        char ch = getPath.charAt(getPath.length( ) - 1);
        if (Character.isDigit(ch)) {
            int c = 1 + i;
            i++;
            return getPath + c + ".dat";
        } else {
            int c = 1 + i;
            i++;
            return getPath + c + ".dat";
        }

    }

    public static void addSlowInitialization(String pathZip, String pathDirectory) {
        try (FileOutputStream fos = new FileOutputStream(pathZip);
             ZipOutputStream zios = new ZipOutputStream(fos)) {
            zipFiles(pathDirectory, zios);
        } catch (FileNotFoundException e) {
            e.printStackTrace( );
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public static void deleteSaveFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles( );
        for (File fileList : files) {
            String tmp = String.valueOf(fileList);
            if (!tmp.endsWith(".zip")) {
                fileList.deleteOnExit( );
            }
        }
    }
}
