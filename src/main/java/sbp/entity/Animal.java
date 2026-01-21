package sbp.entity;

import lombok.Getter;
import lombok.Setter;
import sbp.configuration.DefaultConfiguratiton;
import sbp.type.LogLevel;
import sbp.type.TypeAnimal;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public abstract class Animal extends Creature {

    private static final int START_ENERGY = 100;
    private final Integer speed;
    private final Double volumeFoodSatiety;
    private final List<TypeAnimal> types;
    private final Integer numberCubs;
    private Integer energy = START_ENERGY;

    protected Animal(final Cell cell,
                     final AnimalProperty animalProperty) {
        this.cell = cell;
        this.speed = animalProperty.getSpeed();
        this.volumeFoodSatiety = animalProperty.getVolumeFoodSatiety();
        this.types = animalProperty.getTypes();
        this.numberCubs = animalProperty.getNumberCubs();
        this.weight = animalProperty.getWeight();
        this.maxNumberOnCell = animalProperty.getMaxNumberAnimalOnCell();
    }


    public void move(Cell cell) {
        this.energy = this.energy - 10;
        this.cell = cell;
    }


    public boolean isEnable() {
        return this.energy >= 0;
    }

    public boolean isFull() {
        return this.energy == START_ENERGY;
    }


    public void reproduce() {
        this.energy -= 20;
    }

    public void eat(Double weightVictim) {
        if (this.energy == START_ENERGY) {
            return;
        }
        Integer addedEnergy = volumeFoodSatiety.equals(0.0) ? 100 : (int) Math.round((weightVictim / volumeFoodSatiety) * 100);
        this.energy = (addedEnergy + this.energy) > START_ENERGY ? START_ENERGY : addedEnergy + this.energy;
    }

    public void die() {
        this.energy = 0;
        if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
            System.out.printf("Animal is died id = %d%n", this.getId());
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "speed=" + speed +
                ", volumeFoodSatiety=" + volumeFoodSatiety +
                ", types=" + types +
                ", numberCubs=" + numberCubs +
                ", weight=" + weight +
                ", energy=" + energy +
                ", maxNumberOnCell=" + maxNumberOnCell +
                ", cell=" + cell +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return speed == animal.speed && volumeFoodSatiety == animal.volumeFoodSatiety && numberCubs == animal.numberCubs && id == animal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, volumeFoodSatiety, numberCubs, id);
    }

}