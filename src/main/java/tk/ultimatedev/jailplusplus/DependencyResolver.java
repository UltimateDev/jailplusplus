package tk.ultimatedev.jailplusplus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Set;

public class DependencyResolver {
    boolean cb;
    Set<Dependency> dependencies = new HashSet<Dependency>();

    public DependencyResolver(boolean cb) {
        this.cb = cb;

        if (cb) {
            File libFolder = new File("lib");

            if (!libFolder.exists()) {
                if (!libFolder.mkdirs()) {
                    System.out.println("I couldn't create the JailPlusPlus libraries folder!");
                }
            }
        } else {
            File libFolder = new File("../lib");

            if (!libFolder.exists()) {
                if (!libFolder.mkdirs()) {
                    System.out.println("I couldn't create the JailPlusPlus libraries folder!");
                }
            }
        }
    }

    public enum Dependency {
        JRUBY, H2, SQLITE, MYSQL, GSON
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public void resolve() {
        if (dependencies.size() > 0) {
            System.out.println("I need to download dependencies for Jail++! This should only take a moment...");
            System.out.println();
        }

        for (Dependency dependency: dependencies) {
            resolve(dependency);
        }
    }

    public void resolve(Dependency dependency) {
        if (cb) {
            switch (dependency) {
                case JRUBY:
                    if (!((new File("lib/jruby-1.7.0.preview2.jar")).exists())) {
                        get("lib/jruby-1.7.0.preview2.jar", "http://search.maven.org/remotecontent?filepath=org/jruby/jruby/1.7.0.preview2/jruby-1.7.0.preview2.jar");
                    }
                    break;
                case H2:
                    if (!((new File("lib/h2-1.3.168.jar")).exists())) {
                        get("lib/h2-1.3.168.jar", "http://search.maven.org/remotecontent?filepath=com/h2database/h2/1.3.168/h2-1.3.168.jar");
                    }
                    break;
                case SQLITE:
                    if (!((new File("lib/sqlite-jdbc-3.7.2.jar")).exists())) {
                        get("lib/sqlite-jdbc-3.7.2.jar", "http://search.maven.org/remotecontent?filepath=org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar");
                    }
                    break;
                case MYSQL:
                    if (!((new File("lib/mysql-connector-java-5.1.21.jar")).exists())) {
                        get("lib/mysql-connector-java-5.1.21.jar", "http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/5.1.21/mysql-connector-java-5.1.21.jar");
                    }
                    break;
                case GSON:
                    if (!((new File("lib/gson-2.2.2.jar")).exists())) {
                        get("lib/gson-2.2.2.jar", "http://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.2.2/gson-2.2.2.jar");
                    }
                    break;
            }
        } else {
            switch (dependency) {
                case JRUBY:
                    if (!((new File("../lib/jruby-1.7.0.preview2.jar")).exists())) {
                        get("../lib/jruby-1.7.0.preview2.jar", "http://search.maven.org/remotecontent?filepath=org/jruby/jruby/1.7.0.preview2/jruby-1.7.0.preview2.jar");
                    }
                    break;
                case H2:
                    if (!((new File("../lib/h2-1.3.168.jar")).exists())) {
                        get("../lib/h2-1.3.168.jar", "http://search.maven.org/remotecontent?filepath=com/h2database/h2/1.3.168/h2-1.3.168.jar");
                    }
                    break;
                case SQLITE:
                    if (!((new File("../lib/sqlite-jdbc-3.7.2.jar")).exists())) {
                        get("../lib/sqlite-jdbc-3.7.2.jar", "http://search.maven.org/remotecontent?filepath=org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar");
                    }
                    break;
                case MYSQL:
                    if (!((new File("../lib/mysql-connector-java-5.1.21.jar")).exists())) {
                        get("../lib/mysql-connector-java-5.1.21.jar", "http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/5.1.21/mysql-connector-java-5.1.21.jar");
                    }
                    break;
                case GSON:
                    if (!((new File("../lib/gson-2.2.2.jar")).exists())) {
                        get("../lib/gson-2.2.2.jar", "http://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.2.2/gson-2.2.2.jar");
                    }
                    break;
            }
        }
    }

    public void get(String artifactPath, String artifactURL) {
        try {
            System.out.println("Downloading file " + artifactPath + "...");

            URL artifact = new URL(artifactURL);
            ReadableByteChannel readableByteChannel = Channels.newChannel(artifact.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(artifactPath);

            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 1 << 24);
        } catch (MalformedURLException ex) {
            System.out.println("I was unable to download the dependency " + artifactPath);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            System.out.println("I was unable to download the dependency " + artifactPath);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("I was unable to download the dependency " + artifactPath);
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("I was unable to download the dependency " + artifactPath);
            ex.printStackTrace();
        }
    }
}
