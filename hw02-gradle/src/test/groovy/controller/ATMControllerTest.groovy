package controller

import dao.repository.RepositoryServiceImpl
import dao.repository.interfaces.RepositoryService
import dao.repository.repo.Repo
import dto.MoneyBoxDTO
import dto.PersonDTO
import org.junit.Before
import service.ATMServiceImpl
import service.interfaces.ATMService

class ATMControllerTest extends GroovyTestCase {

    private static Repo repo
    private static RepositoryService repoService
    private static ATMService service
    private static ATMController controller

    @Before
    void init(){
        repo = Repo.instance
        repoService = new RepositoryServiceImpl(repo)
        service = new ATMServiceImpl(repoService)
        controller = new ATMController(service)
    }

    void testAuthorize() {
        PersonDTO personDTOauth = new PersonDTO()
        personDTOauth.setCardNumber('1234 4321')
        assert controller.authorize(personDTOauth)
    }

    void testFindPerson() {
        PersonDTO personDTOfind = new PersonDTO()
        personDTOfind.setCardNumber('1234 4321')
        def person = controller.findPerson(personDTOfind)
        assert person.firstName == 'Semenov'
    }

    void testReceiveMoney() {
        PersonDTO personDTO = new PersonDTO()
        personDTO.setCardNumber('1234 4321')

        Map<String, Integer> mapMoney = new HashMap<>()

        mapMoney.put('50', 5)
        mapMoney.put('100', 3)
        mapMoney.put('500', 1)
        mapMoney.put('1000', 4)
        mapMoney.put('5000', 2)

        MoneyBoxDTO moneyBoxDTO = new MoneyBoxDTO()
        moneyBoxDTO.setBoxes(mapMoney)
        def receiveMoney = controller.receiveMoney(personDTO, moneyBoxDTO)
        def rubles = 5 * 50 + 3 * 100 + 1 * 500 + 4 * 1000 + 2 * 5000 + 20000

        assert receiveMoney == rubles.toString() + ",75"
    }

    void testDispensedMoney() {
        PersonDTO personDTO = new PersonDTO()
        personDTO.setCardNumber('1234 4321')

        def getMoney = 10000
        Integer count5000 = (Integer) (getMoney / 5000)
        if (!count5000) {
            getMoney = getMoney - (count5000 * 5000)
        }
        Integer count1000 = (Integer) (getMoney / 1000)
        if (!count1000) {
            getMoney = getMoney - (count1000 * 1000)
        }
        Integer count500 = (Integer) (getMoney / 500)
        if (!count500) {
            getMoney = getMoney - (count500 * 500)
        }
        Integer count100 = (Integer) (getMoney / 100)
        if (!count100) {
            getMoney = getMoney - (count100 * 100)
        }
        Integer count50 = (Integer) (getMoney / 50)

        Map<String, Integer> mapMoney = new HashMap<>()
        mapMoney.put('50', count50)
        mapMoney.put('100', count100)
        mapMoney.put('500', count500)
        mapMoney.put('1000', count1000)
        mapMoney.put('5000', count5000)

        MoneyBoxDTO moneyBoxDTO = new MoneyBoxDTO()
        moneyBoxDTO.setBoxes(mapMoney)

        def dispensedBoxDTO = controller.dispensedMoney(personDTO.cardNumber, moneyBoxDTO)

        assert dispensedBoxDTO.boxes.values().sum() == 10000

        def person = controller.findPerson(personDTO)
        assert person.amount == '10000,75'
    }

    void testGetCashBalance() {
        PersonDTO personDTO = new PersonDTO()
        personDTO.setCardNumber('1234 4321')

        def person = controller.getCashBalance(personDTO)
        assert person.amount == "20000,75"
    }

    void testGetAliquot() {
        def aliquot = controller.getAliquot()
        assert aliquot == "50"
    }
}
