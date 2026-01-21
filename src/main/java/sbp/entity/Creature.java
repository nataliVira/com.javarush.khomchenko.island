package sbp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

public abstract class Creature {
    @Getter
    protected Integer maxNumberOnCell;
    @Getter
    @Setter
    protected Cell cell;
    @Getter
    @Setter
    protected Long id = null;
    @Getter
    protected Double weight;
    @Getter
    protected String image;
    @Getter
    protected ReentrantLock lock = new ReentrantLock();

    public abstract boolean isEnable();
    public abstract void die();


}
