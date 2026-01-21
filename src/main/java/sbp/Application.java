package sbp;

import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.Creature;
import sbp.fabrica.AnimalFactory;
import sbp.fabrica.PlantFactory;
import sbp.repository.CreatureRepository;
import sbp.service.scheduler.TaskScheduledExecutorService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class Application {
    private static final String INITIAL_ERROR = "Initial configuration error:";

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init();
        TaskScheduledExecutorService service = new TaskScheduledExecutorService();
        service.start();
    }

    private static void init() {
        AnimalFactory animalFactory = AnimalFactory.getInstance();
        CreatureRepository repository = CreatureRepository.getInstance();
        DefaultConfiguratiton.InitConfiguration.getAnimalConfiguration().forEach((k, v) -> {
            try {
                List<Creature> animals = animalFactory.createCreatures(v, k, null);
                if (repository.saveAll(animals)) {
                    System.out.println("Created:" + animals);
                }
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException
                     | InstantiationException | IllegalAccessException e) {
                System.out.println(INITIAL_ERROR);
                e.printStackTrace();
            }
        });

        PlantFactory plantFactory = PlantFactory.getInstance();
        DefaultConfiguratiton.InitConfiguration.getPlantConfiguration().forEach((k, v) -> {
            try {
                List<Creature> plants = plantFactory.createCreatures(v, k, null);
                if (repository.saveAll(plants)) {
                    System.out.println("Created Plants: " + plants.size());
                }
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException
                     | InstantiationException | IllegalAccessException e) {
                System.out.println(INITIAL_ERROR);
                e.printStackTrace();
            }
        });
    }

}