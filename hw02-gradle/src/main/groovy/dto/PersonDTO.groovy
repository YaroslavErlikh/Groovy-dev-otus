package dto

import java.time.LocalDateTime

class PersonDTO {
    private String firstName
    private String secondName
    private String patronymicName
    private LocalDateTime birthDate
    private String cardNumber
    private String amount
    private Integer rubles
    private Integer kopeck

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getSecondName() {
        return secondName
    }

    void setSecondName(String secondName) {
        this.secondName = secondName
    }

    String getPatronymicName() {
        return patronymicName
    }

    void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName
    }

    LocalDateTime getBirthDate() {
        return birthDate
    }

    void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate
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
        amount = rubles.toString() + ',' + kopeck.toString()
    }

    Integer getRubles() {
        return rubles
    }

    void setRubles(Integer rubles) {
        this.rubles = rubles
        computeAmount()
    }

    Integer getKopeck() {
        return kopeck
    }

    void setKopeck(Integer kopeck) {
        this.kopeck = kopeck
        computeAmount()
    }
}
