package org.example;

import java.util.Map;

public class BudgetController {

    public ResponseEntity calculate(BudgetRequest request) {
        if (request.getDays() <= 0) {
            return ResponseEntity.badRequest("Количество дней должно быть больше нуля.");
        }

        double total = request.getDailyCost() * request.getDays();

        if (request.isIncludeInsurance()) {
            total *= 1.05; // ошибка
        }

        return ResponseEntity.ok(Map.of("TotalAmount", total));
    }
}

class ResponseEntity {
    private final int status;
    private final Object body;

    private ResponseEntity(int status, Object body) {
        this.status = status;
        this.body = body;
    }

    public static ResponseEntity ok(Object body) { return new ResponseEntity(200, body); }
    public static ResponseEntity badRequest(Object body) { return new ResponseEntity(400, body); }

    public int getStatus() { return status; }
    public Object getBody() { return body; }
}
