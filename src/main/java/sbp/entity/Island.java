package sbp.entity;

import lombok.Getter;
import lombok.ToString;
import sbp.configuration.DefaultConfiguratiton;
import sbp.util.Generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ToString
public class Island {
    @Getter
    private final int width;
    @Getter
    private final int height;

    @Getter
    private final Integer maxPlantsPerDay = DefaultConfiguratiton.IslanProperties.numberPlantsPerDayOnCell;

    private final Map<String, AnimalProperty> animalToMaxNumberAnimal = new HashMap<>();
    private final Cell[][] cells;

    private class Holder {
        private static final Island island;

        static {
            island = new Island(DefaultConfiguratiton.IslanProperties.width, DefaultConfiguratiton.IslanProperties.height);
        }

        private Holder() {
        }

        public static Island getInstance() {
            return island;
        }
    }

    public static Island getInstance() {
        return Holder.getInstance();
    }

    private Island(int width, int height) {
        this.width = width > 0 ? width : 100;
        this.height = height > 0 ? height : 20;
        this.animalToMaxNumberAnimal.putAll(DefaultConfiguratiton.animalProperties.stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getNameAnimal().toLowerCase(), v), HashMap::putAll));
        this.cells = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.cells[y][x] = new Cell(x, y);
            }
        }
    }

    public Cell getCell(int height, int width) {
        try {
            return cells[height][width];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<Cell> getAllCells() {
        return Arrays.stream(cells).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public Cell getRandomCell() {
        return cells[Generator.getValue(height)][Generator.getValue(width)];
    }

}