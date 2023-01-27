package controller

import dto.MoneyBoxDTO
import dto.PersonDTO
import service.interfaces.ATMService

class ATMController {

    private ATMService service

    ATMController(ATMService service) {
        this.service = service
    }

    boolean authorize(PersonDTO personDTO){
        return service.authorize(personDTO)
    }

    PersonDTO findPerson(PersonDTO personDTO) {
        return service.findPerson(personDTO)
    }

    String receiveMoney(PersonDTO personDTO, MoneyBoxDTO moneyBoxDTO) {
        PersonDTO person = service.receiveMoney(personDTO, moneyBoxDTO)
        return person.amount
    }

    MoneyBoxDTO dispensedMoney(String cardNumber, MoneyBoxDTO moneyBoxDTO) {
        return service.dispensedMoney(cardNumber, moneyBoxDTO)
    }

    PersonDTO getCashBalance(PersonDTO personDTO) {
        return service.getCashBalance(personDTO)
    }

    String getAliquot() {
        return service.getAliquot()
    }
}
