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
    private static final String[] textureExtensions = { ".png", ".jpg" };
    private static final String[] sfxExtensions = { ".wav", ".mp3", ".flac" };
    private static final String[] musicExtensions = sfxExtensions;
    private static final String[] fontExtensions = { ".ttf" };

    private static String TEXTURE_DIR = "textures/";
    private static String SFX_DIR = "sfx/";
    private static String MUSIC_FIR = "music/";
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

            if (path.startsWith(TEXTURE_DIR) && endsWithAny(path, textureExtensions)) {
                gdxAssets.load(path, Texture.class);
            } else if (path.startsWith(SFX_DIR) && endsWithAny(path, sfxExtensions)) {
                gdxAssets.load(path, Sound.class);
            } else if (path.startsWith(MUSIC_FIR) && endsWithAny(path, musicExtensions)) {
                gdxAssets.load(path, Music.class);
            } else if (path.startsWith(FONT_DIR) && endsWithAny(path, fontExtensions)) {
                // TODO: fonts
            } else {
                System.out.println("[ASSETS : warning] : unsupported asset : \"" + path + "\"");
            }
            // TODO: support more asset types (?)
        }
        // [load all assets in queue]
        gdxAssets.finishLoading();
    }

    /**
     * cool way to get tuples in java
     */
    private record AssetSpec(String basePath, Class<?> expectedType) {
    }

    /**
     * resolves correct extension and type from file name
     */
    private static AssetSpec resolveSpec(String fileName) {
        if (endsWithAny(fileName, textureExtensions)) {
            return new AssetSpec(TEXTURE_DIR, Texture.class);
        }
        if (endsWithAny(fileName, sfxExtensions)) {
            return new AssetSpec(SFX_DIR, Sound.class);
        }
        if (endsWithAny(fileName, musicExtensions)) {
            return new AssetSpec(MUSIC_FIR, Music.class);
        }
        throw new IllegalArgumentException("[ASSETS] : unsupported asset extension for file: " + fileName);
    }

    /**
     * getter for loaded assets:
     * provide filename WITH extension and type
     */
    public static <T> T get(String file, Class<T> type) {
        AssetSpec spec = resolveSpec(file);
        if (!type.isAssignableFrom(spec.expectedType())) {
            throw new IllegalArgumentException(
                    "[ASSETS] Type mismatch for \"" + file + "\": expected " + spec.expectedType().getSimpleName()
                            + ", got " + type.getSimpleName());
        }

        String fullPath = spec.basePath() + file;
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
