package ThreadModel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Mark {
   private int id;
   private int valuesOfMark;

    public Mark withId(Integer id) {
        setId(id);
        return this;
    }

    public Mark withValue(Integer value) {
        if (value > 100 ) value = 100;
        if (value < 0) value = 0;
        setValuesOfMark(value);
        return this;
    }

}
