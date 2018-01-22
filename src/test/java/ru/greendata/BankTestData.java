package ru.greendata;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.greendata.model.Bank;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.greendata.model.AbstractBaseEntity.START_SEQ;
import static ru.greendata.web.json.JsonUtil.writeValue;

public class BankTestData {

    public static final int SBER_ID = START_SEQ + 3;
    public static final int DEUTCHE_ID = START_SEQ + 4;

    public static final Bank SBER = new Bank(SBER_ID, "Sberbank", "045773603");
    public static final Bank DEUTCHE = new Bank(DEUTCHE_ID, "DeutscheBank", "044525101");

    public static void assertMatch(Bank actual, Bank expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Bank> actual, Bank... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Bank> actual, Iterable<Bank> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Bank... expected) {
        return content().json(writeValue((Arrays.asList(expected))));
    }

    public static ResultMatcher contentJson(Bank expected) {
        return content().json(writeValue((expected)));
    }
}
