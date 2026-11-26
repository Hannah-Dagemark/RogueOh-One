package com.roo.rogueyard;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader.FreeTypeFontGeneratorParameters;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public final class Assets {
    private static Assets instance;
    private final AssetManager gdxAssets = new AssetManager();

    // for fonts
    private FileHandleResolver resolver = new InternalFileHandleResolver();

    // valid extensions for assets
    private static final String[] TEXTURE_EXT = { ".png", ".jpg" };
    private static final String[] AUDIO_EXT = { ".wav", ".mp3", ".flac" };
    private static final String[] FONT_EXT = { ".ttf" };

    private static String TEXTURE_DIR = "textures/";
    private static String SOUND_DIR = "sound/";
    private static String MUSIC_DIR = "music/";
    private static String FONT_DIR = "fonts/";

    // which sizes a font should generate
    private static int[] FONT_SIZES = { 8, 12, 16, 20, 24, 36, 48 };

    private Assets() {
        // setup font loading
        gdxAssets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        gdxAssets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

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

    public static String baseNameNoExtension(String rawPath) {
        String fileName = Path.of(rawPath).getFileName().toString();
        String baseName = fileName.substring(0, fileName.indexOf("."));
        return baseName;
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
                FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();
                for (int fontSize : FONT_SIZES) {
                    fontParams.fontFileName = path; // the file that exists in assets/fonts/
                    fontParams.fontParameters.size = fontSize;
                    gdxAssets.load(baseNameNoExtension(path) + fontSize + ".ttf", BitmapFont.class, fontParams);
                }
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

    public static BitmapFont getFont(String file) {
        return get(file, BitmapFont.class); // this is different because how font loading works
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
