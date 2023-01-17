package dao.entity

import java.time.LocalDateTime

class Person {
    private Integer id
    private String fio
    private LocalDateTime birthDay
    private String cardNumber
    private String amount
    private Integer rubles = 0
    private Integer kopeck = 0

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getFio() {
        return fio
    }

    void setFio(String fio) {
        this.fio = fio
    }

    LocalDateTime getBirthDay() {
        return birthDay
    }

    void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay
    }

    String getCardNumber() {
        return cardNumber
    }

    void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber
    }

    String getAmount() {
        return amount
    }

    void computeAmount() {
        rubles.toString() + ',' + kopeck.toString()
    }

    Integer getRubles() {
        return rubles
    }

    void addRubles(Integer rubles) {
        this.rubles = this.rubles + rubles
        computeAmount()
    }

    void subtractRubles(Integer rubles) {
        this.rubles = this.rubles - rubles
        computeAmount()
    }

    Integer getKopeck() {
        return kopeck
    }

    void addKopeck(Integer kopeck) {
        this.kopeck = this.kopeck + kopeck
        computeAmount()
    }
}
