package sbp.repository;

import lombok.ToString;
import sbp.entity.Animal;
import sbp.entity.Cell;
import sbp.entity.Creature;
import sbp.entity.plant.Plant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ToString
public class CreatureRepository {

    private final Map<Cell, ConcurrentHashMap<Class, List<Creature>>> creaturesOnCell = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    private CreatureRepository() {
    }

    private class Holder {
        private static final CreatureRepository INSTANCE;

        static {
            INSTANCE = new CreatureRepository();
        }

        private Holder() {
        }

        public static CreatureRepository getInstance() {
            return INSTANCE;
        }
    }

    public static CreatureRepository getInstance() {
        return Holder.getInstance();
    }


    public boolean saveAll(List<Creature> newCreaturies) {
        if (newCreaturies == null || newCreaturies.isEmpty()) {
            return false;
        }

        Cell cell = newCreaturies.get(0).getCell();
        Class<?> animalClass = newCreaturies.get(0).getClass();
        Integer maxNumber = newCreaturies.get(0).getMaxNumberOnCell();

        synchronized (cell) {
            Map<Class, List<Creature>> creaturesMap = creaturesOnCell.computeIfAbsent(cell, k -> new ConcurrentHashMap<>());
            List<Creature> creatures = creaturesMap.computeIfAbsent(animalClass, k -> Collections.synchronizedList(new ArrayList<>()));

            if (creatures.size() + newCreaturies.size() > maxNumber) {
                return false;
            }

            for (Creature creature : newCreaturies) {
                creature.setId(getId());
                creatures.add(creature);
            }
            return true;
        }
    }

    public boolean save(Creature creature, Cell newCell) {
        boolean isSaved = false;
        Integer maxNumber = creature.getMaxNumberOnCell();
        boolean isNew = false;
        if (creature.getId() == null) {
            creature.setId(getId());
            isNew = true;
        }
        Class<?> animalClass = creature.getClass();
        synchronized (newCell) {
            Map<Class, List<Creature>> animalsMap = creaturesOnCell.computeIfAbsent(newCell, k -> new ConcurrentHashMap<>());
            List<Creature> animals = animalsMap.computeIfAbsent(animalClass, k -> Collections.synchronizedList(new ArrayList<>()));
            if (animals.size() < maxNumber) {
                animals.add(creature);
                isSaved = true;
            }
        }
        if (isSaved && !isNew) {
            delete(creature);
        }
        return isSaved;
    }

    public Map<Class, List<Creature>> getAllAnimalsOnCell(Cell cell) {
        return creaturesOnCell.entrySet().stream()
                .filter(entry -> entry.getKey().equals(cell))
                .flatMap(entry -> entry.getValue().entrySet().stream())
                .filter(entry -> !Plant.class.equals(entry.getKey()))
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }


    public List<Creature> getAllPlantsOnCell(Cell cell) {
        Map<Class, List<Creature>> map = creaturesOnCell.get(cell);
        if (map == null || !map.containsKey(Plant.class)) {
            return new ArrayList<>();
        }
        List<Creature> creatures = map.get(Plant.class);
        return creatures == null ? new ArrayList<>() : new ArrayList<>(creatures);
    }


    public boolean delete(Creature creature) {
        Cell cell = creature.getCell();
        if (cell == null) {
            return false;
        }
        synchronized (cell) {
            Map<Class, List<Creature>> animalsMap = creaturesOnCell.get(cell);
            if (animalsMap == null) {
                return false;
            }

            List<Creature> list = animalsMap.get(creature.getClass());
            if (list == null) {
                return false;
            }

            boolean removed = list.remove(creature);
            if (removed && list.isEmpty()) {
                animalsMap.remove(creature.getClass());
            }
            if (animalsMap.isEmpty()) {
                creaturesOnCell.remove(creature.getCell());
            }
            return removed;
        }
    }


    private long getId() {
        return idGenerator.incrementAndGet();
    }


    public Map<String, Integer> getCreaturesNumberOnAllCell() {
        return this.creaturesOnCell.entrySet().stream()
                .flatMap(cellEntry -> cellEntry.getValue().entrySet().stream())
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getSimpleName(),
                        entry -> entry.getValue().size(),
                        Integer::sum
                ));
    }


    public List<Animal> getAllAnimals() {
        return creaturesOnCell.values().stream()
                .flatMap(cellEntry -> cellEntry.values().stream())
                .flatMap(List::stream)
                .filter(creature -> creature instanceof Animal)
                .map(creature -> (Animal) creature)
                .collect(Collectors.toList());
    }

    public List<Plant> getAllPlants() {
        return creaturesOnCell.values().stream()
                .flatMap(cellEntry -> cellEntry.values().stream())
                .flatMap(List::stream)
                .filter(creature -> creature instanceof Plant)
                .map(creature -> (Plant) creature)
                .collect(Collectors.toList());
    }

    public List<Creature> getAllCreatures() {
        return creaturesOnCell.values().stream()
                .flatMap(cellEntry -> cellEntry.values().stream())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}