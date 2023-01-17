package dao.repository

import dao.entity.Person
import dao.entity.moneybox.interfaces.MoneyBox
import dao.repository.interfaces.RepositoryService
import dao.repository.repo.Repo
import dto.enumbox.EnumMoneyBox

class RepositoryServiceImpl implements RepositoryService {

    private final Repo repo

    RepositoryServiceImpl(Repo repo) {
        this.repo = repo
    }

    @Override
    Person save(Person person) {
        try {
            Integer idPers = null
            repo.personList.forEach { id, pers ->
                if (person.cardNumber == pers.cardNumber) {
                    idPers = pers.id
                }
            }
            repo.personList.put(idPers, person)
            return person
        } catch (Exception e) {
            return null
        }
    }

    @Override
    Person findPerson(int id) {
        Person personRepo = null
        repo.personList.forEach { idRep, pers ->
            if (idRep == id) {
                personRepo = pers
            }
        }
        return personRepo
    }

    @Override
    Person findPerson(String cardNumber) {
        Person personRepo = null
        repo.personList.forEach { id, pers ->
            if (pers.cardNumber == cardNumber) {
                personRepo = pers
            }
        }
        return personRepo
    }

    @Override
    boolean saveMoney(Map<EnumMoneyBox, MoneyBox> moneyBox) {
        try {
            moneyBox.forEach { nominal, box ->
                EnumMoneyBox.MONEY_BOX_50.equalsNominal(nominal.nominal()) ?
                        repo.moneyBoxList.put(EnumMoneyBox.MONEY_BOX_50, box) : null
                EnumMoneyBox.MONEY_BOX_100.equalsNominal(nominal.nominal()) ?
                        repo.moneyBoxList.put(EnumMoneyBox.MONEY_BOX_100, box) : null
                EnumMoneyBox.MONEY_BOX_500.equalsNominal(nominal.nominal()) ?
                        repo.moneyBoxList.put(EnumMoneyBox.MONEY_BOX_500, box) : null
                EnumMoneyBox.MONEY_BOX_1000.equalsNominal(nominal.nominal()) ?
                        repo.moneyBoxList.put(EnumMoneyBox.MONEY_BOX_1000, box) : null
                EnumMoneyBox.MONEY_BOX_5000.equalsNominal(nominal.nominal()) ?
                        repo.moneyBoxList.put(EnumMoneyBox.MONEY_BOX_5000, box) : null
            }
            return true
        } catch (Exception e) {
            return false
        }
    }

    @Override
    Map<EnumMoneyBox, MoneyBox> getMoneyBoxes() {
        return repo.moneyBoxList
    }
}
