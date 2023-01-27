package dao.repository.repo

import dao.entity.Person
import dao.entity.moneybox.interfaces.MoneyBox
import dao.repository.init.InitATM
import dto.enumbox.EnumMoneyBox

@Singleton
class Repo {

    private final Map<Integer, Person> personList = InitATM.initPerson()

    private final Map<EnumMoneyBox, MoneyBox> moneyBoxList = InitATM.initMoneyBox()

    Map<Integer, Person> getPersonList() {
        return personList
    }

    Map<EnumMoneyBox, MoneyBox> getMoneyBoxList() {
        return moneyBoxList
    }
}
