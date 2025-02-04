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
        return accountDAO.insertAccount(null);
    }

    // returns the account with given username
    public Account getAccountByUsername(Account account) {
        return accountDAO.getAccountByUsername(account);
    }
}