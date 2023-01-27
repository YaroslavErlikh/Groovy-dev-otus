package dto.enumbox

enum EnumMoneyBox {

    MONEY_BOX_50('50'),
    MONEY_BOX_100('100'),
    MONEY_BOX_500('500'),
    MONEY_BOX_1000('1000'),
    MONEY_BOX_5000('5000')

    private final String NOMINAL

    private EnumMoneyBox(String s) {
        NOMINAL = s
    }

    boolean equalsNominal(String nom) {
        return this.NOMINAL == nom
    }

    String nominal() {
        return this.NOMINAL
    }
}