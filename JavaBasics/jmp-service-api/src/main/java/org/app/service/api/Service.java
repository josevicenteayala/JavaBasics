package org.app.service.api;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Predicate;

import org.app.model.BankCard;
import org.app.model.Subscription;
import org.app.model.User;

public interface Service {

    int PAYABLE_AGE = 18;

    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);
    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);

    default double getAverageUsersAge() {
        OptionalDouble average = getAllUsers().stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now())).average();
        if(average.isPresent()) {
            return average.getAsDouble();
        }
        return 0;
    }

    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) >= PAYABLE_AGE;
    }
}
