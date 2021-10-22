package ThreadModel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Theams {
  private String theamName;

    public Theams withValue(String theamName) {
       setTheamName(theamName);
        return this;
    }
}
