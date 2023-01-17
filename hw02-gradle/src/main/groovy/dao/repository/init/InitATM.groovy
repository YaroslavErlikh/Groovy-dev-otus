package dao.repository.init

import dao.entity.Person
import dao.entity.moneybox.MoneyBox100
import dao.entity.moneybox.MoneyBox1000
import dao.entity.moneybox.MoneyBox50
import dao.entity.moneybox.MoneyBox500
import dao.entity.moneybox.MoneyBox5000
import dao.entity.moneybox.interfaces.MoneyBox
import dto.enumbox.EnumMoneyBox

import java.time.LocalDateTime

class InitATM {

    static Map<Integer, Person> initPerson(){
        Map<Integer, Person> mapPerson = new HashMap<>()

        Person person1 = new Person()
        person1.setId(1)
        person1.setFio('Semenov Viktor Mikhailovich')
        person1.setCardNumber("1234 4321")
        person1.setBirthDay(LocalDateTime.parse("1984-07-13T23:00:00"))
        person1.addRubles(20000)
        person1.addKopeck(75)
        mapPerson.put(1, person1)

        Person person2 = new Person()
        person2.setId(2)
        person2.setFio('Petrov Ivan Sergeevich')
        person2.setCardNumber("1234 4322")
        person2.setBirthDay(LocalDateTime.parse("1982-05-12T23:00:00"))
        person2.addRubles(70000)
        person2.addKopeck(50)
        mapPerson.put(2, person2)

        Person person3 = new Person()
        person3.setId(3)
        person3.setFio('Ivanyuk Tatiana Viktorovna')
        person3.setCardNumber("1234 4323")
        person3.setBirthDay(LocalDateTime.parse("1986-11-22T23:00:00"))
        person3.addRubles(2000000)
        person3.addKopeck(93)
        mapPerson.put(3, person3)

        Person person4 = new Person()
        person4.setId(4)
        person4.setFio('Serova Elena Vseslavovna')
        person4.setCardNumber("1234 4324")
        person4.setBirthDay(LocalDateTime.parse("1994-03-05T23:00:00"))
        person4.addRubles(650000)
        person4.addKopeck(03)
        mapPerson.put(4, person4)

        Person person5 = new Person()
        person5.setId(5)
        person5.setFio('Preobrajenskiy Ilya Pavlovich')
        person5.setCardNumber("1234 4325")
        person5.setBirthDay(LocalDateTime.parse("1954-10-24T23:00:00"))
        person5.addRubles(350000)
        person5.addKopeck(18)
        mapPerson.put(5, person5)

        return mapPerson
    }

    static Map<EnumMoneyBox, MoneyBox> initMoneyBox() {
        Map<EnumMoneyBox, MoneyBox> mapMoneyBox = new HashMap<>()

        MoneyBox50 moneyBox50 = new MoneyBox50()
        moneyBox50 + 3000
        mapMoneyBox.put(EnumMoneyBox.MONEY_BOX_50, moneyBox50)

        MoneyBox100 moneyBox100 = new MoneyBox100()
        moneyBox100 + 2000
        mapMoneyBox.put(EnumMoneyBox.MONEY_BOX_100, moneyBox100)

        MoneyBox500 moneyBox500 = new MoneyBox500()
        moneyBox500 + 1000
        mapMoneyBox.put(EnumMoneyBox.MONEY_BOX_500, moneyBox500)

        MoneyBox1000 moneyBox1000 = new MoneyBox1000()
        moneyBox1000 + 750
        mapMoneyBox.put(EnumMoneyBox.MONEY_BOX_1000, moneyBox1000)

        MoneyBox5000 moneyBox5000 = new MoneyBox5000()
        moneyBox5000 + 500
        mapMoneyBox.put(EnumMoneyBox.MONEY_BOX_5000, moneyBox5000)

        return mapMoneyBox
    }
}
