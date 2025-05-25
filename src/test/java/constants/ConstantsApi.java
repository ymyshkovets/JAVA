package constants;

import models.User;

public class ConstantsApi {

    public static final String BASE_URL = "https://petstore.swagger.io";

    public static final User DEFAULT_USER = new User(0, "string", "string", "string", "string", "string", "string", 0);

    public static User expectedUser = new User(9223372036854767242L, "string", "string", "string", "string", "string", "string", 0);
}
