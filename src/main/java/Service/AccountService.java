package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4) {
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    public Account verifyLoginAttempt(Account account) {
        return accountDAO.verifyAccount(account);
    }
}