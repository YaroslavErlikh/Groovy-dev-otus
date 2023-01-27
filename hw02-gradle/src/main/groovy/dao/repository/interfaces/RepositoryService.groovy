package dao.repository.interfaces

import dao.entity.Person
import dao.entity.moneybox.interfaces.MoneyBox
import dto.enumbox.EnumMoneyBox

interface RepositoryService {

    Person save(Person person)

    Person findPerson(int id)

    Person findPerson(String cardNumber)

    boolean saveMoney(Map<EnumMoneyBox, MoneyBox> moneyBox)

    Map<EnumMoneyBox, MoneyBox> getMoneyBoxes()
}