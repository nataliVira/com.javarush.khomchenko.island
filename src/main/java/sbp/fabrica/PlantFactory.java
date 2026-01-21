package sbp.fabrica;

import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.Cell;
import sbp.entity.Creature;
import sbp.entity.Island;
import sbp.entity.PlantProperty;
import sbp.entity.plant.Plant;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PlantFactory extends AbstractFactory {

    private final Island island = Island.getInstance();

    private class Holder {
        private static final PlantFactory INSTANCE = new PlantFactory();

        private Holder() {
        }

        public static PlantFactory getAnimalProperty() {
            return INSTANCE;
        }
    }

    public static PlantFactory getInstance() {
        return PlantFactory.Holder.getAnimalProperty();
    }

    @Override
    public List<Creature> createCreatures(final Integer number,
                                          final String name,
                                          final Cell cell) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlantProperty plantProperty = DefaultConfiguratiton.plantProperty.stream()
                .filter(prop -> name.toLowerCase().equals(prop.getNamePlant().toLowerCase()))
                .findFirst().get();
        Class<?> clazz = Class.forName(plantProperty.getPackageName().toLowerCase()
                + "." + plantProperty.getNamePlant());
        List<Creature> plants = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            plants.add((Plant) createPlant(plantProperty, clazz, cell));
        }
        return plants;
    }

    private Object createPlant(final PlantProperty plantProperty,
                               final Class<?> clazz,
                               final Cell cell) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getDeclaredConstructor(Cell.class, PlantProperty.class);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance(cell == null ? island.getRandomCell() : cell, plantProperty);
        return instance;
    }

}
