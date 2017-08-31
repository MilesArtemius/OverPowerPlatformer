package greensun.engine_support.every_day_singles;

import greensun.engine_support.Depacker;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class MediaStorage {
    private static MediaStorage single;

    private static HashMap<String, Image> TextureSet = new HashMap<>();
    private static HashMap<String, Image> BackgroundSet = new HashMap<>();
    private static HashMap<String, Media> SoundSet = new HashMap<>();

    public HashMap<String, Image> getTextures() {
        return TextureSet;
    }
    public HashMap<String, Image> getBackgrounds() {
        return BackgroundSet;
    }
    public HashMap<String, Media> getSounds() {
        return SoundSet;
    }

    public static MediaStorage get() {
        if (single == null) {
            TextureSet = new HashMap<>();
            BackgroundSet = new HashMap<>();
            SoundSet = new HashMap<>();
            return single = new MediaStorage("null"); //TODO: revise!
        } else {
            return single;
        }
    }

    public static MediaStorage init(String filepath) {
        if (single == null) {
            TextureSet = new HashMap<>();
            BackgroundSet = new HashMap<>();
            SoundSet = new HashMap<>();
            return single = new MediaStorage(filepath);
        } else {
            return single = new MediaStorage(filepath);
        }
    }

    private MediaStorage(String filepath) {
        if (filepath.equals("null")) {
            TextureSet.putAll(getStartedImages("textures"));
            BackgroundSet.putAll(getStartedImages("backgrounds"));
        } else {
            TextureSet.putAll(getStartedImages(filepath + "&textures/"));
            BackgroundSet.putAll(getStartedImages(filepath + "&textures/entities/"));
            SoundSet.putAll(getStartedMedia(filepath + "&musics/"));
        }
    }

    public HashMap<String, Media> getStartedMedia(String path) {
        HashMap<String, Media> hm = new HashMap<>();
        for (Map.Entry<String, File> entry: Depacker.getStartedFiles(getClass(), path).entrySet()) {
            hm.put(entry.getKey(), new Media(entry.getValue().toURI().toString()));
        }
        return hm;
    }

    public HashMap<String, Image> getStartedImages(String path) {
        HashMap<String, Image> hm = new HashMap<>();
        for (Map.Entry<String, File> entry: Depacker.getStartedFiles(getClass(), path).entrySet()) {
            try {
                hm.put(entry.getKey(), new Image(Files.newInputStream(entry.getValue().toPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return hm;
    }
}