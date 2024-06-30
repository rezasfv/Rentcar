package servlet;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PaymentService;
import service.StripeConfig;

import java.io.IOException;


public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long amount = Math.round( (Float) request.getSession().getAttribute("amount") * 100) ; // stipe get amount in cents so we multiply by 100
            StripeConfig.initialize();
            PaymentService paymentService = new PaymentService();
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, "eur");

            response.setContentType("application/json");
            response.getWriter().write(paymentIntent.toJson());
        } catch (StripeException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment processing failed");
        }
    }
}


