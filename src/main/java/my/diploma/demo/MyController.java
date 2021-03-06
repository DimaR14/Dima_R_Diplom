package my.diploma.demo;

import my.diploma.demo.objects.*;
import my.diploma.demo.service.UserService;
import my.diploma.demo.service.MyTransactionService;
import my.diploma.demo.service.TitleService;
import my.diploma.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class MyController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyTransactionService myTransactionService;

    @Autowired
    private TitleService titleService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/add_user")
    public String addBookkeeper() {
        return "add_user";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    @RequestMapping("/")
    public String index(Model model) throws ParseException {

        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        List<User> users = userList(userService.findByAccount(account));

        model.addAttribute("users", users);
        model.addAttribute("login", getCurrentUser().getUsername());
        model.addAttribute("balance", account.getBalance());

        //return "index";
        return  "main";
    }

    @RequestMapping("/transaction")
    public String transaction(Model model) {

        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        List<User> users = userList(userService.findByAccount(account));

        model.addAttribute("users", users);
        return "transaction";
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    public String newBookkeeper(Model model,
                                @RequestParam String name,
                                @RequestParam String token) {
        if (userService.findByTelegramToken(token) != null) {
            model.addAttribute("exists", "Token already exists");
            return "add_user";
        }
        User user = new User(name, token);
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        user.setAccount(account);
        userService.addUser(user);
        return "redirect:/";
    }

    @RequestMapping("/report")
    public String myReport(Model model) throws ParseException {
        String date = getNewDate(new Date());
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        HashMap<String, String> month = myMonth(myTransactionService.getAllTransactionFromAccount(account));
        List<MyTransaction> list = getAllForMonth(myTransactionService.getAllTransactionFromAccount(account), date, "+");
        List<MyTransaction> list2 = getAllForMonth(myTransactionService.getAllTransactionFromAccount(account), date, "-");
        double profit = sum(title(list), "+");
        double spend = sum(title(list2), "-");
        ;
        model.addAttribute("months", month);
        model.addAttribute("my_report1", title(list));
        model.addAttribute("my_report2", title(list2));
        model.addAttribute("profit", profit);
        model.addAttribute("spend", spend);
        return "report";
    }

    @RequestMapping("/month/{key}")
    public String key(@PathVariable(value = "key") String key,
                      Model model) throws ParseException {
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        HashMap<String, String> month = myMonth(myTransactionService.getAllTransactionFromAccount(account));
        List<MyTransaction> list = getAllForMonth(myTransactionService.getAllTransactionFromAccount(account), key, "+");
        List<MyTransaction> list2 = getAllForMonth(myTransactionService.getAllTransactionFromAccount(account), key, "-");
        double profit = sum(title(list), "+");
        double spend = sum(title(list2), "-");
        model.addAttribute("months", month);
        model.addAttribute("my_report1", title(list));
        model.addAttribute("my_report2", title(list2));
        model.addAttribute("profit", profit);
        model.addAttribute("spend", spend);
        return "report";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newUser(Model model,
                          @RequestParam String login,
                          @RequestParam String password) {
        if (accountService.findByLogin(login) != null) {
            model.addAttribute("exists", true);
            return "register";
        }
        accountService.addAccount(login, password);
        return "login";
    }


    @RequestMapping("/my_transaction")
    public String MyTransaction(Model model) {
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        List<Title> titles = getTitles(account);
        model.addAttribute("title", titles);
        model.addAttribute("my_transaction", myTransactionService.getAllTransactionFromAccount(account));
        return "my_transaction";
    }

    @RequestMapping("/title/{name}")
    public String title(@PathVariable(value = "name") String name,
                        Model model) {
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        List<Title> titles = getTitles(account);
        List<MyTransaction> transactionsFromTitle = myTransactionService.getAllTransactionByTitleAndAccount(name, account);
        if(name.equals("all")){
            transactionsFromTitle = myTransactionService.getAllTransactionFromAccount(account);
        }
        model.addAttribute("my_transaction", transactionsFromTitle);
        model.addAttribute("title", titles);
        return "my_transaction";
    }

    @RequestMapping(value = "/add_transaction", method = RequestMethod.POST)
    public String addTransaction(Model model,
                                 @RequestParam String sum,
                                 @RequestParam String attribute,
                                 @RequestParam String title,
                                 @RequestParam(value = "user") long user_id,
                                 @RequestParam String date) throws ParseException {
        Account account = accountService.findByLogin(getCurrentUser().getUsername());
        if (sum.isEmpty() || attribute.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("user",userList(userService.findByAccount(account)));
            return "transaction";
        }
        if (checkSum(model, sum) == false) {
            model.addAttribute("user", userList(userService.findByAccount(account)));
            return "transaction";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date trDate;
        String trTitle;

        if (date.isEmpty()) {
            trDate = new Date();
        } else {
            trDate = format.parse(date);
        }

        if (title.isEmpty()) {
            trTitle = "other";
        } else {
            trTitle = title;
        }
        User user = userService.findById(user_id);
        MyTransaction myTransaction = new MyTransaction(trTitle, trDate, attribute, Double.valueOf(sum));
        userService.addMyTransaction(myTransaction, user);
        Title userTitle = new Title(trTitle);
        userTitle.setAccount(user.getAccount());
        if (titleService.existsByNameAndAccount(userTitle.getName(), user.getAccount().getId()) == false) {
            titleService.addTitle(userTitle);
        }
        accountService.refresh(account.getLogin());
        return "redirect:/";
    }

    @RequestMapping(value = "/transaction/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "toDelete[]", required = false)
                                               long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            for (long id : toDelete) {
                MyTransaction transaction = myTransactionService.findById(id);
                User user = transaction.getUser();
                userService.deleteMyTransaction(transaction, user);
                if (transaction.getAttribute().equals("+")) {
                    user.setBalance(user.getBalance() - transaction.getSum());
                } else {
                    user.setBalance(user.getBalance() + transaction.getSum());
                }
            }
        myTransactionService.deleteTransactions(toDelete);
        accountService.refresh(getCurrentUser().getUsername());


        return new ResponseEntity<>(HttpStatus.OK);
    }

    private org.springframework.security.core.userdetails.User getCurrentUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private List<MyTransaction> getAllForMonth(List<MyTransaction> list, String date, String attribute) throws ParseException {
        List<MyTransaction> list2 = new ArrayList<>();
        for (MyTransaction transaction : list) {
            if (transaction.getAttribute().equals(attribute) && getNewDate(transaction.getDate()).equals(date)) {
                list2.add(transaction);
            }
        }
        return list2;
    }

    private String getNewDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String newDate = format.format(date);
        return newDate;
    }

    private HashMap<String, String> month() {
        HashMap<String, String> month = new HashMap<>();
        month.put("01", "january");
        month.put("02", "february");
        month.put("03", "march");
        month.put("04", "april");
        month.put("05", "may");
        month.put("06", "june");
        month.put("07", "july");
        month.put("08", "august");
        month.put("09", "september");
        month.put("10", "october");
        month.put("11", "november");
        month.put("12", "december");
        return month;
    }

    private HashMap<String, String> myMonth(List<MyTransaction> transactions) {
        HashMap<String, String> date = new HashMap<>();
        for (MyTransaction transaction : transactions) {
            if (date.get(getNewDate(transaction.getDate())) == null) {
                date.put(getNewDate(transaction.getDate()), getValue(getNewDate(transaction.getDate())));
            }
        }
        return date;
    }

    private String getValue(String date) {
        HashMap<String, String> month = month();
        String[] strings = date.split("-");
        String value = strings[0] + " " + month.get(strings[1]);
        return value;
    }

    private List<Report> title(List<MyTransaction> list) {
        List<Report> reports = new ArrayList<>();
        List<Title> list3 = titleService.getAllByAccount(accountService.findByLogin(getCurrentUser().getUsername()).getId());

        for (Title title : list3) {
            reports.add(new Report(title.getName(), 0));
        }

        for (MyTransaction transaction : list) {
            for (Report report : reports) {
                if (transaction.getTitle().equalsIgnoreCase(report.getTitle()) && transaction.getAttribute().equals("+")) {
                    report.setSum(report.getSum() + transaction.getSum());
                }
                if (transaction.getTitle().equalsIgnoreCase(report.getTitle()) && transaction.getAttribute().equals("-")) {
                    report.setSum(report.getSum() - transaction.getSum());
                }
            }
        }

        List<Report> report = new ArrayList<>();
        for (Report r : reports) {
            if (r.getSum() != 0) {
                report.add(r);
            }
        }
        return report;
    }

    private double sum(List<Report> reports, String attribute) {
        double sum = 0;
        if (attribute.equals("+")) {
            for (Report report : reports) {
                sum = sum + report.getSum();
            }
        }
        if (attribute.equals("-")) {
            for (Report report : reports) {
                sum = sum - report.getSum();
            }
        }
        return sum;
    }

    private boolean checkSum(Model model, String sum) {
        try {
            double trSum = Double.valueOf(sum);
            return true;
        } catch (NumberFormatException ex) {
            model.addAttribute("wrong", true);
            return false;
        }
    }

    private List<User> userList(List<User> list) {
        List<User> users = new ArrayList<>();
        for (User user : list) {
            if (user.getTelegramToken() != null) {
                users.add(user);
            }
        }
        return users;
    }


    private List<Title> getTitles(Account account) {
        List<Title> titles = new ArrayList<>();
        List<Title> list = titleService.getAllByAccount(account.getId());
        for (Title title : list) {
            if (!myTransactionService.getAllTransactionByTitleAndAccount(title.getName(), account).isEmpty()) {
                titles.add(title);
            }
        }
        return titles;
    }


}

