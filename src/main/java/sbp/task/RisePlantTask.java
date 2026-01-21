package sbp.task;

import sbp.entity.Cell;
import sbp.entity.Island;
import sbp.service.PlantService;

import java.util.concurrent.CountDownLatch;

public class RisePlantTask implements Runnable {
    private final PlantService plantService = PlantService.getInstance();
    private final Island island = Island.getInstance();
    private final CountDownLatch latch;

    public RisePlantTask(final CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (Cell cell : island.getAllCells()) {
            try {
                plantService.grow(cell);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
    }

}
