package sbp.entity;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class PlantProperty {

    @NonNull
    private final String namePlant;

    @NonNull
    private final Double weight;

    @NonNull
    private final String packageName;

    @NonNull
    private final Integer maxNumberAnimalOnCell;

    @NonNull
    private final String image;

    public PlantProperty(final String namePlant,
                         final Integer maxNumberAnimalOnCell,
                         final String packageName,
                         final Double weight,
                         final String image) {
        this.namePlant = namePlant;
        this.weight = weight;
        this.packageName = packageName;
        this.maxNumberAnimalOnCell = maxNumberAnimalOnCell;
        this.image = image;
    }

}
