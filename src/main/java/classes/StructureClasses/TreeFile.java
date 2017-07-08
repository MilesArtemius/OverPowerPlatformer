package classes.StructureClasses;

import java.io.File;

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

    public void setViewName(String name) {
        this.viewName = name;
    }
}
