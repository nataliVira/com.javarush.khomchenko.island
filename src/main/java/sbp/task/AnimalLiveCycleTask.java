package sbp.task;

import sbp.entity.Animal;
import sbp.repository.CreatureRepository;
import sbp.service.AnimalService;

import java.util.List;
import java.util.concurrent.*;

public class AnimalLiveCycleTask implements Runnable {

    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 10;
    private final CreatureRepository animalOnCellRepository;
    private final AnimalService animalService;
    private final CountDownLatch latch;

    private final ThreadPoolExecutor executor;

    public AnimalLiveCycleTask(final CreatureRepository animalOnCellRepository,
                               final AnimalService animalService,
                               final ThreadPoolExecutor executor,
                               final CountDownLatch latch
    ) {
        this.animalOnCellRepository = animalOnCellRepository;
        this.animalService = animalService;
        this.latch = latch;
        this.executor = executor;
    }


    @Override
    public void run() {
        List<Animal> allAnimals = animalOnCellRepository.getAllAnimals();

        for (Animal animal : allAnimals) {
            boolean submitted = false;
            int attempts = 0;

            while (!submitted && attempts < MAX_RETRIES) {
                try {
                    if (executor.getQueue().remainingCapacity() == 0) {
                        Thread.sleep(RETRY_DELAY_MS);
                        attempts++;
                        continue;
                    }
                    executor.submit(new AnimalTask(animalService, animal, latch));
                    submitted = true;

                } catch (RejectedExecutionException | InterruptedException e) {
                    try {
                        e.printStackTrace();
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        break;
                    }
                    attempts++;
                }
            }
            if (!submitted)
                System.err.println("Не удалось запустить задачу для животного: " + animal);
        }
    }

}
