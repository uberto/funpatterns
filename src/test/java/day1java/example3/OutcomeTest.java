package day1java.example3;

import java.util.Objects;

public class OutcomeTest {

    static class DbError{
        final String msg;

        DbError(String msg) {
            this.msg = msg;
        }
    }

    static class Order{
        final int id;
        final int userId;
        final double amount;

        Order(int id, int userId, double amount) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Order order = (Order) o;
            return id == order.id &&
                    userId == order.userId &&
                    Double.compare(order.amount, amount) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, userId, amount);
        }
    }

    static class User {
        final int id;
        final String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

}
