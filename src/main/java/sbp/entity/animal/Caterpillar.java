package sbp.entity.animal;

import sbp.entity.AnimalProperty;
import sbp.entity.Cell;
import sbp.entity.Herbivorous;

public class Caterpillar extends Herbivorous {

    protected Caterpillar(final Cell cell,
                          final AnimalProperty animalProperty) {
        super(cell, animalProperty);
    }
}
