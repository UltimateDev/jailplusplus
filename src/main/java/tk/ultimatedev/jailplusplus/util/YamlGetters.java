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
    
    // savers
    
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
    
    // setters

    public void setPrisonersEntry(String playername, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getPrisonersPath(playername + subpath);
        YamlConfiguration c = FilePaths.getInstance().getPrisonersFileConf();
        c.set(path, newentry);
        this.savePrisonersFile();
    }

    public void setJailsEntry(String jailname, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getJailPath(jailname + subpath);
        YamlConfiguration c = FilePaths.getInstance().getJailFileConf();
        c.set(path, newentry);
        this.saveJailsFile();
    }
    
    public void setCellsEntry(int jid, int cid, String subpath, Object newentry) {
        String path = FilePaths.getInstance().getCellPath(jid, cid);
        YamlConfiguration c = FilePaths.getInstance().getCellFileConf();
        c.set(path + subpath, newentry);
        this.saveCellsFile();
    }
    
    // getters
    
    // strings

    public String getPrisonersString(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public String getJailString(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public String getCellString(int jid, int cid, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getString(FilePaths.getInstance().getCellPath(jid, cid) + subpath);
    }
    
    // ints

    public int getPrisonersInt(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public int getJailInt(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public int getCellInt(int jid, int cid, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getInt(FilePaths.getInstance().getCellPath(jid, cid) + subpath);
    }
    
    // booleans

    public boolean getPrisonersBool(String playername, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getPrisonersPath(playername) + subpath);
    }

    public boolean getJailBool(String jailname, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getJailPath(jailname) + subpath);
    }
    
    public boolean getCellBool(int jid, int cid, String subpath) {
        return FilePaths.getInstance().getPrisonersFileConf().getBoolean(FilePaths.getInstance().getCellPath(jid, cid) + subpath);
    }
    
}