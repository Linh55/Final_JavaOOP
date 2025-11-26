package AccountManager.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import AccountManager.Account;

public class AccountDatabase {
    private static AccountDatabase accountDB = new AccountDatabase();
    private ArrayList<Account> accounts = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    private AccountDatabase(){}

    public static AccountDatabase getAccountDB(){ return accountDB;}

    //Hiển thị tất cả accounts
    public void showAllAccounts(){
        if (accounts.size() == 0){
            System.out.println("Danh sách tài khoản trống!");
            return;
        }
        for (Account a : accounts){
            System.out.println("Tên đăng nhập: " + a.getUsername() +
                                "\nMật khẩu: " + a.getPassword() + "\n");
        }
    }

    //Thêm tài khoản
    public void addAccount(String username, String password){
        int oldSize = accounts.size();
        Account newAccount = new Account(username, password);
        accounts.add(newAccount);
        if (oldSize < accounts.size()){
            System.out.println("Đã thêm tài khoản thành công!");
        } else {
            System.out.println("Lỗi! Thêm tài khoản không thành công!");
        }
    }

    //Tìm kiếm tài khoản theo tên đăng nhập
    public Account findAccountByUsername (String username){
        for (Account a : accounts){
            if (a.getUsername().equals(username)){
                return a;
            }
        }
        return null;
    }

    //Xóa tài khoản
    public void deleteAccount(String username){
        Account a = accountDB.findAccountByUsername(username);
        if (a != null) accounts.remove(a);
    }

    //Dùng hashing mã hóa mật khẩu
    public String hashSHA256(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            //Chuyển đổi byte sang hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    //Kiểm tra đăng nhập
    public boolean checkLogin(String username, String password){
        Account a = accountDB.findAccountByUsername(username);
        if (a == null){
            System.out.println("Sai tên đăng nhập!");
            return false;
        }
        String savedPassword = a.getPassword();
        if (!password.equals(savedPassword)){
            System.out.println("Sai mật khẩu!");
            return false;
        }
        return true;
    }
}
