package ru.greendata;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.greendata.model.Client;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.greendata.model.AbstractBaseEntity.START_SEQ;
import static ru.greendata.model.LegalForm.FORM_COMPANY;
import static ru.greendata.model.LegalForm.FORM_PERSON;
import static ru.greendata.web.json.JsonUtil.writeValue;

public class ClientTestData {
    public static final int PUTIN_ID = START_SEQ;
    public static final int TRUMP_ID = START_SEQ + 1;
    public static final int ROGA_ID = START_SEQ + 2;

    public static final Client PUTIN = new Client(PUTIN_ID, "Putin V.V.", "Putin", "Russia, Moscow", FORM_PERSON);
    public static final Client TRUMP = new Client(TRUMP_ID, "Trump D.", "Trump", "USA, Washington", FORM_PERSON);
    public static final Client ROGA = new Client(ROGA_ID, "Roga & Co", "Roga", "Russia, Perm", FORM_COMPANY);

    public static void assertMatch(Client actual, Client expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Client> actual, Client... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Client> actual, Iterable<Client> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Client... expected) {
        return content().json(writeValue((Arrays.asList(expected))));
    }

    public static ResultMatcher contentJson(Client expected) {
        return content().json(writeValue((expected)));
    }
}
