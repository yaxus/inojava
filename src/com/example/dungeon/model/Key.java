package com.example.dungeon.model;

/**
 * Предмет - Ключ
 */
public class Key extends Item {
    public Key(String name) {
        super(name);
    }

    @Override
    public void apply(GameState ctx) {
        if (!ctx.getCurrent().getDoors().isEmpty())
            System.out.println("Ключ звенит. Возможно, где-то есть дверь...");
        else
            System.out.println("По близости нет дверей");
    }
}
