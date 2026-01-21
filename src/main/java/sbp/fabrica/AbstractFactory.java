package sbp.fabrica;

import sbp.entity.Cell;
import sbp.entity.Creature;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractFactory {

    public abstract List<? extends Creature> createCreatures(final Integer number,
                                                             final String name,
                                                             final Cell cell) throws ClassNotFoundException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}
