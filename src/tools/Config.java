package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Jérémy Chaut
 */
public class Config {

    private BufferedWriter bufferw;
    private Properties p;
    private File fconf;
    private String[] conf;
    private String name;

    public Config() {
        build("config.properties", null);
    }

    public Config(String s, String[] c) {
        build(s, c);
    }

    private void build(String s, String[] c) {
        name = s;
        conf = c;
        p = new Properties();
        try {
            fconf = new File(name);
            FileInputStream in = new FileInputStream(fconf);
            p.load(in);
            in.close();
        } catch (Exception e) {
            createConfig(name, conf);
        }
    }

    public void createConfig() {
        createConfig(null, null);
    }

    public void createConfig(String name, String[] conf) {
        if (conf == null) {
            fconf = new File(name);
            if (!fconf.exists()) {
                try {
                    bufferw = new BufferedWriter(new FileWriter(name));
                    bufferw.write("#header");
                    bufferw.newLine();
                    bufferw.write("#");
                    bufferw.newLine();
                    bufferw.write("config.skin=skin/aquathemepack.zip");
                    bufferw.newLine();
                    bufferw.write("config.panel=0");
                    bufferw.newLine();
                    bufferw.write("config.dir=0");
                    bufferw.newLine();
                    bufferw.write("config.mail=0");
                    bufferw.newLine();
                    bufferw.write("config.Rdir=0");
                    bufferw.newLine();
                    bufferw.write("config.Rmail=0");
                    bufferw.newLine();
                    bufferw.write("config.Rdb=0");
                    bufferw.newLine();
                    bufferw.write("config.smtp=");
                    bufferw.newLine();
                    bufferw.write("config.lang=" + System.getProperty("user.language"));
                    bufferw.newLine();
                    bufferw.flush();
                    bufferw.close();
                } catch (IOException ioe) {
                }
            }
        } else {
            fconf = new File(name);
            if (!fconf.exists()) {
                try {
                    if (fconf.createNewFile()) {
                        bufferw = new BufferedWriter(new FileWriter(name));
                        bufferw.write("#header");
                        bufferw.newLine();
                        for (int i = 0; i < conf.length; i++) {
                            bufferw.write(conf[i]);
                            bufferw.newLine();
                        }
                        bufferw.flush();
                        bufferw.close();
                    }
                } catch (IOException ioe) {
                    System.err.println(ioe.getMessage());
                }
            }

        }
        build(name, conf);
    }

    public Properties getConfig() {
        return p;
    }

    public void setConfig(String key, String value) {
        try {
            FileInputStream in = new FileInputStream(fconf);
            FileOutputStream outfile = new FileOutputStream(fconf);
            p.load(in);
            in.close();
            p.setProperty(key, value);
            p.store(outfile, "header");
            outfile.close();

        } catch (Exception e) {
        }
    }

    public File getFileConf() {
        return fconf;
    }

    public void setFileConf(File fconf) {
        this.fconf = fconf;
    }

    public String[] getListConf() {
        return conf;
    }

    public void setListConf(String[] conf) {
        this.conf = conf;
    }
    
    
}