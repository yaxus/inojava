package com.example.dungeon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект - Монстр
 */
public class Monster extends Entity {
    private int level;
    private final List<Item> items = new ArrayList<>();

    public Monster(String name, int level, int hp) {
        super(name, hp);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Item> getItems() {
        return items;
    }
}
