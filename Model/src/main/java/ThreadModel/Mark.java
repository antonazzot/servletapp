package ThreadModel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Mark {
   private int valuesOfMark;

    public Mark withValue(Integer value) {
        setValuesOfMark(value);
        return this;
    }


}
