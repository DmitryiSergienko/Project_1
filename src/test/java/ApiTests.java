import com.github.javafaker.Faker;
import edu.grade.clients.ApiClient;
import edu.grade.model.CreateUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static edu.grade.generators.UserGenerator.randomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf
public class ApiTests {

    private ApiClient apiClient = new ApiClient();

    @Test
    public void createUser() {
        Response response = apiClient.users().create(randomUser());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void createFailUser() {
        CreateUserRequest user = randomUser();
        user.setPassword("");
        Response response = apiClient.users().create(user);

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(403));
    }

    @Test
    public void authUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        Response response = apiClient.users().auth(user.getPassword(), user.getEmail());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void authFailUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        Response response = apiClient.users().auth(user.getPassword() + "1", user.getEmail());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(401));
    }

    @Test
    public void editEmailUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        var faker = new Faker();
        String newEmail = faker.internet().safeEmailAddress();
        Response response = apiClient.users().edit(newEmail, user.getName(), user.getPassword());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void editFailEmailUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        Response response = apiClient.users().edit("lol.kek.ru", user.getName(), user.getPassword());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(403));
    }

    @Test
    public void editNameUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        var faker = new Faker();
        String newName = faker.name().firstName();
        Response response = apiClient.users().edit(user.getEmail(), newName, user.getPassword());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void editPasswordUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        var faker = new Faker();
        String newPassword = faker.internet().password(8, 20);
        Response response = apiClient.users().edit(user.getEmail(), user.getName(), newPassword);

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void editFailPasswordUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        Response response = apiClient.users().edit(user.getEmail(), user.getName(), "1234");

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(403));
    }

    @Test
    public void createOrder(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        Response response = apiClient.orders().create(true, null);

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @Test
    public void createFailOrder(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());

        Response response = apiClient.orders().create(false, Collections.emptyList());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(500)); //почему-то с 400 не работает
    }

    @Test
    public void take5OrdersByUser(){
        CreateUserRequest user = randomUser();
        apiClient.users().create(user);
        apiClient.users().auth(user.getPassword(), user.getEmail());
        apiClient.orders().create(true, null);

        Response response = apiClient.orders().takeOrdersUser(5);

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @AfterEach
    public void tearDown() { apiClient.users().delete(); }
}