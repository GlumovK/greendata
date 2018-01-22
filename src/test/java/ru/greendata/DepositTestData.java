package ru.greendata;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.greendata.model.Deposit;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.greendata.model.AbstractBaseEntity.START_SEQ;
import static ru.greendata.web.json.JsonUtil.writeValue;

public class DepositTestData {
    public static final int DEPOSIT1_ID = START_SEQ + 5;
    public static final int DEPOSIT2_ID = START_SEQ + 6;
    public static final int DEPOSIT3_ID = START_SEQ + 7;
    public static final int DEPOSIT4_ID = START_SEQ + 8;


    public static final Deposit DEPOSIT1 = new Deposit(DEPOSIT1_ID, of(2000, 03, 30), 35, 240);
    public static final Deposit DEPOSIT2 = new Deposit(DEPOSIT2_ID, of(2016, 11, 8), 4, 48);
    public static final Deposit DEPOSIT3 = new Deposit(DEPOSIT3_ID, of(2018, 01, 01), 7, 12);
    public static final Deposit DEPOSIT4 = new Deposit(DEPOSIT4_ID, of(2017, 03, 03), 8, 24);

    public static final List<Deposit> DEPOSITS = Arrays.asList(DEPOSIT1, DEPOSIT2, DEPOSIT3, DEPOSIT4);

    public static void assertMatch(Deposit actual, Deposit expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Deposit> actual, Deposit... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Deposit> actual, Iterable<Deposit> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Deposit... expected) {
        return content().json(writeValue((Arrays.asList(expected))));
    }

    public static ResultMatcher contentJson(Deposit expected) {
        return content().json(writeValue((expected)));
    }
}
