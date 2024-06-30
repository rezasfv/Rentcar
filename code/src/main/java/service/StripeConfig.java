package service;

import com.stripe.Stripe;

public class StripeConfig {
    public static void initialize() {
        Stripe.apiKey = "sk_test_51PJzXqRxoQCZUBajQCPsgUroQx6rtyjyTogml8YwKCspc1A6lAN2PhUJdXMkRWGlBg2TRMvwsZ5HtlZb5oe0FdZV00SuL0fT9e";
    }
}
