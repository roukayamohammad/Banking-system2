package bankingsystem.adapter;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import domain.adapter.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * اختبارات لنمط Adapter Pattern
 */
public class AdapterTest {

    @Test
    @DisplayName("إنشاء أداپتر للبنك الخارجي")
    public void testBankAdapterCreation() {
        // Given
        ExternalBank legacyBank = new ExternalBank();

        // When
        PaymentProcessor adapter = new BankAdapter(legacyBank);

        // Then
        assertNotNull(adapter);
        assertInstanceOf(BankAdapter.class, adapter);
    }

    @Test
    @DisplayName("تحويل الدفع عبر الأداپتر")
    public void testPaymentThroughAdapter() {
        // يمكن استخدام Mockito لمحاكاة ExternalBank
        // لكن للتبسيط سنختبر فقط أن الأداپتر يعمل

        ExternalBank legacyBank = new ExternalBank();
        PaymentProcessor adapter = new BankAdapter(legacyBank);

        // هذا الاختبار يتحقق فقط من عدم حدوث أخطاء
        assertDoesNotThrow(() -> adapter.pay(1000.0),
                "يجب أن يتم الدفع دون أخطاء");
    }

    @Test
    @DisplayName("الأداپتر يوفر واجهة موحدة")
    public void testAdapterProvidesUnifiedInterface() {
        // هذا يختبر أننا يمكننا استخدام PaymentProcessor
        // دون معرفة تفاصيل ExternalBank

        PaymentProcessor processor = new BankAdapter(new ExternalBank());

        // يمكن استبدال BankAdapter بأي PaymentProcessor آخر
        assertNotNull(processor);
    }
}