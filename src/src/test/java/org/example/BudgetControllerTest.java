package org.example;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetControllerTest {

    // 1. Happy Path
    @Test
    public void calculate_ValidRequestWithoutInsurance_ReturnsOkWithCorrectTotal() {
        BudgetController controller = new BudgetController();
        BudgetRequest request = new BudgetRequest();
        request.setDailyCost(100.0);
        request.setDays(5);
        request.setIncludeInsurance(false);

        ResponseEntity response = controller.calculate(request);

        assertEquals(200, response.getStatus());

        Map<String, Double> body = (Map<String, Double>) response.getBody();
        assertEquals(500.0, body.get("TotalAmount"));
    }

    // 2. ТЕСТ НА ПОИСК БАГА (Страховка 5% из Части 2 ТЗ)
    @Test
    public void calculate_WithInsurance_ReturnsOkWithFivePercentPremium() {
        BudgetController controller = new BudgetController();
        BudgetRequest request = new BudgetRequest();
        request.setDailyCost(100.0);
        request.setDays(5);
        request.setIncludeInsurance(true);

        ResponseEntity response = controller.calculate(request);

        Map<String, Double> body = (Map<String, Double>) response.getBody();


        assertEquals(525.0, body.get("TotalAmount"));
    }


    @Test
    public void calculate_NegativeDays_ReturnsBadRequest() {
        BudgetController controller = new BudgetController();
        BudgetRequest request = new BudgetRequest();
        request.setDailyCost(100.0);
        request.setDays(-1); // Ломаем данные
        request.setIncludeInsurance(false);

        ResponseEntity response = controller.calculate(request);

        assertEquals(400, response.getStatus());
        assertEquals("Количество дней должно быть больше нуля.", response.getBody());
    }


    @Test
    public void calculate_ZeroDailyCost_ReturnsOkWithZeroTotal() {
        BudgetController controller = new BudgetController();
        BudgetRequest request = new BudgetRequest();
        request.setDailyCost(0.0);
        request.setDays(10);
        request.setIncludeInsurance(true);

        ResponseEntity response = controller.calculate(request);

        Map<String, Double> body = (Map<String, Double>) response.getBody();
        assertEquals(0.0, body.get("TotalAmount"));
    }

    @Test
    public void calculate_FloatingPointCost_CalculatesCorrectlyToPennies() {
        BudgetController controller = new BudgetController();
        BudgetRequest request = new BudgetRequest();
        request.setDailyCost(100.50); // Копейки
        request.setDays(3);
        request.setIncludeInsurance(true);

        ResponseEntity response = controller.calculate(request);

        Map<String, Double> body = (Map<String, Double>) response.getBody();
        assertEquals(316.575, body.get("TotalAmount"));
    }
}
