package com.roo.rogueyard.shared.game;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.Component;

public class InventoryComponent implements Component {
    public List<WeaponComponent> weapons;

    public InventoryComponent(List<WeaponComponent> weapons) {
        this.weapons = weapons;
    }
}
