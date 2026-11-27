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

import java.util.Random;

public class WalkableLayer {

    private final LayeredMap map;
    private Entity[] enemies;
    private final DescendPointEntity[] descendPoints;
    private final PseudoSeed seed;
    private final int difficultyFactor;
    private final double layoutSeed;
    private final double[][] standardScaleMapLayout;
    private final RandomRef enemyRandomizer;

    public WalkableLayer(LayeredMap map, PseudoSeed seed, int difficultyFactor, RandomRef generationRandomizer, RandomRef enemyRandomizer) {
        this.seed = seed;

        this.layoutSeed = seed.getRandom(generationRandomizer).nextDouble();
        this.standardScaleMapLayout = getMapLayout(1);
        this.difficultyFactor = difficultyFactor;
        this.descendPoints = generateDescentPoints(seed.getRandom(generationRandomizer), map);
        this.enemyRandomizer = enemyRandomizer;
        this.map = map;
    }

    public void activateLayer() {
        enemies = generateEnemies(this.seed.getRandom(enemyRandomizer), this.difficultyFactor);
        for (DescendPointEntity descendPoint : descendPoints) {
            descendPoint.getComponent(VisualComponent.class).visible = true;
        }
    }

    public void clearLayer() {
        for (Entity enemy : enemies) {
            EntityManager.getInstance().destroyEntity(enemy);
        }
        for (Entity descendPoint : descendPoints) {
            EntityManager.getInstance().destroyEntity(descendPoint);
        }
    }

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

    private DescendPointEntity[] generateDescentPoints(Random randomPositionGenerator, LayeredMap map) {
        int amountOfDescendPoints = randomPositionGenerator.nextInt(1, 4);
        DescendPointEntity[] descendPoints = new DescendPointEntity[amountOfDescendPoints];
        for (int i = 0; i < amountOfDescendPoints; i++) {
            Vector2 descendPointLocation = new Vector2(randomPositionGenerator.nextInt(0,500), randomPositionGenerator.nextInt(0,500));
            descendPoints[i] = DescendPointFactory.createDescendPoint(descendPointLocation, map);
        }
        return descendPoints;
    }

    public double[][] getMapLayout() {
        return this.standardScaleMapLayout;
    }

    public double[][] getMapLayout(float scale) {
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

    public LayeredMap getMap() {
        return this.map;
    }

    public DescendPointEntity[] getDescendPoints() {
        return descendPoints;
    }
}
