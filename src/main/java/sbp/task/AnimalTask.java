package sbp.task;

import sbp.entity.Animal;
import sbp.service.AnimalService;

import java.util.concurrent.CountDownLatch;

public class AnimalTask implements Runnable {
    private final AnimalService animalService;

    private final Animal animal;
    private final CountDownLatch latch;

    AnimalTask(final AnimalService animalService,
               final Animal animal,
               final CountDownLatch latch) {
        this.animalService = animalService;
        this.animal = animal;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            animalService.move(this.animal);
            animalService.eat(this.animal);
            animalService.reproduce(this.animal);
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

