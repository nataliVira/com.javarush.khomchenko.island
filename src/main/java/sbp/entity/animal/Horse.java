package sbp.entity.animal;

import sbp.entity.AnimalProperty;
import sbp.entity.Cell;
import sbp.entity.Herbivorous;

public class Horse extends Herbivorous {

    protected Horse(final Cell cell,
                    final AnimalProperty animalProperty) {
        super(cell, animalProperty);
    }
}
