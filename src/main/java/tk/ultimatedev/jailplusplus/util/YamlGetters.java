package tk.ultimatedev.jailplusplus.util;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;

/**
 * @author YoshiGenius
 */
public class YamlGetters {
    
    private static YamlGetters instance;
    
    public static YamlGetters getInstance() {
        return YamlGetters.instance;
    }
    
    public void setPrisonersEntry(String playername, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getPrisonersPath(playername + subpath);
        YamlConfiguration c = FilePaths.getInstance().getPrisonersFileConf();
        c.set(path, newentry);
        this.savePrisonersFile();
    }
    
    public void savePrisonersFile() {
        File f = FilePaths.getInstance().getPrisonersFile();
        YamlConfiguration c = FilePaths.getInstance().getPrisonersFileConf();
        try {
            c.save(f);
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
        }
    }
    
    public void setJailsEntry(String jailname, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getJailPath(jailname + subpath);
        YamlConfiguration c = FilePaths.getInstance().getJailFileConf();
        c.set(path, newentry);
        this.saveJailsFile();
    }
    
    public void saveJailsFile() {
        File f = FilePaths.getInstance().getJailsFile();
        YamlConfiguration c = FilePaths.getInstance().getJailFileConf();
        try {
            c.save(f);
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
        }
    }
    
    public void setCellsEntry(String jailname, String cellname, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getCellPath(jailname, cellname);
        YamlConfiguration c = FilePaths.getInstance().getCellFileConf();
        c.set(path + subpath, newentry);
        this.saveCellsFile();
    }
    
    public void saveCellsFile() {
        File f = FilePaths.getInstance().getCellsFile();
        YamlConfiguration c = FilePaths.getInstance().getCellFileConf();
        try {
            c.save(f);
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
        }
    }
    
    public String getPrisonersString(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public String getJailString(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public String getCellString(String jailname, String cellname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getCellPath(jailname, cellname) + subpath);
    }
    
    public int getPrisonersInt(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public int getJailInt(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public int getCellInt(String jailname, String cellname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getCellPath(jailname, cellname) + subpath);
    }
    
    public boolean getPrisonersBool(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public boolean getJailBool(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public boolean getCellBool(String jailname, String cellname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getCellPath(jailname, cellname) + subpath);
    }

}
