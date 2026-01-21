package sbp.task;

import sbp.configuration.DefaultConfiguratiton;
import sbp.repository.CreatureRepository;
import sbp.type.LogLevel;

import java.util.Map;

public class StatisticTask implements Runnable {

    private final CreatureRepository animalOnCellRepository;

    public StatisticTask(final CreatureRepository animalOnCellRepository) {
        this.animalOnCellRepository = animalOnCellRepository;
    }

    @Override
    public void run() {
        Map<String, Integer> statistic = animalOnCellRepository.getCreaturesNumberOnAllCell();
        System.out.println("Statistic:\n" + animalOnCellRepository.getCreaturesNumberOnAllCell());
        if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
            System.out.println("Repository:\n" + animalOnCellRepository.getAllAnimals());
        }
    }

}