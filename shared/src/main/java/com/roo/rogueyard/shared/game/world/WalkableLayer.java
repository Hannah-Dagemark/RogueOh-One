package com.roo.rogueyard.shared.game.world;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.npc.EnemyNpcFactory;

import java.util.Random;

public class WalkableLayer {

    private final LayeredMap map;
    private Entity[] enemies;
    private final DescendPointEntity[] descendPoints;
    private final PseudoSeed seed;
    private final int difficultyFactor;

    public WalkableLayer(LayeredMap map, PseudoSeed seed, int difficultyFactor) {
        this.seed = seed;
        this.difficultyFactor = difficultyFactor;
        this.descendPoints = generateDescentPoints(seed.layerGenerationRandomizer, map);
        this.map = map;
    }

    public void activateLayer() {
        enemies = generateEnemies(this.seed.enemySpawnRandomizer, this.difficultyFactor);
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

    public LayeredMap getMap() {
        return this.map;
    }

    public DescendPointEntity[] getDescendPoints() {
        return descendPoints;
    }
}
