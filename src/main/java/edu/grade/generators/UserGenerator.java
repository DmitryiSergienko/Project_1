package edu.grade.generators;

import com.github.javafaker.Faker;
import edu.grade.model.CreateUserRequest;

public class UserGenerator {

    public static CreateUserRequest randomUser() {
        Faker faker = new Faker();

        return new CreateUserRequest()
                .withEmail(faker.internet().safeEmailAddress())
                .withPassword(faker.internet().password(8, 20))
                .withName(faker.name().firstName());
    }
}
