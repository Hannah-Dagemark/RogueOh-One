package com.roo.rogueyard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public final class Assets {

    private static final Assets instance = new Assets();
    private final AssetManager gdxAssets = new AssetManager();

    // for fonts
    private static FileHandleResolver resolver = new InternalFileHandleResolver();

    // valid extensions for assets
    private String[] textureExtensions = { ".png", ".jpg" };
    private String[] sfxExtensions = { ".wav", ".mp3", ".flac" };
    private String[] musicExtensions = sfxExtensions;
    private String[] fontExtensions = { ".ttf" };

    private static String texturePath = "sfx/";
    private static String sfxPath = "sfx/";
    private static String musicPath = "music/";
    private static String fontPath = "fonts/";

    private Assets() {
        // setup font loading
        // gdxAssets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        // gdxAssets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        loadAssets();
    }

    public static Assets getInstance() {
        return instance;
    }

    /*
     * helper function to match against all file extensions defined in "[Type]Extensions"
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
    public void loadAssets() {


        // read the asset.txt generated from the gradle build
        FileHandle listFile = Gdx.files.internal("assets.txt");
        // [loop queues all assets]
        for (String rawLine : listFile.readString("UTF-8").split("\\r?\\n")) {
            String path = rawLine.trim();

            if (path.startsWith("textures/") && endsWithAny(path, textureExtensions)) {
                gdxAssets.load(path, Texture.class);
            } else if (path.startsWith("sfx/") && endsWithAny(path, sfxExtensions)) {
                gdxAssets.load(path, Sound.class);
            } else if (path.startsWith("music/") && endsWithAny(path, musicExtensions)) {
                gdxAssets.load(path, Music.class);
            } else if (path.startsWith("fonts/") && endsWithAny(path, fontExtensions)) {
                // TODO: fonts
            } else {
                System.out.println("[warning] : unsupported asset : \"" + path + "\"");
            }
            // TODO: support more asset types (?)
        }
        // [load all assets in queue]
        gdxAssets.finishLoading();
    }

    /**
     * Normalize the path to allow for both
     * "sprite.png"
     * "textures/sprite.png"
     * to work
     */

    public <T> T get(String fullPath, Class<T> type) {
        if (gdxAssets.isLoaded(fullPath)) {
            return gdxAssets.get(fullPath, type);
        } else {
            System.out.println("[warning] : unloaded asset requested : \"" + fullPath + "\"");
            return null;
        }
    }

    public void dispose() {
        gdxAssets.dispose();
    }
}
