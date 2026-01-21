package sbp.configuration;

import sbp.entity.AnimalProperty;
import sbp.entity.PlantProperty;
import sbp.type.LogLevel;
import sbp.type.TypeAnimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultConfiguratiton {
    private DefaultConfiguratiton() {
    }

    public interface Animal {
        String WOLF = "Wolf", BOA = "Boa", FOX = "Fox", BEAR = "Bear",
                EAGLE = "Eagle", HORSE = "Horse", DEER = "Deer", RABBIT = "Rabbit",
                MOUSE = "Mouse", GOAT = "Goat", SHEEP = "Sheep", BOAR = "Boar", BUFFALO = "Buffalo",
                DUCK = "Duck", CATERPILLAR = "Caterpillar", PLANT = "Plant";
    }

    public interface InitConfiguration {
        static Map<String, Integer> getAnimalConfiguration() {
            Map<String, Integer> map = new HashMap<>();
            map.put(Animal.WOLF, 20);
            map.put(Animal.BOA, 20);
            map.put(Animal.FOX, 20);
            map.put(Animal.BEAR, 20);
            map.put(Animal.EAGLE, 20);
            map.put(Animal.HORSE, 20);
            map.put(Animal.DEER, 20);
            map.put(Animal.RABBIT, 20);
            map.put(Animal.MOUSE, 20);
            map.put(Animal.GOAT, 20);
            map.put(Animal.SHEEP, 20);
            map.put(Animal.BOAR, 20);
            map.put(Animal.BUFFALO, 20);
            map.put(Animal.DUCK, 10);
            map.put(Animal.CATERPILLAR, 10);
            return map;
        }

        static Map<String, Integer> getPlantConfiguration() {
            Map<String, Integer> map = new HashMap<>();
            map.put(Animal.PLANT, 1);
            return map;
        }
    }

    public interface IslanProperties {
        Integer width = 100, height = 20, numberPlantsPerDayOnCell = 10;
        LogLevel log_level = LogLevel.INFO;
    }


    interface Package {
        String ANIMAL_PACKAGE = "sbp.entity.animal";
        String PLANT_PACKAGE = "sbp.entity.plant";
    }

    public static List<AnimalProperty> animalProperties = List.of(
            new AnimalProperty(Animal.WOLF, 3, 8.0, List.of(TypeAnimal.PREDATOR),
                    1, 30, Package.ANIMAL_PACKAGE, 50.0, "\uD83D\uDC3A"),
            new AnimalProperty(Animal.BOA, 1, 3.0, List.of(TypeAnimal.PREDATOR),
                    1, 30, Package.ANIMAL_PACKAGE, 15.0, "\uD83D\uDC0D"),
            new AnimalProperty(Animal.FOX, 2, 2.0, List.of(TypeAnimal.PREDATOR),
                    1, 30, Package.ANIMAL_PACKAGE, 8.0, "\uD83E\uDD8A"),
            new AnimalProperty(Animal.BEAR, 2, 80.0, List.of(TypeAnimal.PREDATOR),
                    1, 5, Package.ANIMAL_PACKAGE, 500.0, "\uD83D\uDC3B"),
            new AnimalProperty(Animal.EAGLE, 3, 1.0, List.of(TypeAnimal.PREDATOR),
                    1, 20, Package.ANIMAL_PACKAGE, 6.0, "\uD83E\uDD85"),
            new AnimalProperty(Animal.HORSE, 4, 60.0, List.of(TypeAnimal.HERBIVOROUS),
                    1, 20, Package.ANIMAL_PACKAGE, 400.0, "\uD83D\uDC0E"),
            new AnimalProperty(Animal.DEER, 4, 50.0, List.of(TypeAnimal.HERBIVOROUS),
                    1, 20, Package.ANIMAL_PACKAGE, 300.0, "\uD83E\uDD8C"),
            new AnimalProperty(Animal.RABBIT, 2, 0.45, List.of(TypeAnimal.HERBIVOROUS),
                    1, 150, Package.ANIMAL_PACKAGE, 300.0, "\uD83D\uDC07"),
            new AnimalProperty(Animal.MOUSE, 1, 0.01, List.of(TypeAnimal.HERBIVOROUS, TypeAnimal.PREDATOR),
                    1, 500, Package.ANIMAL_PACKAGE, 0.05, "\uD83D\uDC01"),
            new AnimalProperty(Animal.GOAT, 3, 10.0, List.of(TypeAnimal.HERBIVOROUS),
                    1, 140, Package.ANIMAL_PACKAGE, 60.0, "\uD83D\uDC10"),
            new AnimalProperty(Animal.SHEEP, 3, 15.0, List.of(TypeAnimal.HERBIVOROUS),
                    1, 140, Package.ANIMAL_PACKAGE, 70.0, "\uD83D\uDC11"),
            new AnimalProperty(Animal.BOAR, 2, 50.0, List.of(TypeAnimal.HERBIVOROUS, TypeAnimal.PREDATOR),
                    1, 50, Package.ANIMAL_PACKAGE, 400.0, "\uD83D\uDC17"),
            new AnimalProperty(Animal.BUFFALO, 3, 100.0, List.of(TypeAnimal.PREDATOR),
                    1, 10, Package.ANIMAL_PACKAGE, 700.0, "\uD83D\uDC03"),
            new AnimalProperty(Animal.DUCK, 4, 0.15, List.of(TypeAnimal.HERBIVOROUS, TypeAnimal.PREDATOR),
                    1, 200, Package.ANIMAL_PACKAGE, 1.0, "\uD83E\uDD86"),
            new AnimalProperty(Animal.CATERPILLAR, 0, 0.0, List.of(TypeAnimal.HERBIVOROUS),
                    1, 1000, Package.ANIMAL_PACKAGE, 0.01, "\uD83D\uDC1B"));


    public static List<PlantProperty> plantProperty = List.of(
            new PlantProperty(Animal.PLANT, 200, Package.PLANT_PACKAGE, 1.0, "\uD83C\uDF3F"));


    public static final Map<String, Map<String, Integer>> HUNTING_DICTIONARY = new ConcurrentHashMap<>();

    static {
        Map<String, Integer> huntingDictionaryWolf = new ConcurrentHashMap<>();
        huntingDictionaryWolf.put(Animal.WOLF, -1);
        huntingDictionaryWolf.put(Animal.BOA, 0);
        huntingDictionaryWolf.put(Animal.FOX, 0);
        huntingDictionaryWolf.put(Animal.BEAR, 0);
        huntingDictionaryWolf.put(Animal.EAGLE, 0);
        huntingDictionaryWolf.put(Animal.HORSE, 10);
        huntingDictionaryWolf.put(Animal.DEER, 15);
        huntingDictionaryWolf.put(Animal.RABBIT, 16);
        huntingDictionaryWolf.put(Animal.MOUSE, 80);
        huntingDictionaryWolf.put(Animal.GOAT, 60);
        huntingDictionaryWolf.put(Animal.SHEEP, 70);
        huntingDictionaryWolf.put(Animal.BOAR, 15);
        huntingDictionaryWolf.put(Animal.BUFFALO, 10);
        huntingDictionaryWolf.put(Animal.DUCK, 40);
        huntingDictionaryWolf.put(Animal.CATERPILLAR, 0);
        huntingDictionaryWolf.put(Animal.PLANT, 0);
        HUNTING_DICTIONARY.put(Animal.WOLF, huntingDictionaryWolf);

        Map<String, Integer> huntingDictionaryHerbivore = new HashMap<>();

        Map<String, Integer> huntingBoa = new HashMap<>();
        huntingBoa.put(Animal.FOX, 15);
        huntingBoa.put(Animal.RABBIT, 20);
        huntingBoa.put(Animal.MOUSE, 40);
        huntingBoa.put(Animal.DUCK, 10);
        HUNTING_DICTIONARY.put(Animal.BOA, huntingBoa);

        Map<String, Integer> huntingFox = new HashMap<>();
        huntingFox.put(Animal.RABBIT, 70);
        huntingFox.put(Animal.MOUSE, 90);
        huntingFox.put(Animal.DUCK, 60);
        huntingFox.put(Animal.CATERPILLAR, 40);
        HUNTING_DICTIONARY.put(Animal.FOX, huntingFox);

        Map<String, Integer> huntingDictionaryBear = new ConcurrentHashMap<>();
        huntingDictionaryBear.put(Animal.BOA, 80);
        huntingDictionaryBear.put(Animal.HORSE, 40);
        huntingDictionaryBear.put(Animal.DEER, 80);
        huntingDictionaryBear.put(Animal.RABBIT, 80);
        huntingDictionaryBear.put(Animal.MOUSE, 90);
        huntingDictionaryBear.put(Animal.GOAT, 70);
        huntingDictionaryBear.put(Animal.SHEEP, 70);
        huntingDictionaryBear.put(Animal.BOAR, 50);
        huntingDictionaryBear.put(Animal.BUFFALO, 20);
        huntingDictionaryBear.put(Animal.DUCK, 10);
        HUNTING_DICTIONARY.put(Animal.BEAR, huntingDictionaryBear);

        Map<String, Integer> huntingEagle = new HashMap<>();
        huntingEagle.put(Animal.FOX, 10);
        huntingEagle.put(Animal.RABBIT, 90);
        huntingEagle.put(Animal.MOUSE, 90);
        huntingEagle.put(Animal.DUCK, 80);
        HUNTING_DICTIONARY.put(Animal.EAGLE, huntingEagle);

        HUNTING_DICTIONARY.put(Animal.HORSE, huntingDictionaryHerbivore);
        HUNTING_DICTIONARY.put(Animal.DEER, huntingDictionaryHerbivore);
        HUNTING_DICTIONARY.put(Animal.RABBIT, huntingDictionaryHerbivore);

        Map<String, Integer> huntingMouse = new HashMap<>();
        huntingMouse.put(Animal.CATERPILLAR, 90);
        huntingMouse.put(Animal.PLANT, 100);
        HUNTING_DICTIONARY.put(Animal.MOUSE, huntingMouse);

        HUNTING_DICTIONARY.put(Animal.GOAT, huntingDictionaryHerbivore);
        HUNTING_DICTIONARY.put(Animal.SHEEP, huntingDictionaryHerbivore);

        Map<String, Integer> huntingBoar = new HashMap<>();
        huntingBoar.put(Animal.MOUSE, 50);
        huntingBoar.put(Animal.CATERPILLAR, 90);
        huntingBoar.put(Animal.PLANT, 100);
        HUNTING_DICTIONARY.put(Animal.BOAR, huntingBoar);

        HUNTING_DICTIONARY.put(Animal.BUFFALO, huntingDictionaryHerbivore);

        Map<String, Integer> huntingDuck = new HashMap<>();
        huntingDuck.put(Animal.CATERPILLAR, 90);
        huntingDuck.put(Animal.PLANT, 100);
        HUNTING_DICTIONARY.put(Animal.DUCK, huntingDuck);

        HUNTING_DICTIONARY.put(Animal.CATERPILLAR, huntingDictionaryHerbivore);

        Map<String, Integer> huntingDictionaryDuck = new HashMap<>();
        huntingDictionaryDuck.put(Animal.PLANT, 100);
        huntingDictionaryDuck.put(Animal.CATERPILLAR, 0);
        HUNTING_DICTIONARY.put(Animal.DUCK, huntingDictionaryDuck);
    }

}
