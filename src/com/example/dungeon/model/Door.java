package com.example.dungeon.model;

import java.io.Serializable;

/**
 * Дверь
 */
public record Door(int requireKeys) implements Serializable { }
