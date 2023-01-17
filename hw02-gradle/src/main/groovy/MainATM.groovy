import controller.ATMController
import dao.repository.RepositoryServiceImpl
import dao.repository.interfaces.RepositoryService
import dao.repository.repo.Repo
import dto.MoneyBoxDTO
import dto.PersonDTO
import service.ATMServiceImpl
import service.interfaces.ATMService

class MainATM {

    private static Repo repo
    private static RepositoryService repoService
    private static ATMService service
    private static ATMController controller
    private static PersonDTO personDTO

    static void main(String[] args) {
        println("Hello ATM Ololo")
        initATM()
        println("Insert your card")
        Scanner sc = new Scanner(System.in)

        boolean exit = false
        while (!exit) {
            PersonDTO personDTO = new PersonDTO()
            personDTO.setCardNumber(sc.nextLine())
            if (controller.authorize(personDTO)) {
                this.personDTO = controller.findPerson(personDTO)
                exit = process(sc, exit)
            } else {
                println('Authorization error, insert another card')
            }
        }

    }

    static void initATM() {
        repo = Repo.instance
        repoService = new RepositoryServiceImpl(repo)
        service = new ATMServiceImpl(repoService)
        controller = new ATMController(service)
    }

    private static process(Scanner sc, boolean exit) {
        println("""Hello ${personDTO.secondName} ${personDTO.firstName}

               1 - Balance\n
               2 - Deposit\n
               3 - Dispensed\n
               4 - Exit""")

        def input = sc.nextInt()
        switch (input) {
            case 1:
                println("Balance - ${controller.getCashBalance(personDTO).amount} rubles")
                break
            case 2:
                Map<String, Integer> mapMoney = new HashMap<>()

                println('Count banknotes 50 rubles - ')
                mapMoney.put('50', sc.nextInt())
                println('Count banknotes 100 rubles - ')
                mapMoney.put('100', sc.nextInt())
                println('Count banknotes 500 rubles - ')
                mapMoney.put('500', sc.nextInt())
                println('Count banknotes 1000 rubles - ')
                mapMoney.put('1000', sc.nextInt())
                println('Count banknotes 5000 rubles - ')
                mapMoney.put('5000', sc.nextInt())

                MoneyBoxDTO moneyBoxDTO = new MoneyBoxDTO()
                moneyBoxDTO.setBoxes(mapMoney)
                controller.receiveMoney(personDTO, moneyBoxDTO)
                break
            case 3:
                println("Insert amount aliquot - ${controller.getAliquot()}")
                def getMoney = sc.nextInt()
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
                println("${dispensedBoxDTO.message}")
                break
            case 4:
                println("Exit from system, card return")
                personDTO = null
                exit = true
                break
            default: println('Error input')
        }
        return exit
    }
}