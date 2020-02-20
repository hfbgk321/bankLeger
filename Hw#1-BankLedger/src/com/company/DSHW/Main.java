package com.company.DSHW;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * This is the main method. User sees this.
 * @author Jacky Chen
 * SBU ID: 112704638
 * Email: Jacky.Chen.6@stonybrook.edu
 */
public class Main {

    public static void main(String[] args) throws Exception{
        GeneralLedger test = new GeneralLedger();
        GeneralLedger backUp =  new GeneralLedger();
        Scanner input = new Scanner(System.in);
        boolean endProgress = false;
        displayMenu();
        while(endProgress == false){
            System.out.print("Enter a Selection: ");
            String choice = input.nextLine();
            switch (choice.toUpperCase()){
                case "A":
                    boolean endAdd = false;
                    while(!endAdd) {
                        try {
                            System.out.print("Enter Date:");
                            String date = input.nextLine();
                            System.out.print("Enter Amount ($): ");
                            double amount = input.nextDouble();
                            System.out.print("Enter Description: ");
                            input.nextLine();
                            String description = input.nextLine();
                            Transaction current = new Transaction(date, amount, description);
                            test.addTransaction(current);
                            displayMenu();
                            endAdd = true;
                        } catch (FullGeneralLedgerException x) {
                            System.out.println(x.getMessage());
                        } catch (InvalidTransationException x) {
                            System.out.println(x.getMessage());
                        } catch (TransactionAlreadyExistsException x) {
                            System.out.println(x.getMessage());
                        }
                    }
                    System.out.println();
                    break;
                case "G":
                    boolean continueAction = true;
                    while(continueAction) {
                        try {
                            DecimalFormat d1 = new DecimalFormat("#.##");
                            System.out.print("Enter Position: ");
                            int pos = input.nextInt();
                            Transaction x = test.getTransaction(pos);
                            System.out.println("No.     Date        Debit   Credit   Description");
                            System.out.println("-------------------------------------------------");
                            if (x.getAmount() < 0) {
                                System.out.println((pos) + "      " + x.getDate() + "              " +
                                        d1.format(Math.abs(x.getAmount())) + "    " + x.getDescription() + ".");
                            } else {
                                System.out.println((pos) + "      " + x.getDate() + "    " +
                                        d1.format(x.getAmount()) + "              " + x.getDescription() + ".");
                            }
                            displayMenu();
                            input.nextLine();
                            continueAction = false;
                        } catch (ArrayIndexOutOfBoundsException y) {
                            System.out.println("Invalid Position");
                        }catch(InvalidLedgerPositionException y1){
                            System.out.println(y1.getMessage());
                        }
                    }
                    System.out.println();
                    break;
                case"R":
                    boolean endRemove = false;
                    while(!endRemove) {
                        try {
                            System.out.print("Enter Position: ");
                            int posOfRemoval = input.nextInt();
                            test.removeTransaction(posOfRemoval);
                            displayMenu();
                            endRemove = true;
                        }
                        catch(InvalidLedgerPositionException x2){
                            System.out.println(x2.getMessage());
                        }
                        catch(ArrayIndexOutOfBoundsException x3){
                            System.out.println("Invalid Position");
                        }
                    }
                    input.nextLine();
                    System.out.println();
                    break;
                case "P":
                    test.printAllTransactions();
                    displayMenu();
                    break;
                case "F":
                    System.out.print("Enter Date: ");
                    String date = input.nextLine();
                    test.filter(test,date);
                    displayMenu();
                    System.out.println();
                    break;
                case"L":
                    System.out.print("Enter Date: ");
                    String dateLook = input.nextLine();
                    System.out.print("Enter Amount ($): ");
                    double amountLook = input.nextDouble();
                    input.nextLine();
                    System.out.print("Enter Description: ");
                    String descriptionLook = input.nextLine();
                    DecimalFormat d1 = new DecimalFormat("#.##");
                    Transaction look = new Transaction(dateLook,amountLook,descriptionLook);
                    if(test.exists(look)){
                        System.out.println("No.     Date        Debit   Credit   Description");
                        System.out.println("-------------------------------------------------");
                        for(int x1 = 0; x1 < test.getLedger().length;x1++){
                            if(test.getLedger()[x1].equals(look)){
                                if(test.getLedger()[x1].getAmount()<0) {
                                    System.out.println((x1+1)+"      "+test.getLedger()[x1].getDate()+"              "+
                                            d1.format(Math.abs(test.getLedger()[x1].getAmount()))+"    "+test.getLedger()[x1].getDescription()+".");
                                }
                                else{
                                    System.out.println((x1+1)+"      "+test.getLedger()[x1].getDate()+"    "+
                                            d1.format(test.getLedger()[x1].getAmount())+"              "+test.getLedger()[x1].getDescription()+".");
                                }
                                break;
                            }

                        }
                    }
                    else{
                        System.out.println("Transaction does not exist");
                    }
                    displayMenu();
                    break;
                case "S":
                    System.out.print("There are "+test.size()+" transactions currently in the ledger");
                    displayMenu();
                    System.out.println();
                    break;
                case "B":
                    backUp = (GeneralLedger) test.clone();
                    System.out.print("Created a backup of the current general ledger.");
                    displayMenu();
                    System.out.println();
                    break;
                case "PB":
                    backUp.printAllTransactions();
                    displayMenu();
                    System.out.println();
                    break;
                case "RB":
                    test = (GeneralLedger) backUp.clone();
                    System.out.println("General Ledger successfully reverted back to the backup copy");
                    displayMenu();
                    System.out.println();
                    break;
                case "CB":
                    boolean same = true;
                    for(int xCB = 0 ; xCB < backUp.getLedger().length; xCB++){
                        if(backUp.getLedger()[xCB] == null && test.getLedger()[xCB] !=null ||
                                backUp.getLedger()[xCB] != null && test.getLedger()[xCB] ==null ){
                            same = false;
                            break;
                        }
                        if(backUp.getLedger()[xCB] == null && test.getLedger()[xCB] ==null){
                            break;
                        }
                        if(!(backUp.getLedger()[xCB].equals(test.getLedger()[xCB]))){
                            same = false;
                            break;
                        }
                    }
                    if(same)
                        System.out.println("The current ledger is the same as the backup copy");
                    else
                        System.out.println("The current ledger is not the same as the backup copy");
                    displayMenu();
                    System.out.println();
                    break;
                case "PF":
                    DecimalFormat d2 = new DecimalFormat("#.##");
                    System.out.println("Financial Data: ");
                    System.out.println("--------------------------------------------------------------");
                    System.out.println("Assets: "+d2.format(test.getTotalDebitAmount()));
                    System.out.println("Liabilities: "+d2.format(test.getTotalCreditAmount()));
                    System.out.println("New Worth: "+d2.format((test.getTotalDebitAmount()-test.getTotalCreditAmount())));
                    displayMenu();
                    System.out.println();
                    break;
                case "Q":
                    System.out.println("Program Terminating Successfully....");
                    endProgress = true;
                    break;
                case "TS":
                    System.out.println(test.toString());
                    break;
                default:
            }
        }
    }

    /**
     * This method displays the menu
     */
    public static void displayMenu(){
        System.out.println("(A) Add Transaction ");
        System.out.println("(G) Get Transaction");
        System.out.println("(R) Remove Transaction");
        System.out.println("(P) Print Transactions in General Ledger");
        System.out.println("(F) Filter by Date");
        System.out.println("(L) Look for Transaction");
        System.out.println("(S) Size");
        System.out.println("(B) Backup");
        System.out.println("(PB) Print Transactions in Backup");
        System.out.println("(RB) Revert to Backup");
        System.out.println("(CB) Compare Backup with Current");
        System.out.println("(PF) Print Financial Information");
        System.out.println("(Q) Quit");
    }
}

