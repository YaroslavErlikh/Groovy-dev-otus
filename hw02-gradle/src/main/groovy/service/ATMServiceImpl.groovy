package service

import dao.entity.Person
import dao.entity.moneybox.*
import dao.repository.interfaces.RepositoryService
import dto.MoneyBoxDTO
import dto.PersonDTO
import dto.enumbox.EnumMoneyBox
import service.interfaces.ATMService

class ATMServiceImpl implements ATMService {

    private static final String ATM_CAN_NOT_DISPENSED_AMOUNT = 'Unable to pay requested amount'

    private RepositoryService repository

    ATMServiceImpl(RepositoryService repository) {
        this.repository = repository
    }

    @Override
    boolean authorize(PersonDTO personDTO) {
        def person = repository.findPerson(personDTO.cardNumber)
        return person ? true : false
    }

    @Override
    PersonDTO findPerson(PersonDTO personDTO) {
        Person person = repository.findPerson(personDTO.cardNumber)

        return createPersonDto(person)
    }

    @Override
    PersonDTO receiveMoney(PersonDTO personDTO, MoneyBoxDTO moneyBoxDTO) {
        def person = repository.findPerson(personDTO.cardNumber)
        if (!person) {
            //Exception
            return null
        }
        def boxes = repository.getMoneyBoxes()
        moneyBoxDTO.getBoxes().forEach { box, count ->
            if (EnumMoneyBox.MONEY_BOX_50.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_50) as MoneyBox50) + count
                person.addRubles(count * 50)
            }
            if (EnumMoneyBox.MONEY_BOX_100.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_100) as MoneyBox100) + count
                person.addRubles(count * 100)
            }
            if (EnumMoneyBox.MONEY_BOX_500.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_500) as MoneyBox500) + count
                person.addRubles(count * 500)
            }
            if (EnumMoneyBox.MONEY_BOX_1000.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_1000) as MoneyBox1000) + count
                person.addRubles(count * 1000)
            }
            if (EnumMoneyBox.MONEY_BOX_5000.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_5000) as MoneyBox5000) + count
                person.addRubles(count * 5000)
            }
        }
        if (!repository.saveMoney(boxes) &&
                !repository.save(person)) {
            //Exception
            return null
        }
        return createPersonDto(person)
    }

    @Override
    MoneyBoxDTO dispensedMoney(String cardNumber, MoneyBoxDTO moneyBoxDTO) {
        def person = repository.findPerson(cardNumber)
        if (!person) {
            //Exception
            return null
        }

        Map<String, Integer> dispensedMap = new HashMap<>()
        def boxes = repository.getMoneyBoxes()

        Integer maxAmount = 0
        boxes.values().forEach {box -> maxAmount = maxAmount + box.amount}

        Integer getMoney = 0
        moneyBoxDTO.boxes.forEach {box, count ->
            EnumMoneyBox.MONEY_BOX_50.equalsNominal(box) ?
                getMoney = 50 * count : null
            EnumMoneyBox.MONEY_BOX_100.equalsNominal(box) ?
                    getMoney = 100 * count : null
            EnumMoneyBox.MONEY_BOX_500.equalsNominal(box) ?
                    getMoney = 500 * count : null
            EnumMoneyBox.MONEY_BOX_1000.equalsNominal(box) ?
                    getMoney = 1000 * count: null
            EnumMoneyBox.MONEY_BOX_5000.equalsNominal(box) ?
                    getMoney = 5000 * count: null
        }

        if (getMoney >= maxAmount) {
            //Exception
            MoneyBoxDTO result = new MoneyBoxDTO()
            result.setMessage(ATM_CAN_NOT_DISPENSED_AMOUNT)
            return result
        }

        moneyBoxDTO.getBoxes().forEach { box, count ->
            if (EnumMoneyBox.MONEY_BOX_50.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_50) as MoneyBox50) - count
                person.subtractRubles(count * 50)
                dispensedMap.put(EnumMoneyBox.MONEY_BOX_50.nominal(), count)
            }
            if (EnumMoneyBox.MONEY_BOX_100.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_100) as MoneyBox100) - count
                person.subtractRubles(count * 100)
                dispensedMap.put(EnumMoneyBox.MONEY_BOX_100.nominal(), count)
            }
            if (EnumMoneyBox.MONEY_BOX_500.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_500) as MoneyBox500) - count
                person.subtractRubles(count * 500)
                dispensedMap.put(EnumMoneyBox.MONEY_BOX_500.nominal(), count)
            }
            if (EnumMoneyBox.MONEY_BOX_1000.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_1000) as MoneyBox1000) - count
                person.subtractRubles(count * 1000)
                dispensedMap.put(EnumMoneyBox.MONEY_BOX_1000.nominal(), count)
            }
            if (EnumMoneyBox.MONEY_BOX_5000.equalsNominal(box)) {
                (boxes.get(EnumMoneyBox.MONEY_BOX_5000) as MoneyBox5000) - count
                person.subtractRubles(count * 5000)
                dispensedMap.put(EnumMoneyBox.MONEY_BOX_5000.nominal(), count)
            }
        }
        if (!repository.saveMoney(boxes) &&
                !repository.save(person)) {
            //Exception
            return null
        }

        MoneyBoxDTO dispensedDto = new MoneyBoxDTO()
        dispensedDto.setBoxes(dispensedMap)

        Integer payMoney = 0
        dispensedDto.boxes.forEach {box, count ->
            EnumMoneyBox.MONEY_BOX_50.equalsNominal(box) ?
                    payMoney = payMoney + 50 * count : payMoney
            EnumMoneyBox.MONEY_BOX_100.equalsNominal(box) ?
                    payMoney = payMoney + 100 * count : payMoney
            EnumMoneyBox.MONEY_BOX_500.equalsNominal(box) ?
                    payMoney = payMoney + 500 * count : payMoney
            EnumMoneyBox.MONEY_BOX_1000.equalsNominal(box) ?
                    payMoney = payMoney + 1000 * count : payMoney
            EnumMoneyBox.MONEY_BOX_5000.equalsNominal(box) ?
                    payMoney = payMoney + 5000 * count : payMoney
        }

        dispensedDto.setMessage("Pay ${payMoney}")
        return dispensedDto
    }

    @Override
    PersonDTO getCashBalance(PersonDTO personDTO) {
        def person = repository.findPerson(personDTO.cardNumber)
        if (!person) {
            //Exception
            return null
        }

        personDTO.setRubles(person.rubles)
        personDTO.setKopeck(person.kopeck)
        return personDTO
    }

    @Override
    String getAliquot() {
        String result = null
        repository.getMoneyBoxes().forEach {nominal, box ->
            if (!result && box.amount > 0) {
                result = nominal.nominal()
            }
        }
        return result
    }

    private static PersonDTO createPersonDto(Person person) {
        PersonDTO personDTO = new PersonDTO()

        def fio = person.fio.split(" ")
        personDTO.setFirstName(fio[0])
        personDTO.setSecondName(fio[1])
        personDTO.setPatronymicName(fio[2])
        personDTO.setBirthDate(person.birthDay)
        personDTO.setCardNumber(person.cardNumber)
        personDTO.setRubles(person.getRubles())
        personDTO.setKopeck(person.getKopeck())

        return personDTO
    }
}
