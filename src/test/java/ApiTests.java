import edu.grade.clients.ApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static edu.grade.generators.UserGenerator.randomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf
public class ApiTests {

    private ApiClient apiClient = new ApiClient();

    @Test
    public void someTest() {
        Response response = apiClient.users().create(randomUser());

        assertThat("Некорректный код ответа", response.statusCode(), equalTo(200));
    }

    @AfterEach
    public void tearDown() {
        apiClient.users().delete();
    }
}
