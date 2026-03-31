package seedu.finbro.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
//@@author WangZX2001
public class CurrencyRateTableTest {

    @Test
    void isUnsupportedCurrency_supportedBaseCurrency_false() {
        assertFalse(CurrencyRateTable.isUnsupportedCurrency("SGD"));
    }

    @Test
    void isUnsupportedCurrency_supportedMappedCurrency_false() {
        assertFalse(CurrencyRateTable.isUnsupportedCurrency("USD"));
    }

    @Test
    void isUnsupportedCurrency_lowerCaseSupportedCurrency_false() {
        assertFalse(CurrencyRateTable.isUnsupportedCurrency("eur"));
    }

    @Test
    void isUnsupportedCurrency_unknownCurrency_true() {
        assertTrue(CurrencyRateTable.isUnsupportedCurrency("ABC"));
    }

    @Test
    void convert_sameCurrency_returnsSameAmount() {
        double result = CurrencyRateTable.convert(100.0, "USD", "USD");
        assertEquals(100.0, result, 0.0001);
    }

    @Test
    void convert_sgdToUsd_returnsConvertedAmount() {
        double result = CurrencyRateTable.convert(100.0, "SGD", "USD");
        assertEquals(74.0, result, 0.0001);
    }

    @Test
    void convert_usdToSgd_returnsConvertedAmount() {
        double result = CurrencyRateTable.convert(74.0, "USD", "SGD");
        assertEquals(100.0, result, 0.0001);
    }

    @Test
    void convert_usdToEur_returnsCrossConvertedAmount() {
        double result = CurrencyRateTable.convert(74.0, "USD", "EUR");
        assertEquals(68.0, result, 0.0001);
    }

    @Test
    void convert_unsupportedFromCurrency_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> CurrencyRateTable.convert(100.0, "ABC", "USD"));
    }

    @Test
    void convert_unsupportedToCurrency_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> CurrencyRateTable.convert(100.0, "USD", "XYZ"));
    }
}
