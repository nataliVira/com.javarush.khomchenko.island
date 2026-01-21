package sbp.entity;


import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import sbp.type.TypeAnimal;

import java.util.List;

@Getter
@ToString
public class AnimalProperty {

    @NonNull
    private final String nameAnimal;

    @NonNull
    private final Integer speed;

    @NonNull
    private final Double volumeFoodSatiety;

    @NonNull
    private final List<TypeAnimal> types;

    @NonNull
    private final Integer numberCubs;

    @NonNull
    private final Integer maxNumberAnimalOnCell;

    @NonNull
    private final String packageName;

    @NonNull
    private final Double weight;

    @NonNull
    private final String image;

    public AnimalProperty(
            final String nameAnimal,
            final Integer speed,
            final Double volumeFoodSatiety,
            final List<TypeAnimal> types,
            final Integer numberCubs,
            final Integer maxNumberAnimalOnCell,
            final String packageName,
            final Double weight,
            final String image) {
        this.packageName = packageName;
        this.weight = weight;
        this.image = image;

        if (speed < 0 || volumeFoodSatiety < 0 || numberCubs <= 0 || maxNumberAnimalOnCell <= 0) {
            throw new IllegalArgumentException("Speed must be greater than 0");
        }

        if (types.isEmpty()) {
            throw new IllegalArgumentException("Types must not be empty");
        }

        this.nameAnimal = nameAnimal;
        this.speed = speed;
        this.volumeFoodSatiety = volumeFoodSatiety;
        this.types = List.copyOf(types);
        this.numberCubs = numberCubs;
        this.maxNumberAnimalOnCell = maxNumberAnimalOnCell;
    }

}