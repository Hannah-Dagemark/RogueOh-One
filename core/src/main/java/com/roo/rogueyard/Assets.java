package com.roo.rogueyard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

// for fonts
// import com.badlogic.gdx.assets.loaders.FileHandleResolver;
// import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
// import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
// import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public final class Assets {
    private static Assets instance;
    private final AssetManager gdxAssets = new AssetManager();

    // for fonts
    // private static FileHandleResolver resolver = new
    // InternalFileHandleResolver();

    // valid extensions for assets
    private static final String[] TEXTURE_EXT = { ".png", ".jpg" };
    private static final String[] AUDIO_EXT = { ".wav", ".mp3", ".flac" };
    private static final String[] FONT_EXT = { ".ttf" };

    private static String TEXTURE_DIR = "textures/";
    private static String SOUND_DIR = "sound/";
    private static String MUSIC_DIR = "music/";
    private static String FONT_DIR = "fonts/";

    private Assets() {
        // setup font loading
        // gdxAssets.setLoader(FreeTypeFontGenerator.class, new
        // FreeTypeFontGeneratorLoader(resolver));
        // gdxAssets.setLoader(BitmapFont.class, ".ttf", new
        // FreetypeFontLoader(resolver));

        loadAssets();
    }

    public static void init() {
        if (instance == null) {
            instance = new Assets();
        }
    }

    /**
     * helper function to match against all file extensions
     */
    private static boolean endsWithAny(String value, String[] suffixes) {
        for (String suffix : suffixes) {
            if (value.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * loads all assets located in [root]/assets/*"
     */
    private void loadAssets() {

        // read the asset.txt generated from the gradle build
        FileHandle listFile = Gdx.files.internal("assets.txt");
        // [loop queues all assets]
        for (String rawLine : listFile.readString("UTF-8").split("\\r?\\n")) {
            String path = rawLine.trim();

            if (path.startsWith(TEXTURE_DIR) && endsWithAny(path, TEXTURE_EXT)) {
                gdxAssets.load(path, Texture.class);
            } else if (path.startsWith(SOUND_DIR) && endsWithAny(path, AUDIO_EXT)) {
                gdxAssets.load(path, Sound.class);
            } else if (path.startsWith(MUSIC_DIR) && endsWithAny(path, AUDIO_EXT)) {
                gdxAssets.load(path, Music.class);
            } else if (path.startsWith(FONT_DIR) && endsWithAny(path, FONT_EXT)) {
                // TODO: fonts
            } else {
                System.out.println("[ASSETS : warning] : unsupported asset : \"" + path + "\"");
            }
            // TODO: support more asset types (?)
        }
        // [load all assets in queue]
        gdxAssets.finishLoading();
    }

    public static Texture getTexture(String file) {
        String fullPath = TEXTURE_DIR + file;
        return get(fullPath, Texture.class);
    }

    public static Sound getSound(String file) {
        String fullPath = SOUND_DIR + file;
        return get(fullPath, Sound.class);
    }

    public static Music getMusic(String file) {
        String fullPath = MUSIC_DIR + file;
        return get(fullPath, Music.class);
    }

    private static <T> T get(String fullPath, Class<T> type) {
        if (instance == null) {
            throw new IllegalStateException("[ASSETS] Asset manager not initialized");
        }
        if (!instance.gdxAssets.isLoaded(fullPath, type)) {
            throw new IllegalStateException("[ASSETS] Asset not loaded: \"" + fullPath + "\"");
        }
        return instance.gdxAssets.get(fullPath, type);
    }

    public static void dispose() {
        if (instance != null) {
            instance.gdxAssets.dispose();
        }
    }
}
