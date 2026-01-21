package sbp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Cell {
    private final int width;
    private final int height;

    public Cell(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return width == cell.width && height == cell.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

}