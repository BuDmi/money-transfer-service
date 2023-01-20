package ru.netology.moneytransfer;

import org.junit.jupiter.api.Test;
import ru.netology.moneytransfer.exception.ErrorInputData;
import ru.netology.moneytransfer.model.Card;


import static org.junit.jupiter.api.Assertions.assertThrows;

class CardTest {
    @Test
    public void checkErrorInputDataExceptionAfterProcessCardNumber() {
        assertThrows(ErrorInputData.class, () -> new Card("", "12/25", "123"));
        assertThrows(ErrorInputData.class, () -> new Card("124214", "12/25", "123"));
    }

    @Test
    public void checkErrorInputDataExceptionAfterProcessValidTill() {
        assertThrows(ErrorInputData.class, () -> new Card("1111111111111111", "", "123"));
        assertThrows(ErrorInputData.class, () -> new Card("1111111111111111", "12/5", "123"));
        assertThrows(ErrorInputData.class, () -> new Card("1111111111111111", "12/19", "123"));
    }

    @Test
    public void checkErrorInputDataExceptionAfterProcessCvc() {
        assertThrows(ErrorInputData.class, () -> new Card("1111111111111111", "12/19", ""));
        assertThrows(ErrorInputData.class, () -> new Card("1111111111111111", "12/19", "13"));
    }
}
