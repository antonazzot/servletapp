package ThreadModel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Theams {
    private  int id;
    private String theamName;
    public Theams withId (int id) {
        setId(id);
        return this;
    }

    public Theams withValue(String theamName) {
       setTheamName(theamName);
        return this;
    }

}
