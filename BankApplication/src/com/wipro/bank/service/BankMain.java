package com.wipro.bank.service;

import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;

public class BankMain {
        static BankDAO bd = new BankDAO();


        public static void main(String[] args) throws SQLException {
                
                
                Date now = new java.sql.Date(System.currentTimeMillis());
                String account_number;
                String from_acc_no;
                String to_ac_no;
                float amount;


                while (true) {
                        Scanner sc2 = new Scanner(System.in);
                        System.out.println("Welcome to Bank of Maharashtra ");
                        System.out.println("1. View Balance");
                        System.out.println("2. Transfer Money");
                        System.out.println("3. Exit");
                        System.out.print("Enter your choice:");
                        int choice = sc2.nextInt();
                        if(choice == 3){
                                System.out.println("Closing Application...");
                                break;
                        }
                        switch (choice) {
                                case 1:
                                        sc2 = new Scanner(System.in);
                                        System.out.print("Please Enter your Account Number:");
                                        account_number = sc2.nextLine();
                                        System.out.println(checkBalance(account_number));
                                        break;
                                case 2:
                                        sc2 = new Scanner(System.in);
                                        System.out.print("Enter your Account No : ");
                                        from_acc_no = sc2.nextLine();
                                        System.out.print("Enter Beneficiary Account Number : ");
                                        to_ac_no = sc2.nextLine();
                                        System.out.print("Enter the Amount to transfer : ");
                                        amount = sc2.nextFloat();
                                        TransferBean tf = new TransferBean(from_acc_no, to_ac_no, now, amount);
                                        System.out.println(transfer(tf));
                                        break;
                                default:
                                        System.out.println("Please enter correct choice ");
                        }
                }
                

        }

        public static String checkBalance(String account_number){
                if(bd.validateAccount(account_number)){
                        return "Your Account balance is : "+bd.findBalance(account_number);
                }
                else{
                        return "Account not found";
                }
        }

        public static String transfer(TransferBean transferBean){
                
                if (bd.transferMoney(transferBean)) {
                        return "Transaction successful!!!";
                }else{
                        return "Transaction Failed :(";
                }
        }


}
