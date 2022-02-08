package users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "temp_student")
public class TempStudent implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String login;
    private String password;
    private int age;


    @Override
    public String getInf() {
        return "ID: " + getId() +
                "Name: " + getName() +
                "Login: " + getLogin() +
                "age: " + getAge();
    }
}
