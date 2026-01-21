package sbp.fabrica;

import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalFactory extends AbstractFactory {
    private final Island island = Island.getInstance();

    private final Map<String, AnimalProperty> animalToMaxNumberAnimal = new HashMap<>();

    private AnimalFactory() {
        this.animalToMaxNumberAnimal.putAll(DefaultConfiguratiton.animalProperties.stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getNameAnimal().toLowerCase(), v), HashMap::putAll));
    }

    private class Holder {
        private static final AnimalFactory INSTANCE = new AnimalFactory();

        private Holder() {
        }

        public static AnimalFactory getAnimalProperty() {
            return INSTANCE;
        }
    }

    public static AnimalFactory getInstance() {
        return Holder.getAnimalProperty();
    }

    public List<Creature> createCreatures(final Integer number,
                                          final String name,
                                          final Cell cell) throws ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AnimalProperty animalProperty = DefaultConfiguratiton.animalProperties.stream()
                .filter(prop -> name.toLowerCase().equals(prop.getNameAnimal().toLowerCase()))
                .findFirst().get();
        Class<?> clazz = Class.forName(animalProperty.getPackageName().toLowerCase()
                + "." + animalProperty.getNameAnimal());
        List<Creature> animals = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            animals.add((Animal) createAnimal(animalProperty, clazz, cell));
        }
        return animals;
    }

    private Object createAnimal(final AnimalProperty animalProperty,
                                final Class<?> clazz,
                                final Cell cell) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


        Constructor<?> constructor = clazz.getDeclaredConstructor(Cell.class, AnimalProperty.class);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance(cell == null ? island.getRandomCell() : cell, animalProperty);
        return instance;
    }

}