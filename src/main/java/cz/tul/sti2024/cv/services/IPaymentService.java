package cz.tul.sti2024.cv.services;

import cz.tul.sti2024.cv.models.Payment;

public interface IPaymentService {

    void processPayment(Payment payment);
}
