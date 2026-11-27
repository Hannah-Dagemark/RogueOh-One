package com.roo.rogueyard.shared.game.world;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.engine.utils.seed.PseudoSeed;
import com.roo.rogueyard.shared.engine.utils.SimplexNoise;
import com.roo.rogueyard.shared.engine.utils.seed.RandomRef;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.npc.EnemyNpcFactory;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.world.entities.DescendPointEntity;
import com.roo.rogueyard.shared.game.world.entities.DescendPointFactory;

import java.util.Random;

/**
 * Represents the spaces the player navigates in and moves between in the world.
 */
public class WalkableLayer {
    private Entity[] enemies;
    private final DescendPointEntity[] descendPoints;
    private final PseudoSeed seed;
    private final int difficultyFactor;
    private final double layoutSeed;
    private final double[][] standardScaleLayerLayout;
    private final RandomRef enemyRandomizer;

    /**
     * Initialiser
     * @param map The map which the layer belongs to
     * @param seed The seed instance which should be used for random generation
     * @param difficultyFactor The difficulty factor of the layer
     * @param generationRandomizer The randomiser the layer should use for generating terrain and objects
     * @param enemyRandomizer The randomiser the layer should use for generating enemies and enemy properties
     */
    public WalkableLayer(LayeredMap map, PseudoSeed seed, int difficultyFactor, RandomRef generationRandomizer, RandomRef enemyRandomizer) {
        this.seed = seed;
        this.layoutSeed = seed.getRandom(generationRandomizer).nextDouble();
        this.standardScaleLayerLayout = getLayerLayout(1);
        this.difficultyFactor = difficultyFactor;
        this.descendPoints = generateDescentPoints(seed.getRandom(generationRandomizer), map);
        this.enemyRandomizer = enemyRandomizer;
    }

    /**
     * Activates the current layer, making objects visible and spawning enemies.
     */
    public void activateLayer() {
        enemies = generateEnemies(this.seed.getRandom(enemyRandomizer), this.difficultyFactor);
        for (DescendPointEntity descendPoint : descendPoints) {
            descendPoint.getComponent(VisualComponent.class).visible = true;
        }
        // TODO: Add hitbox registration / activation here
    }

    /**
     * Clears the layer of all associated entities and objects, destroying them. This is in essence 'unloading' a layer.
     */
    public void clearLayer() {
        for (Entity enemy : enemies) {
            EntityManager.getInstance().destroyEntity(enemy);
        }
        for (Entity descendPoint : descendPoints) {
            EntityManager.getInstance().destroyEntity(descendPoint);
        }
    }

    /**
     * Private generator function for generating all enemies in a layer
     * @param randomNumberGenerator The generator that should be used for enemies
     * @param difficultyFactor The difficulty factor that should be used for enemies ( typicaly layer's difficulty factor )
     * @return A fixed array of the generated enemies
     */
    private Entity[] generateEnemies(Random randomNumberGenerator, int difficultyFactor) {
        EnemyNpcFactory standardEnemyFactory = new EnemyNpcFactory();
        int amountOfEnemies = randomNumberGenerator.nextInt( Math.floorDiv(difficultyFactor, 2), 3+difficultyFactor);
        Entity[] enemies = new Entity[amountOfEnemies];
        for (int i = 0; i < amountOfEnemies; i++) {
            enemies[i] = standardEnemyFactory.createNPC();
            enemies[i].getComponent(TransformComponent.class).position = new Vector2(randomNumberGenerator.nextFloat(-1,1),randomNumberGenerator.nextFloat(-1,1)).scl(randomNumberGenerator.nextInt(100, 800)).add(800, 500);
        }
        return enemies;
    }

    /**
     * Private generator function for generating all descend points in a layer
     * @param randomPositionGenerator The randomiser that should be used for descend point positions and amounts
     * @param map The map the descend points should belong to and reference ( for descending in the map )
     * @return A fixed array of the descend points
     */
    private DescendPointEntity[] generateDescentPoints(Random randomPositionGenerator, LayeredMap map) {
        int amountOfDescendPoints = randomPositionGenerator.nextInt(1, 4);
        DescendPointEntity[] descendPoints = new DescendPointEntity[amountOfDescendPoints];
        for (int i = 0; i < amountOfDescendPoints; i++) {
            Vector2 descendPointLocation = new Vector2(randomPositionGenerator.nextInt(0,500), randomPositionGenerator.nextInt(0,500));
            descendPoints[i] = DescendPointFactory.createDescendPoint(descendPointLocation, map);
        }
        return descendPoints;
    }

    /**
     * Get the standard layout of the Layer given by the layout generation function
     * @return a 2-dimensional array of 0 or 1 integers, representing walls or walkable terrain
     */
    public double[][] getLayerLayout() {
        return this.standardScaleLayerLayout;
    }

    /**
     * Generate a scaled representation of the layer layout.
     * @param scale a floating point scalar multiplier of the resolution of the layer layout plane
     * @return a 2-dimensional array of 0 or 1 integers, representing walls or walkable terrain
     */
    public double[][] getLayerLayout(float scale) {
        double compactFactor = 0.25; // between 0 and 1, decrease for tighter spaces, increase for more openness

        int width = Math.round(500*scale);
        int height = Math.round(500*scale);
        double distanceTaper = 7; // increase for more aggressive generation of walls around the edges

        double[][] result = new double[width][height];

        double scaleFactor = 0.14; // Scales the image in or out in sharpness. Lower values tends to mean less cohesion.

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                double iF = ((double)i / width)-0.5;
                double jF = ((double)j / height)-0.5;
                double distanceSquared = Math.max(iF*iF + jF*jF - 0.05, 0);
                double x = (i/(width*scaleFactor));
                double y = (j/(height*scaleFactor));
                result[i][j]=((SimplexNoise.noise(x,y, this.layoutSeed)) + distanceSquared*distanceTaper > compactFactor ? 1 : 0);
            }
        }
        return result;
    }

    /**
     * Get the layers descend points
     * @return A fixed array of descend point entities
     */
    public DescendPointEntity[] getDescendPoints() {
        return descendPoints;
    }
}
