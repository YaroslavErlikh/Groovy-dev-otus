package service.interfaces

import dto.MoneyBoxDTO
import dto.PersonDTO

interface ATMService {

    boolean authorize(PersonDTO personDTO)

    PersonDTO findPerson(PersonDTO personDTO)

    PersonDTO receiveMoney(PersonDTO personDTO, MoneyBoxDTO moneyBoxDTO)

    MoneyBoxDTO dispensedMoney(String cardNumber, MoneyBoxDTO moneyBoxDTO)

    PersonDTO getCashBalance(PersonDTO personDTO)

    String getAliquot()
}