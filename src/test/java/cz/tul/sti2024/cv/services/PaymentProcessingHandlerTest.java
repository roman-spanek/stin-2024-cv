package cz.tul.sti2024.cv.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.tul.sti2024.cv.models.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

public class PaymentProcessingHandlerTest {
    private PaymentProcessingHandler paymentProcessingHandler;
    private CardPaymentService cardPaymentServiceMock;
    private CashPaymentService cashPaymentServiceMock;
    private PaymentTransformations paymentTransformationsMock;

    @BeforeEach
    void init() {
        paymentTransformationsMock = Mockito.mock(PaymentTransformations.class);
        cardPaymentServiceMock = Mockito.mock(CardPaymentService.class);
        cashPaymentServiceMock = Mockito.mock(CashPaymentService.class);
        paymentProcessingHandler = new PaymentProcessingHandler(cardPaymentServiceMock,
                cashPaymentServiceMock, paymentTransformationsMock);
    }

    @Test
    void mockTransformationCallTest() throws IOException {
        Payment payment = new Payment();
        payment.setPaymentType("CASH");
        Mockito.when(paymentTransformationsMock.transformJsonIntoPayment(Mockito.anyString())).thenReturn(payment);
        String payload = Files.readString(new ClassPathResource("inputdata/card_valid_payment.json").getFile().toPath());
        paymentProcessingHandler.processPayment(payload);

        Mockito.verify(paymentTransformationsMock, Mockito.times(1)).transformJsonIntoPayment(payload);
    }


    @Test
    void mockTransformationThrowsExceptionTest() throws IOException {
        Mockito.when(paymentTransformationsMock.transformJsonIntoPayment(Mockito.anyString())).thenReturn(new Payment());
        String payload = Files.readString(new ClassPathResource("inputdata/card_valid_payment.json").getFile().toPath());
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            paymentProcessingHandler.processPayment(payload);
        });


        Mockito.verify(paymentTransformationsMock, Mockito.times(1)).transformJsonIntoPayment(payload);
        Assertions.assertEquals(NullPointerException.class, thrown.getClass());

    }

    @Test
    void mockTransformationExceptionCallTest() throws IOException {
        Mockito.doThrow(new Exception("Test")).when(paymentTransformationsMock.transformJsonIntoPayment(Mockito.anyString()));
        String payload = Files.readString(new ClassPathResource("inputdata/card_valid_payment.json").getFile().toPath());
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            paymentProcessingHandler.processPayment(payload);
        });

        Mockito.verify(paymentTransformationsMock, Mockito.times(1)).transformJsonIntoPayment(payload);
        Assertions.assertEquals(NullPointerException.class, thrown.getClass());
    }
}
