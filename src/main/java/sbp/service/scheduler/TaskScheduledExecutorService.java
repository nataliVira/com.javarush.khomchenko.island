package sbp.service.scheduler;

import sbp.repository.CreatureRepository;
import sbp.service.AnimalService;
import sbp.task.AnimalLiveCycleTask;
import sbp.task.RisePlantTask;
import sbp.task.StatisticTask;

import java.util.concurrent.*;

public class TaskScheduledExecutorService {

    private static final int CAPACITY = 100;

    private final ThreadPoolExecutor executor;
    private final ScheduledExecutorService scheduledExecutor;
    private final CreatureRepository repository;

    public TaskScheduledExecutorService() {
        this.executor = new ThreadPoolExecutor(
                10,
                10,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(CAPACITY),
                new ThreadPoolExecutor.AbortPolicy());
        this.scheduledExecutor = Executors.newScheduledThreadPool(1);
        this.repository = CreatureRepository.getInstance();
    }

    public void start() {
        scheduledExecutor.scheduleWithFixedDelay(this::tick, 0, 1, TimeUnit.SECONDS);
    }

    private void tick() {
        CountDownLatch latchPlant = new CountDownLatch(1);
        new RisePlantTask(latchPlant).run();

        CountDownLatch latchAnimal = new CountDownLatch(repository.getAllAnimals().size());
        new AnimalLiveCycleTask(repository, AnimalService.getInstance(), executor, latchAnimal).run();

        try {
            latchPlant.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error during RisePlantTask execution");
            e.printStackTrace();
        }

        try {
            latchAnimal.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error during AnimalPlantTask execution");
            e.printStackTrace();
        }

        new StatisticTask(repository).run();
    }
}