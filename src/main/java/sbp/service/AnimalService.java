package sbp.service;

import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.Animal;
import sbp.entity.Cell;
import sbp.entity.Creature;
import sbp.entity.Island;
import sbp.fabrica.AnimalFactory;
import sbp.repository.CreatureRepository;
import sbp.type.LogLevel;
import sbp.type.TypeAnimal;
import sbp.util.Generator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static sbp.configuration.DefaultConfiguratiton.HUNTING_DICTIONARY;

public class AnimalService implements AbleEat, Movable, AbleReproduction {

    private final Island island = Island.getInstance();
    private final CreatureRepository repository = CreatureRepository.getInstance();
    private final AnimalFactory animalFactory = AnimalFactory.getInstance();
    private final ReentrantLock serviceLock = new ReentrantLock();


    private class Holder {
        private static final AnimalService INSTANCE = new AnimalService();

        public static AnimalService getInstance() {
            return INSTANCE;
        }
    }

    public static AnimalService getInstance() {
        return Holder.getInstance();
    }

    @Override
    public void move(final Animal animal) {
        serviceLock.lock();
        try {
            if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                System.out.println(String.format("Try moved %s id %d", animal.getClass().getSimpleName(), animal.getId()));
            }
            int direction = Generator.getDirection();

            AbstractMap.SimpleImmutableEntry<Integer, Integer> widthHeight = setNewCoordinates(animal, direction);
            Integer newWidth = widthHeight.getKey();
            Integer newHeight = widthHeight.getValue();

            Cell newCell = island.getCell(newHeight, newWidth);
            if (newCell == null) {
                if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                    System.out.println(String.format("Bad moved %s id %d newCell %s", animal.getClass().getSimpleName(), animal.getId(), newCell));
                }
                return;
            }
            ReentrantLock animalLock = animal.getLock();
            animalLock.lock();
            try {
                if (!animal.isEnable()) {
                    repository.delete(animal);
                    return;
                }
                if (repository.save(animal, newCell)) {
                    animal.move(newCell);
                    if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                        System.out.println(String.format("Moved %s id %d", animal.getClass().getSimpleName(), animal.getId()));
                    }
                }
            } finally {
                animalLock.unlock();
            }
        } finally {
            serviceLock.unlock();
        }
    }

    @Override
    public void eat(final Animal animal) {
        serviceLock.lock();
        try {
            Objects.requireNonNull(animal);
            ReentrantLock animalLockLock = animal.getLock();
            animalLockLock.lock();
            try {
                if (!animal.isEnable() || animal.isFull()) {
                    return;
                }

                if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                    System.out.println(String.format("Try eat id %d  %s %s", animal.getId(), animal.getClass().getSimpleName(), animal));
                }

                Map<String, Integer> huntingDictionary = HUNTING_DICTIONARY.get(animal.getClass().getSimpleName());

                if (animal.getTypes().contains(TypeAnimal.PREDATOR)) {
                    predatorEat(animal, huntingDictionary);
                }
                if (animal.getTypes().contains(TypeAnimal.HERBIVOROUS)) {
                    herbivorousEat(animal);
                }
            } finally {
                animalLockLock.unlock();
            }
        } finally {
            serviceLock.unlock();
        }
    }

    private void herbivorousEat(final Animal animal) {
        if (animal.getVolumeFoodSatiety().equals(0.0)) {
            return;
        }
        List<Creature> ediblePlants = repository.getAllPlantsOnCell(animal.getCell());
        if (ediblePlants == null || ediblePlants.isEmpty()) {
            return;
        }

        for (Creature potentialPrey : ediblePlants) {
            if (animal.isFull()) {
                return;
            }
            Double eatedVolume = tryEatCreature(potentialPrey);
            if (eatedVolume != null) {
                animal.eat(eatedVolume);
            }
        }
    }

    private Double tryEatCreature(final Creature creature) {
        if (!creature.isEnable()) {
            return null;
        }
        creature.die();
        repository.delete(creature);
        return creature.getWeight();
    }

    private void predatorEat(final Animal animal,
                             final Map<String, Integer> huntingDictionary) {
        if (animal.getVolumeFoodSatiety().equals(0.0)) {
            return;
        }

        List<String> potentialFoods = huntingDictionary
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<Class, List<Creature>> potentialMenuCell = repository.getAllAnimalsOnCell(animal.getCell());
        if (potentialMenuCell.isEmpty()) {
            return;
        }

        List<Creature> menu = potentialMenuCell
                .entrySet()
                .stream()
                .filter(entry -> potentialFoods.contains(entry.getKey().getSimpleName()))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());

        if (menu.isEmpty()) {
            return;
        }

        for (Creature potentialPrey : menu) {
            if (animal.isFull()) {
                return;
            }
            Double eatedVolume = tryEatCreature(potentialPrey);
            if (eatedVolume != null) {
                animal.eat(eatedVolume);
            }
        }
    }

    @Override
    public void reproduce(final Animal animal) {
        serviceLock.lock();
        try {
            Objects.requireNonNull(animal);
            ReentrantLock animalLock = animal.getLock();
            animalLock.lock();
            if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                System.out.println(String.format("Try reproduce %s id %d", animal.getClass().getSimpleName(), animal.getId()));
            }
            try {
                if (!animal.isFull() || !animal.isEnable()) {
                    return;
                }

                int numberCubs = Generator.getValue(animal.getNumberCubs() + 1);
                if (numberCubs == 0) {
                    return;
                }

                List<Creature> cubs;
                try {
                    cubs = animalFactory.createCreatures(numberCubs, animal.getClass().getSimpleName(), animal.getCell());
                    if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                        System.out.println("Animal: " + animal.getClass().getSimpleName() + " id = " + animal.getId() + " cubs:" + cubs.size());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    return;
                }

                if (!cubs.isEmpty() && repository.saveAll(cubs)) {
                    animal.reproduce();
                    if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
                        System.out.println(String.format("Is reproduced:  id = %d %s cubs = %d", animal.getId(), animal, cubs.size()));
                    }
                }
            } finally {
                animalLock.unlock();
            }
        } finally {
            serviceLock.unlock();
        }
    }

    private AbstractMap.SimpleImmutableEntry<Integer, Integer> setNewCoordinates(final Animal animal, final int direction) {
        int speed = animal.getSpeed();
        int width = animal.getCell().getWidth();
        int height = animal.getCell().getHeight();

        return switch (direction) {
            case 0 -> new AbstractMap.SimpleImmutableEntry<>(width - speed, height - speed);
            case 1 -> new AbstractMap.SimpleImmutableEntry<>(width, height - speed);
            case 2 -> new AbstractMap.SimpleImmutableEntry<>(width + speed, height - speed);
            case 3 -> new AbstractMap.SimpleImmutableEntry<>(width + speed, height);
            case 4 -> new AbstractMap.SimpleImmutableEntry<>(width + speed, height + speed);
            case 5 -> new AbstractMap.SimpleImmutableEntry<>(width, height + speed);
            case 6 -> new AbstractMap.SimpleImmutableEntry<>(width - speed, height + speed);
            case 7 -> new AbstractMap.SimpleImmutableEntry<>(width - speed, height);
            default -> new AbstractMap.SimpleImmutableEntry<>(width, height);
        };
    }

}
