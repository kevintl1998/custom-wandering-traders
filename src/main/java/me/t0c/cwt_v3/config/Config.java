package me.t0c.cwt_v3.config;

import me.t0c.cwt_v3.utils.StringParser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    private static final int DESPAWN_TIMER_HARD_CAP = Integer.MAX_VALUE;
    private static final int DEFAULT_TRADES_AMOUNT_MAX = 6;

    private static final int DEFAULT_CATEGORY_MIN_TRADES_DEFAULT = 1;
    private static final int DEFAULT_CATEGORY_MAX_TRADES_DEFAULT = 6;
    private static final int MIXED_TRADER_MIN_TRADES_DEFAULT = 1;
    private static final int MIXED_TRADER_MAX_TRADES_DEFAULT = 10;
    private static final int DESPAWN_TIMER_DEFAULT = 20;
    private static final boolean PREVENT_DUPLICATE_TRADES_DEFAULT = false;
    private static final boolean REMOVE_LLAMAS_ON_TRADER_DESPAWN_DEFAULT = false;
    private static final boolean BROADCAST_TRADER_SPAWN_DEFAULT = false;
    private static final boolean LEAK_TRADER_COORDS_DEFAULT = false;
    private static final boolean MIX_TRADER_CATEGORIES_DEFAULT = false;
    private static final boolean KEEP_DEFAULT_TRADES_DEFAULT = true;
    private static final boolean COMBINE_DEFAULT_AND_CUSTOM = false;
    private static final int DEFAULT_TRADES_AMOUNT_DEFAULT = 6;
    private static final String APPEND_DEFAULT_OR_CUSTOM_DEFAULT = "custom";
    private static final int DEFAULT_CATEGORY_WEIGHT_DEFAULT = 10;

    private Config() {}

    private static JavaPlugin plugin;
    private static FileConfiguration file;

    private static int defaultCategoryMinTrades = DEFAULT_CATEGORY_MIN_TRADES_DEFAULT;
    private static int defaultCategoryMaxTrades = DEFAULT_CATEGORY_MAX_TRADES_DEFAULT;
    private static int mixedTraderMinTrades = MIXED_TRADER_MIN_TRADES_DEFAULT;
    private static int mixedTraderMaxTrades = MIXED_TRADER_MAX_TRADES_DEFAULT;
    private static int despawnTimer = DESPAWN_TIMER_DEFAULT;
    private static int defaultCategoryWeight = DEFAULT_CATEGORY_WEIGHT_DEFAULT;
    private static boolean preventDuplicateTrades = PREVENT_DUPLICATE_TRADES_DEFAULT;
    private static boolean removeLlamasOnTraderDespawn = REMOVE_LLAMAS_ON_TRADER_DESPAWN_DEFAULT;
    private static boolean broadcastTraderSpawn = BROADCAST_TRADER_SPAWN_DEFAULT;
    private static boolean leakTraderCoords = LEAK_TRADER_COORDS_DEFAULT;
    private static boolean mixTraderCategories = MIX_TRADER_CATEGORIES_DEFAULT;

    private static boolean keepDefaultTrades = KEEP_DEFAULT_TRADES_DEFAULT;
    private static boolean combineDefaultAndCustom = COMBINE_DEFAULT_AND_CUSTOM;
    private static int defaultTradesAmount = DEFAULT_TRADES_AMOUNT_DEFAULT;
    private static String appendDefaultOrCustom = APPEND_DEFAULT_OR_CUSTOM_DEFAULT;

    public static void loadConfig(JavaPlugin plugin) {
        Config.plugin = plugin;
        Config.file = plugin.getConfig();

        setDefaultCategoryMinTrades();
        setDefaultCategoryMaxTrades();
        setMixedTraderMinTrades();
        setMixedTraderMaxTrades();
        setDespawnTimer();
        setPreventDuplicateTrades();
        setRemoveLlamasOnTraderDespawn();
        setBroadcastTraderSpawn();
        setLeakTraderCoords();
        setMixTraderCategories();
        setDefaultCategoryWeight();
        setKeepDefaultTrades();
        setCombineDefaultAndCustom();
        setDefaultTradesAmount();
        setAppendDefaultOrCustom();

        checkConfigValues();
    }

    /* used by this class to load the config on start */

    private static void setDefaultCategoryMinTrades() { defaultCategoryMinTrades = file.getInt("defaultCategoryMinTrades"); }
    private static void setDefaultCategoryMaxTrades() { defaultCategoryMaxTrades = file.getInt("defaultCategoryMaxTrades"); }
    private static void setMixedTraderMinTrades() { mixedTraderMinTrades = file.getInt("mixedTraderMinTrades"); }
    private static void setMixedTraderMaxTrades() { mixedTraderMaxTrades = file.getInt("mixedTraderMaxTrades"); }
    // if this is set to -1 then there is no despawn timer
    private static void setDespawnTimer() { despawnTimer = file.getInt("despawnTimer"); }
    private static void setDefaultCategoryWeight() { defaultCategoryWeight = file.getInt("defaultCategoryWeight"); }
    private static void setPreventDuplicateTrades() { preventDuplicateTrades = file.getBoolean("preventDuplicateTrades"); }
    private static void setRemoveLlamasOnTraderDespawn() { removeLlamasOnTraderDespawn = file.getBoolean("removeLlamasOnTraderDespawn"); }
    private static void setBroadcastTraderSpawn() { broadcastTraderSpawn = file.getBoolean("broadcastTraderSpawn"); }
    private static void setLeakTraderCoords() { leakTraderCoords = file.getBoolean("leakTraderCoords"); }
    private static void setMixTraderCategories() { mixTraderCategories = file.getBoolean("mixTraderCategories"); }
    private static void setKeepDefaultTrades() { keepDefaultTrades = file.getBoolean("keepDefaultTrades"); }
    private static void setCombineDefaultAndCustom() { combineDefaultAndCustom = file.getBoolean("combineDefaultAndCustom"); }
    private static void setDefaultTradesAmount() { defaultTradesAmount = file.getInt("defaultTradesAmount"); }
    private static void setAppendDefaultOrCustom() { appendDefaultOrCustom = file.getString("appendTradesOrCustom"); }

    private static void checkConfigValues() {

        if(defaultCategoryMinTrades <= 0) {

            defaultCategoryMinTrades = DEFAULT_CATEGORY_MIN_TRADES_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: defaultCategoryMinTrades must be greater than 0");
        }

        if(defaultCategoryMaxTrades < defaultCategoryMinTrades) {

            defaultCategoryMaxTrades = defaultCategoryMinTrades;

            Config.plugin.getLogger().warning("Error in config file: defaultCategoryMaxTrades must be greater than/equal to defaultCategoryMinTrades.");
        }

        if(mixedTraderMinTrades < 0) {

            mixedTraderMinTrades = MIXED_TRADER_MIN_TRADES_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: mixedTraderMinTrades must be greater than 0.");
        }

        if(mixedTraderMaxTrades < mixedTraderMinTrades) {

            mixedTraderMaxTrades = mixedTraderMinTrades;

            Config.plugin.getLogger().warning("Error in config file: mixedTraderMaxTrades must be greater than/equal to mixedTraderMinTrades.");
        }

        // despawn timer    || keeping this here just in case I want to change the hard cap
        if(despawnTimer < 1 || despawnTimer > DESPAWN_TIMER_HARD_CAP) {

            despawnTimer = DESPAWN_TIMER_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: despawnTimer must either be -1 or from 1 to " + DESPAWN_TIMER_HARD_CAP + ".");
        }

        // default category weight
        if(defaultCategoryWeight < 1) {

            defaultCategoryWeight = DEFAULT_CATEGORY_WEIGHT_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: defaultCategoryWeight must be greater than 0.");
        }

        if(defaultTradesAmount > DEFAULT_TRADES_AMOUNT_MAX || defaultTradesAmount < 0) {

            defaultTradesAmount = DEFAULT_TRADES_AMOUNT_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: defaultTradesAmount must be less than/equal to 6 or greater than 0.");
        }

        if(!appendDefaultOrCustom.equals("trades") && !appendDefaultOrCustom.equals("custom")) {

            appendDefaultOrCustom = APPEND_DEFAULT_OR_CUSTOM_DEFAULT;

            Config.plugin.getLogger().warning("Error in config file: appendTradesOrCustom must be either 'trades' or 'custom'");
        }
    }

    public static void saveConfig() {
        file.set("defaultCategoryMinTrades", defaultCategoryMinTrades);
        file.set("defaultCategoryMaxTrades", defaultCategoryMaxTrades);
        file.set("mixedTraderMinTrades", mixedTraderMinTrades);
        file.set("mixedTraderMaxTrades", mixedTraderMaxTrades);
        file.set("despawnTimer", despawnTimer);
        file.set("defaultCategoryWeight", defaultCategoryWeight);
        file.set("preventDuplicateTrades", preventDuplicateTrades);
        file.set("removeLlamasOnTraderDespawn", removeLlamasOnTraderDespawn);
        file.set("broadcastTraderSpawn", broadcastTraderSpawn);
        file.set("leakTraderCoords", leakTraderCoords);
        file.set("mixTraderCategories", mixTraderCategories);
        file.set("keepDefaultTrades", keepDefaultTrades);
        file.set("combineDefaultAndCustom", combineDefaultAndCustom);
        file.set("defaultTradesAmount", defaultTradesAmount);
        file.set("appendTradesOrCustom", appendDefaultOrCustom);
        plugin.saveConfig();
    }

    /* for all classes to use */

    public static boolean setDefaultCategoryMinTrades(String stringValue) {

        if(StringParser.POS_NONZERO_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            if(value > 0 && value <= defaultCategoryMaxTrades) {
            file.set("defaultCategoryMinTrades",defaultCategoryMinTrades);
            plugin.saveConfig();
            return true;
            }
        }
        return false;
    }

    public static boolean setDefaultCategoryMaxTrades(String stringValue) {
        if(StringParser.POS_NONZERO_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            if(value >= defaultCategoryMinTrades) {
                defaultCategoryMaxTrades = value;
                file.set("defaultCategoryMaxTrades", defaultCategoryMaxTrades);
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }

    public static boolean setMixedTraderMinTrades(String stringValue) {
        if(StringParser.POS_NONZERO_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            if(value <= mixedTraderMaxTrades) {
                mixedTraderMinTrades = Integer.parseInt(stringValue);
                file.set("mixedTraderMinTrades", mixedTraderMinTrades);
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }
    
    public static boolean setMixedTraderMaxTrades(String stringValue) {
        if(StringParser.POS_NONZERO_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            if(value >= mixedTraderMinTrades) {
                mixedTraderMaxTrades = value;
                file.set("mixedTraderMaxTrades", mixedTraderMaxTrades);
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }

    public static boolean setDespawnTimer(String stringValue) {
        if(StringParser.INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            // can be: -1 || 1 <= x < ???
            // -1 == no despawn timer
            if (value == -1 || (value > 0 && value < DESPAWN_TIMER_HARD_CAP)) {
                despawnTimer = value;
                file.set("despawnTimer", despawnTimer);
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }

    public static boolean setDefaultCategoryWeight(String stringValue) {
        if(StringParser.POS_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            // can be x > 0
            if(value > 0) {
                defaultCategoryWeight = value;
                file.set("defaultCategoryWeight", defaultCategoryWeight);
                plugin.saveConfig();
                return true;
            }

        }
        return false;
    }
    public static boolean setPreventDuplicateTrades(String stringValue) {
        if (stringValue.toLowerCase().equals("true")) {
            preventDuplicateTrades = true;
            file.set("preventDuplicateTrades", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.toLowerCase().equals("false")) {
            preventDuplicateTrades = false;
            file.set("preventDuplicateTrades", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setRemoveLlamasOnTraderDespawn(String stringValue) {
        if (stringValue.toLowerCase().equals("true")) {
            removeLlamasOnTraderDespawn = true;
            file.set("removeLlamasOnTraderDespawn", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.toLowerCase().equals("false")) {
            removeLlamasOnTraderDespawn = false;
            file.set("removeLlamasOnTraderDespawn", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setBroadcastTraderSpawn(String stringValue) {
        if (stringValue.toLowerCase().equals("true")) {
            broadcastTraderSpawn = true;
            file.set("broadcastTraderSpawn", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.toLowerCase().equals("false")) {
            broadcastTraderSpawn = false;
            file.set("broadcastTraderSpawn", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setLeakTraderCoords(String stringValue) {
        if (stringValue.toLowerCase().equals("true")) {
            leakTraderCoords = true;
            file.set("leakTraderCoords", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.toLowerCase().equals("false")) {
            leakTraderCoords = false;
            file.set("leakTraderCoords", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setMixTraderCategories(String stringValue) {
        if (stringValue.toLowerCase().equals("true")) {
            mixTraderCategories = true;
            file.set("mixTraderCategories", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.toLowerCase().equals("false")) {
            mixTraderCategories = false;
            file.set("mixTraderCategories", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setKeepDefaultTrades(String stringValue) {
        if(stringValue.toLowerCase().equals("true")) {
            keepDefaultTrades = true;
            file.set("keepDefaultTrades", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.equals("false")) {
            keepDefaultTrades = false;
            file.set("keepDefaultTrades", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setCombineDefaultAndCustom(String stringValue) {
        if(stringValue.toLowerCase().equals("true")) {
            combineDefaultAndCustom = true;
            file.set("combineDefaultAndCustom", true);
            plugin.saveConfig();
            return true;

        } else if (stringValue.equals("false")) {
            combineDefaultAndCustom = false;
            file.set("combineDefaultAndCustom", false);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public static boolean setDefaultTradesAmount(String stringValue) {
        if(StringParser.POS_NONZERO_INTEGER.isStringType(stringValue)) {
            int value = Integer.parseInt(stringValue);
            if(value <= DEFAULT_TRADES_AMOUNT_MAX) {
                defaultTradesAmount = value;
                file.set("defaultTradesAmount", defaultTradesAmount);
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }

    public static boolean setAppendDefaultOrCustom(String stringValue) {
        if(stringValue.toLowerCase().equals("default") || stringValue.toLowerCase().equals("custom")) {
            appendDefaultOrCustom = stringValue.toLowerCase();
            file.set("appendDefaultOrCustom", appendDefaultOrCustom);
            return true;
        }
        return false;
    }

    public static int getDefaultCategoryMinTrades() { return defaultCategoryMinTrades; }
    public static int getDefaultCategoryMaxTrades() { return defaultCategoryMaxTrades; }
    public static int getMixedTraderMinTrades() { return mixedTraderMinTrades; }
    public static int getMixedTraderMaxTrades() { return mixedTraderMaxTrades; }
    public static int getDespawnTimer() { return despawnTimer; }
    public static int getDefaultCategoryWeight() { return defaultCategoryWeight; }
    public static boolean getPreventDuplicateTrades() { return preventDuplicateTrades; }
    public static boolean getRemoveLlamasOnTraderDespawn() { return removeLlamasOnTraderDespawn; }
    public static boolean getBroadcastTraderSpawn() { return broadcastTraderSpawn; }
    public static boolean getLeakTraderCoords() { return leakTraderCoords; }
    public static boolean getMixTraderCategories() { return mixTraderCategories; }
    public static boolean getKeepDefaultTrades() { return keepDefaultTrades; }
    public static boolean getCombineDefaultAndCustom() { return combineDefaultAndCustom; }
    public static int getDefaultTradesAmount() { return defaultTradesAmount; }
    public static String getAppendDefaultOrCustom() { return appendDefaultOrCustom; }
}