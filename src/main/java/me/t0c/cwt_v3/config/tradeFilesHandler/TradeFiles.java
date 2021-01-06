package me.t0c.cwt_v3.config.tradeFilesHandler;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradestorage.Category;
import me.t0c.cwt_v3.tradestorage.CategoryTrades;
import me.t0c.cwt_v3.utils.StringParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class TradeFiles extends ConfigHandler {

    private Set<File> tradeFiles;
    File file;

    public TradeFiles(JavaPlugin plugin, String folder) {
        super(plugin, folder);
        file = new File(prefix);

        if(file.listFiles((d, s) -> StringParser.YAML_FILE.isStringType(s)) != null) {
            tradeFiles = new HashSet<>(Arrays.asList(Objects.requireNonNull(file.listFiles((d, s) -> StringParser.YAML_FILE.isStringType(s)))));

        }
        else {
            tradeFiles = new HashSet<>();
        }
    }

    public Map<Category, CategoryTrades> loadConfig() {
        Map<Category, CategoryTrades> trades = new HashMap<>();
        for(File file : tradeFiles) {
            trades.put(new Category(file.getName()), getConfig(CategoryTrades.class, file.getName()));
        }
        return trades;
    }

    public void saveAll() {
        for(Category category : CWT_v3.trades.getTrades().keySet()) {
            this.saveFile(CWT_v3.trades.getTrades().get(category), category.getFileName());
        }
    }

    public void saveConfig(Category category) {
        if(StringParser.YAML_FILE.isStringType(category.getFileName())) {
            this.saveFile(CWT_v3.trades.getTrades().get(category), category.getFileName());
        } else { this.saveFile(CWT_v3.trades.getTrades().get(category), category.getFileName() + ".yml"); }
    }

    public <T> void saveFile(T object, String name) {
        this.saveConfig(object, prefix + name + File.separator);
    }

    /* DEFAULTS */

    @Override
    public <T> void saveConfig(T object, String path) {
        try{
            this.saveYaml(object, path);
        } catch(IOException e){
            logger.warning("Couldn't save config file");
        }
    }

    /* DATA FOLDER */

    @Override
    <T> void saveYaml(T object, String path) throws IOException {
        if(object == null) return;
        File file = new File(path);
        createFile(file);
        Yaml yaml = genYaml(object.getClass());
        Writer writer = new FileWriter(file);
        writer.write(yaml.dumpAsMap(object));
        writer.close();
    }

    public Set<File> getTradeFiles() { return tradeFiles; }
}