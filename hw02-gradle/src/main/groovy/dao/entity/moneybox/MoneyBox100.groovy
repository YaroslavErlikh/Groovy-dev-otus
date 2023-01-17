package dao.entity.moneybox

import dao.entity.moneybox.interfaces.MoneyBox
import dao.entity.moneybox.interfaces.PlusMinus

class MoneyBox100 implements PlusMinus, MoneyBox {
    private final String NOMINAL = "100"
    private int count

    @Override
    void plus(int count) {
        this.count = this.count + count
    }

    @Override
    void minus(int count) {
        this.count = this.count - count
    }

    String getNOMINAL() {
        return NOMINAL
    }

    int getCount() {
        return count
    }

    @Override
    Integer getAmount() {
        return count * 100
    }
}
