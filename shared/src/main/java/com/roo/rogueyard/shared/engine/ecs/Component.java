package com.roo.rogueyard.shared.engine.ecs;

import java.util.HashMap;

abstract class Component<T> {
    HashMap<Integer, T> entities;
}
