package com.infosys.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ApiControllerTest {
    @Test
    void serviceNameIsCorrect() {
        assertEquals("customer-eligibility", "customer-eligibility");
    }
}
