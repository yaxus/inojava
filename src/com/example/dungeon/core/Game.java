package com.example.dungeon.core;

import com.example.dungeon.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private final GameState state = new GameState();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    static {
        WorldInfo.touch("Game");
    }

    public Game() {
        registerCommands();
        bootstrapWorld();
    }

    private void testCommand(String get, List<String> args, GameState ctx) throws InterruptedException {
        String strArgs = String.join(" ", args);
        System.out.printf("--> %s %s%n", get, strArgs);
        commands.get(get).execute(ctx, args);
    }

    private void registerCommands() {
        commands.put("test", (ctx, a) -> {
            // Отладка
            testCommand("move", List.of("north"), ctx);
            testCommand("take", List.of("Малое", "зелье"), ctx);
            testCommand("inventory", List.of(), ctx);
            testCommand("use", List.of("Малое", "зелье"), ctx);
            testCommand("fight", List.of(), ctx);
            testCommand("fight", List.of(), ctx);
            testCommand("move", List.of("east"), ctx);
            testCommand("take", List.of("Ключ"), ctx);
            testCommand("inventory", List.of(), ctx);
            testCommand("move", List.of("south"), ctx);
            testCommand("open", List.of("south"), ctx);
            testCommand("move", List.of("south"), ctx);
        });
        commands.put("about", (ctx, a) -> {
            System.out.println("Game: DungeonMini 0.0.9");
            System.out.printf("Java: %s%n", System.getProperty("java.version"));
            System.out.printf("Author: %s%n", "Yakushev Ilya");
        });
        commands.put("help", (ctx, a) -> System.out.println("Команды: " + String.join(", ", commands.keySet())));
        commands.put("gc-stats", (ctx, a) -> {
            Runtime rt = Runtime.getRuntime();
            long free = rt.freeMemory(), total = rt.totalMemory(), used = total - free;
            System.out.println("Память: used=" + used + " free=" + free + " total=" + total);
        });
        commands.put("alloc", (ctx, a) -> {
            List<Player> listPlayers = new ArrayList<>();
            commands.get("gc-stats").execute(ctx, a);
            commands.get("gc").execute(ctx, a);
            commands.get("gc-stats").execute(ctx, a);
            IntStream.range(0, 1000000).forEach(i -> listPlayers.add(new Player("Герой"+i, i, 0)));
            System.out.println("Создано 1 млн Игроков");
            commands.get("gc-stats").execute(ctx, a);
        });
        commands.put("gc", (ctx, a) -> {
            System.gc();
            System.out.println("Выполнен System.gc()");
        });
        commands.put("look", (ctx, a) -> System.out.println(ctx.getCurrent().describe()));
        commands.put("move", (ctx, a) -> {
            // Выбрать локацию
            Room nextRoom = ctx.getCurrent().getNeighbors().get(a.get(0));
            if (nextRoom == null) throw new InvalidCommandException("Такой локации не существует");
            // Проверить наличие двери
            Door door = ctx.getCurrent().getDoors().get(a.get(0));
            if (door != null) {
                System.out.println("Комната заперта на ключ");
                long existKeys = ctx.getPlayer().getInventory().stream()
                        .filter(i -> i instanceof Key)
                        .count();
                if (existKeys >= door.requireKeys())
                    System.out.println("Используйте команду open "+a.get(0));
                else
                    System.out.printf("Не хватает ключей для открытия двери (%s)%n", door.requireKeys() - existKeys);
                return;
            }

            // Установить локацию
            ctx.setCurrent(nextRoom);
            System.out.println("Вы перешли в: " + nextRoom.getName());
            commands.get("look").execute(ctx, a);
        });
        commands.put("take", (ctx, a) -> {
            // Предметы в текущей локации
            List<Item> curItems = ctx.getCurrent().getItems();
            // Склейка названия предмета из массива аргументов
            String strItem = String.join(" ", a);
            // Поиск индекса по названию предмета
            int iItem = IntStream.range(0, curItems.size())
                    .filter(i -> curItems.get(i).getName().equalsIgnoreCase(strItem))
                    .findFirst()
                    .orElse(-1);
            if (iItem == -1) throw new InvalidCommandException("Такого предмета не существует");
            // Предмет
            Item item = curItems.get(iItem);
            // Добавить предмет в инвентарь
            ctx.getPlayer().getInventory().add(item);
            System.out.println("Взято: " + strItem);
            // Удалить предмет из локации
            curItems.remove(iItem);
        });
        commands.put("inventory", (ctx, a) -> {
            // Группировка с подсчетом по кол-ву Предметов
            Map<String, Long> itemsCounting = ctx.getPlayer().getInventory().stream()
                    .map(i -> String.format("- %s (%%s): %s%%n", i.getClass().getSimpleName(), i.getName()))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            // Вывод
            for(Map.Entry<String, Long> i : itemsCounting.entrySet()){
                System.out.printf(i.getKey(), i.getValue());
            }
        });
        commands.put("use", (ctx, a) -> {
            // Предметы в инвентаре
            List<Item> invent = ctx.getPlayer().getInventory();
            // Склейка названия предмета из массива аргументов
            String strItem = String.join(" ", a);
            // Поиск индекса по названию предмета
            int iItem = IntStream.range(0, invent.size())
                    .filter(i -> invent.get(i).getName().equalsIgnoreCase(strItem))
                    .findFirst()
                    .orElse(-1);
            if (iItem == -1) throw new InvalidCommandException("Такого предмета не существует");
            // Предмет
            Item item = invent.get(iItem);
            // Применить предмет
            item.apply(ctx);

        });
        commands.put("fight", (ctx, a) -> {
            Monster monster = ctx.getCurrent().getMonster();
            if (monster == null) throw new InvalidCommandException("Атаковать некого");
            Player player = ctx.getPlayer();
            int hitMonster = monster.getHp() - player.getAttack();
            if (hitMonster <= 0){
                // Вещи монстра переходят к игроку
                List<Item> mItems = ctx.getCurrent().getMonster().getItems();
                player.getInventory().addAll(mItems);
                ctx.getCurrent().setMonster(null);
                System.out.printf("Вы победили в схватке с %s%n", monster.getName());
                if (!mItems.isEmpty()){
                    System.out.printf("Получаете: %s%n", String.join(", ", mItems.stream().map(Item::getName).toList()));
                }
            } else {
                monster.setHp(hitMonster);
                System.out.printf("Вы бьёте %s на %s. HP монстра: %s%n", monster.getName(), player.getAttack(), monster.getHp());
                int hitPlayer = player.getHp() - monster.getLevel();
                if (hitPlayer <= 0){
                    System.out.printf("Вы проиграли в схватке с %s%n", monster.getName());
                    System.out.println("Игра завершена");
                    commands.get("exit").execute(ctx, a);
                }
                player.setHp(hitPlayer);
                System.out.printf("Монстр отвечает на %s. Ваше HP: %s%n", monster.getLevel(), player.getHp());
            }

        });
        // Открыть дверь
        commands.put("open", (ctx, a) -> {
            Door door = ctx.getCurrent().getDoors().get(a.get(0));
            if (door == null){
                System.out.println("В направлении "+a.get(0)+" дверь отсутствует");
                return;
            }
            long existKeys = ctx.getPlayer().getInventory().stream()
                    .filter(i -> i instanceof Key)
                    .count();
            if (existKeys >= door.requireKeys()) {
                List<Item> keys = ctx.getPlayer().getInventory().stream()
                        .filter(i -> i instanceof Key)
                        .limit(door.requireKeys())
                        .toList();
                // Списываем ключи из инвентаря
                ctx.getPlayer().getInventory().removeAll(keys);
                // Удаляем дверь
                ctx.getCurrent().getDoors().remove(a.get(0));
                System.out.println("Дверь открыта");
            }
            else
                System.out.printf("Не хватает ключей для открытия двери (%s шт)%n", door.requireKeys() - existKeys);
        });
        commands.put("save", (ctx, a) -> SaveLoad.save(ctx));
        commands.put("load", (ctx, a) -> SaveLoad.load(ctx));
        commands.put("scores", (ctx, a) -> SaveLoad.printScores());
        commands.put("exit", (ctx, a) -> {
            System.out.println("Пока!");
            System.exit(0);
        });
    }

    private void bootstrapWorld() {
        Player hero = new Player("Герой", 20, 5);
        state.setPlayer(hero);

        Room square = new Room("Площадь", "Каменная площадь с фонтаном.");
        Room forest = new Room("Лес", "Шелест листвы и птичий щебет.");
        Room cave = new Room("Пещера", "Темно и сыро.");
        Room meadow = new Room("Луг", "Солнце и цветочки.");
        square.getNeighbors().put("north", forest);
        forest.getNeighbors().put("south", square);
        forest.getNeighbors().put("east", cave);
        cave.getNeighbors().put("west", forest);
        cave.getNeighbors().put("south", meadow);
        cave.getDoors().put("south", new Door(2));
        meadow.getNeighbors().put("north", cave);

        forest.getItems().add(new Potion("Малое зелье", 5));
        cave.getItems().add(new Key("Ключ"));                       // new
        Monster woolf = new Monster("Волк", 1, 8);
        woolf.getItems().add(new Key("Ключ"));                      // new
        woolf.getItems().add(new Weapon("Меч кладенец", 20)); // new
        forest.setMonster(woolf);

        state.setCurrent(square);
    }

    public void run() {
        System.out.println("DungeonMini (COMPLETE). 'help' — команды.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;
                List<String> parts = Arrays.asList(line.split("\\s+"));
                String cmd = parts.get(0).toLowerCase(Locale.ROOT);
                List<String> args = parts.subList(1, parts.size());
                Command c = commands.get(cmd);
                try {
                    if (c == null) throw new InvalidCommandException("Неизвестная команда: " + cmd);
                    c.execute(state, args);
                    state.addScore(1);
                } catch (InvalidCommandException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}
