package day1java.example3;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class OutcomeTest {

    static Outcome<DbError, Order> readOrderFromDb(int orderId) {
        if (orderId == existingOrderId)
            return new Outcome.Success(new Order(existingOrderId, 123, 123.4));
        else
            return new Outcome.Failure(new DbError("order " + orderId + " does not exist"));
    }

    static Outcome<DbError, User> readUserFromDb(int id) {
        return new Outcome.Success(new User(id, "Joe"));
    }

    private static int existingOrderId = 123;
    private static int notExistingOrderId = 234;

    @Test
    public void checkForErrorCases() {

        Outcome<DbError, Order> res = readOrderFromDb(notExistingOrderId);
        if (res instanceof Outcome.Success)
            fail("this order doesn't exit");
        else if (res instanceof Outcome.Failure)
            assertThat(((Outcome.Failure) res).error).isEqualTo(new DbError("order " + notExistingOrderId + " does not exist"));
    }


    @Test
    public void mapResults() {
        double amount = readOrderFromDb(existingOrderId)
                .map(o -> o.amount)
                .onFailure(e -> fail());

        assertThat(amount).isEqualTo(123.4);
    }


    @Test
    public void combineResults() {
        String userName = readOrderFromDb(existingOrderId)
                .flatMap(o -> readUserFromDb(o.userId))
                .map(u -> u.name)
                .onFailure(err -> fail("Order and User should exist! " + err.msg));

        assertThat(userName).isEqualTo("Joe");
    }


    static class DbError {
        final String msg;

        DbError(String msg) {
            this.msg = msg;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DbError dbError = (DbError) o;
            return Objects.equals(msg, dbError.msg);
        }

        @Override
        public int hashCode() {
            return Objects.hash(msg);
        }

        @Override
        public String toString() {
            return "DbError{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }

    static class Order {
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
