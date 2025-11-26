package AccountManager.service;

import AccountManager.Account;
import AccountManager.data.AccountDatabase;

import java.util.Scanner;

public class deleteAccount {
    private AccountDatabase accountDB = AccountDatabase.getAccountDB();
    String username;
    Scanner sc = new Scanner(System.in);

    public void delete(){
        System.out.print("Nhập tên đăng nhập tài khoản muốn xóa: ");
        username = sc.nextLine();
        Account a = accountDB.findAccountByUsername(username);
        if(a == null){
            System.out.println("Không tìm thấy tài khoản " + username);
            return;
        }
        accountDB.deleteAccount(username);
        System.out.println("Đã xóa tài khoản thành công!");
    }
}
