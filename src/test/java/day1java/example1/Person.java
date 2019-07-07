package day1java.example1;

import java.util.Date;
import java.util.Objects;

public class Person {

    final Date creation;
    final Boolean isCustomer;
    final int id;
    final String name;

    public Person(Date creation, Boolean isCustomer, int id, String name) {

        this.creation = creation;
        this.isCustomer = isCustomer;
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(creation, person.creation) &&
                Objects.equals(isCustomer, person.isCustomer) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creation, isCustomer, id, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "creation=" + creation +
                ", isCustomer=" + isCustomer +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
