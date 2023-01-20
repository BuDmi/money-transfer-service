package ru.netology.moneytransfer;

import org.junit.jupiter.api.Test;
import ru.netology.moneytransfer.exception.ErrorInputData;
import ru.netology.moneytransfer.model.Amount;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {

    @Test
    public void checkErrorInputDataException() {
        assertThrows(ErrorInputData.class, () -> new Amount(null, "RUB"));
        assertThrows(ErrorInputData.class, () -> new Amount(-1, "RUB"));
    }
}
