package sbp.service;

import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.Cell;
import sbp.entity.Creature;
import sbp.entity.Island;
import sbp.fabrica.PlantFactory;
import sbp.repository.CreatureRepository;
import sbp.util.Generator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PlantService {

    private final CreatureRepository repository = CreatureRepository.getInstance();
    private final PlantFactory plantFactory = PlantFactory.getInstance();
    private final Island island = Island.getInstance();


    private class Holder {
        private static final PlantService INSTANCE = new PlantService();

        public static PlantService getInstance() {
            return INSTANCE;
        }
    }

    public static PlantService getInstance() {
        return PlantService.Holder.getInstance();
    }

    public void grow(final Cell cell) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Integer potencialNumberPlansOnCell = Generator.getValue(island.getMaxPlantsPerDay());
        if (Generator.getValue(100) <= 50 ) {
            List<Creature> plants = plantFactory.createCreatures(potencialNumberPlansOnCell, DefaultConfiguratiton.Animal.PLANT, cell);
            repository.saveAll(plants);
        }
    }

}
