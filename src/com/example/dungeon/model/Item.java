package com.example.dungeon.model;

import java.io.Serializable;

// Предмет абстрактный
public abstract class Item implements Serializable {
    private final String name;

    protected Item(String name) {
        // Удаление лишних пробелов
        this.name = name.trim().replaceAll("\\s+", " ");
    }

    public String getName() {
        return name;
    }

    public abstract void apply(GameState ctx);
}
