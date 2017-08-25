package greensun.StructureClasses;

import com.google.common.io.Files;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HP on 04.07.2017.
 */
public class TreeFile extends File {
    String viewName;

    public TreeFile(String pathname) {
        super(pathname);
        this.viewName = "NoName";
    }

    @Override
    public String toString() {
        return viewName;
    }

    public TreeFile(String pathname, String viewName) {
        super(pathname);
        this.viewName = viewName;
    }

    public boolean isLevel() {
        try {
            if ((this.canRead()) && (this.isDirectory()) && (this.listFiles().length > 0)) {
                ArrayList<String> files = new ArrayList<>();
                boolean levelCounter = false;
                for (File file : this.listFiles()) {
                    files.add(file.getName());
                    if ((file.isDirectory()) && (file.getName().equals("levels"))) {
                        for (File level : file.listFiles()) {
                            if (Files.getFileExtension(level.getName()).equals("upson")) {
                                levelCounter = true;
                            }
                        }
                    }
                }
                if ((files.contains("levels")) &&
                        (files.contains("textures")) &&
                        (files.contains("blockset.json")) &&
                        (files.contains("config.json")) &&
                        levelCounter) {
                    return true;
                }
            } else {
                return false;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isImage() {
        if ((this.isFile()) && (Files.getFileExtension(this.getName()).equals("png"))) {
            return true;
        } else {
            return false;
        }
    }

    public void setViewName(String name) {
        this.viewName = name;
    }
}
