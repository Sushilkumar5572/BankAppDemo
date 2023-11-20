package com.wipro.bank.dao;

import java.sql.*;


import com.mysql.jdbc.CommunicationsException;
import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {
        Connection con;

        public boolean validateAccount(String accountNumber) {
                boolean res = false;

                try {
                        con = DBUtil.getDBConnection();
                        String query = "select * from account_tbl where account_number = " + accountNumber;
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        if (rs.next()) {
                                return true;
                        }
                        con.close();
                } catch (CommunicationsException e) {
                        System.out.println("Database Not Found :(");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return res;
        }

        public float findBalance(String accountNumber) {
                float acc_bal = -1;
                try {
                        con = DBUtil.getDBConnection();
                        String query = "select balance from account_tbl where account_number =" + accountNumber;
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        rs.next();
                        acc_bal = rs.getFloat(1);
                        con.close();
                } catch (CommunicationsException e) {
                        System.out.println("Database Not Found :(");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return acc_bal;
        }

        public int generateSequenceNumber() {
                int sequence = 0;
                return sequence;
        }

        public boolean transferMoney(TransferBean tf) {
                int cnt = 0,cnt2 = 0,cnt3 = 0;
                try {
                        con = DBUtil.getDBConnection();
                        String query1 = "insert into transfer_tbl (from_acc_no,to_acc_no,transaction_date,transaction_amount) values (?,?,?,?)";
                        PreparedStatement pstmt = con.prepareStatement(query1);
                        pstmt.setString(1, tf.getFromAccountNumber());
                        pstmt.setString(2, tf.getToAccountNumber());
                        pstmt.setDate(3, (Date) tf.getDateOfTransaction());
                        pstmt.setFloat(4, tf.getAmount());
                        cnt = pstmt.executeUpdate();
                        if (cnt > 0) {
                                String query2 = "update account_tbl set balance = balance + "+tf.getAmount()+" where account_number = "+tf.getToAccountNumber();
                                String query3 = "update account_tbl set balance = balance - "+tf.getAmount()+" where account_number = "+tf.getFromAccountNumber();
                                Statement st = con.createStatement();
                                cnt2 = st.executeUpdate(query2);
                                cnt3 = st.executeUpdate(query3);
                                st.close();
                                if (cnt2>0 && cnt3>0) {
                                        return true;
                                }else{
                                        return false;
                                }
                                // st.execute(query2);
                                // String query3 = "update account_tbl set bal"
                        }
                        con.close();

                } catch (CommunicationsException e) {
                        System.out.println("Database Not Found :(");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }
}
