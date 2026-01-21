package sbp.entity.plant;

import lombok.Getter;
import lombok.ToString;
import sbp.configuration.DefaultConfiguratiton;
import sbp.entity.Cell;
import sbp.entity.Creature;
import sbp.entity.PlantProperty;
import sbp.type.LogLevel;

@ToString
@Getter
public class Plant extends Creature {

    private boolean isDied = false;


    protected Plant(final Cell cell,
                    final PlantProperty plantProperty) {
        this.cell = cell;
        this.weight = plantProperty.getWeight();
        this.maxNumberOnCell = plantProperty.getMaxNumberAnimalOnCell();
    }


    @Override
    public boolean isEnable() {
        return !isDied;
    }

    @Override
    public void die() {
        isDied = true;
        if (DefaultConfiguratiton.IslanProperties.log_level == LogLevel.DEBUG) {
            System.out.println(String.format("Plant  is died id = %d", this.getId()));
        }
    }

}
