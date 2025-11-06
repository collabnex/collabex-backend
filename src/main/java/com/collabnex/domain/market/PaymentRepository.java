
package com.collabnex.domain.market;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByProviderAndProviderPaymentId(String provider, String providerPaymentId);
}
