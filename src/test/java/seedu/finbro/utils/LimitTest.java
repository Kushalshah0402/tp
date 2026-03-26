package seedu.finbro.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LimitTest {
    /**
     * @author natmloclam
     */
    @Test
    void toFileFormat_twoDP_twoDP() {
        Limit.setLimit(450.00);
        assertEquals("LIMIT | 450.00\n", Limit.toFileFormat());
    }
    /**
     * @author natmloclam
     */
    @Test
    void toFileFormat_twoDP_roundedTwoDP() {
        Limit.setLimit(450.019);
        assertEquals("LIMIT | 450.02\n",  Limit.toFileFormat());
    }
}
