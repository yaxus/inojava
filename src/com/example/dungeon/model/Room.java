package com.example.dungeon.model;

import java.io.Serializable;
import java.util.*;

/**
 * Локация
 */
public class Room implements Serializable {
    private final String name;
    private final String description;
    private final Map<String, Room> neighbors = new HashMap<>();
    private final Map<String, Door> doors = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private Monster monster;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Map<String, Room> getNeighbors() {
        return neighbors;
    }

    public Map<String, Door> getDoors() {
        return doors;
    }

    public List<Item> getItems() {
        return items;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster m) {
        this.monster = m;
    }

    public String describe() {
        StringBuilder sb = new StringBuilder(name + ": " + description);
        if (!items.isEmpty()) {
            sb.append("\nПредметы: ").append(String.join(", ", items.stream().map(Item::getName).toList()));
        }
        if (monster != null) {
            sb.append("\nВ комнате монстр: ").append(monster.getName()).append(" (ур. ").append(monster.getLevel()).append(")");
        }
        if (!neighbors.isEmpty()) {
            sb.append("\nВыходы: ").append(String.join(", ", neighbors.keySet()
                    .stream()
                    .map(room -> room + (doors.containsKey(room) ? "*"+doors.get(room).requireKeys()+"key" : ""))
                    .toList()));
        }
        return sb.toString();
    }
}
