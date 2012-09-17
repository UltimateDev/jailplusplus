package tk.ultimatedev.jailplusplus.util.file;

import java.io.File;

/**
 * @author YoshiGenius
 */
public class FilePaths {
    
    public String getFolderPath() {
        return "plugins/JailPlusPlus/";
    }
    
    public String getUserdataFolderPath() {
        return (getFolderPath() + "userdata/");
    }
    
    public String getJailsFolderPath() {
        return (getFolderPath() + "jails/");
    }
    
    public File getUserYAMLFile(String playername) {
        File file = new File(getUserdataFolderPath() + playername + ".yml");
        return file;
    }
    
    public File getJailYAMLFile(String jailname) {
        File file = new File(getJailsFolderPath() + jailname + ".yml");
        return file;
    }
    
}
