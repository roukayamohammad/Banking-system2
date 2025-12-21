package domain.strategy;

/**
 * واجهة Strategy لحساب الفائدة
 * كل خوارزمية فائدة يجب أن تطبق هذه الواجهة
 */
public interface InterestStrategy {

    /**
     * حساب الفائدة بناءً على الرصيد الحالي
     * @param balance الرصيد الحالي
     * @return قيمة الفائدة
     */
    double calculateInterest(double balance);
}
