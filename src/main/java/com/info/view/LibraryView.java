package com.info.view;

import com.info.bean.Customer;
import com.info.utility.CommonUtility;
import com.info.utility.CustomerUtility;
import com.info.utility.LibraryUtility;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Scanner;

public class LibraryView {

    public void view(String librarianId, SessionFactory sessionFactory) throws IOException {

        Scanner scan = new Scanner(System.in);

        LibraryUtility lib = new LibraryUtility();

        CommonUtility commonUtility = new CommonUtility();
        CustomerUtility customerUtility = new CustomerUtility();

        System.out.println("Choose action to be performed: \n");

        long option = 0;


        do {
            System.out.println();
            System.out.println(" 1. Add Customer\n "
                    + "2. Show All available books\n "
                    + "3. Search BookType By Name\n "
                    + "4. Order BookType From Vendor\n "
                    + "5. Return BookType\n "
                    + "6. Issue BookType\n "
                    + "7. Books issued by customer\n "
                    + "8. Show All Customers List\n "
                    + "9. Show Vendors List\n "
                    + "10. Check types of books vendor has\n "
                    + "11.Search book by subject name\n "
                    + "12. Search book by author name\n "
                    + "13. Exit \n ");


            System.out.print("Enter an option: ");
            try {
                option = Long.parseLong(scan.next());
            } catch (Exception e) {
                System.out.println("Please enter a valid option.");
            }
            switch ((int)option) {
                case 1: {
                    Customer newCustomer = new Customer();
                    scan.nextLine();
                    String name = "";
                    do {
                        System.out.print("Enter Name: ");
                        name = scan.nextLine().trim();

                        if (!lib.isValidName(name)) {
                            System.out.println("Name is not valid. Please enter a valid name. ");
                        }
                    } while (!lib.isValidName(name));
                    //scan.nextLine();
                    String dob = "";
                    do {
                        System.out.print("Enter Date of Birth: ");
                        dob = scan.nextLine().trim();

                        if (!lib.isValidDob(dob)) {
                            System.out.println("Please enter valid date in dd mm yyyy format. ");
                        }
                    } while (!lib.isValidDob(dob));

                    long mobileNumber = 0;
                    do {
                        System.out.print("Enter Mobile Number: ");

                        try {
                            mobileNumber = Long.parseLong(scan.next().trim());
                            if (!lib.isValidMobNo(mobileNumber)) {
                                System.out.println("Please enter valid mobile number.");
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter valid mobile number.");
                        }
                    } while (!lib.isValidMobNo(mobileNumber));
                    scan.nextLine();

                    String address = "";
                    do {
                        System.out.print("Enter Address: ");
                        address = scan.nextLine().trim();

                        if (!lib.isValidAddress(address)) {
                            System.out.println("Please enter valid address. ");
                        }
                    } while (!lib.isValidAddress(address));
                    // generate id

                    String id = lib.generateId(name, mobileNumber, dob, address);
                    //check if customer id is valid or not.
                    if (lib.isValidCustomerId(id, sessionFactory)) {
                        System.out.println("Customer already exist. Cannot add this customer. ");
                        break;
                    }

                    String password = lib.generatePassword(name, dob);

                    newCustomer.setName(name);
                    newCustomer.setId(id);
                    newCustomer.setDob(dob);
                    newCustomer.setMobNumber(mobileNumber);
                    newCustomer.setAddress(address);
                    newCustomer.setPassword(password);

                    //  System.out.println(newCustomer.getName() + "  " + newCustomer.getId() + " " + newCustomer.getMobNumber() + " " + newCustomer.getAddress());

                    lib.addCustomer(newCustomer, sessionFactory);
                }

                break;

                case 2:
                    commonUtility.showAllAvailableBooks(sessionFactory);
                    break;

                case 3:
                    scan.nextLine();
                    System.out.print("Enter Book Name: ");
                    String bookNameToSearch = scan.nextLine().trim();
                    commonUtility.searchBookByName(bookNameToSearch, sessionFactory);
                    break;

                case 4:
                    scan.nextLine();
                    String vendorId = "";
                    System.out.print("Enter Vendor Id: ");
                    do {
                        vendorId = scan.nextLine().trim();
                        if (!lib.isValidVendorId(vendorId, sessionFactory)) {
                            System.out.println("This vendor id is not valid. ");
                        }
                    } while (!lib.isValidVendorId(vendorId, sessionFactory));

                    System.out.print("Enter BookType name ");
                    String bookname = scan.nextLine().trim();
                    System.out.print("Enter BookType author ");
                    String bookauthor = scan.nextLine().trim();
                    System.out.print("Enter Quantity: ");

                    int quant = 0;
                    try {
                        quant = Integer.parseInt(scan.next().trim());
                    } catch (Exception e) {
                        System.out.println("Please enter valid quantity. ");
                        break;
                    }

                    //  System.out.println(vendorId + " " + bookname + " " + bookauthor + " " + quant);
                    if (lib.orderBook(vendorId, bookname, bookauthor, quant, sessionFactory)) {
                        System.out.println("BookType ordered Successfully. ");
                    } else {
                        System.out.println("Please enter correct details. ");
                    }
                    break;
                case 5:
                    System.out.print("Enter Customer Id: ");
                    String cusid = scan.next().trim();
                    //check if customer id is valid or not.
                    if (!lib.isValidCustomerId(cusid, sessionFactory)) {
                        System.out.println("Customer id doesn't exist. ");
                        break;
                    }

                    System.out.print("Enter Book barCode ");
                    int barCode=0;
                    try{
                        barCode=Integer.parseInt(scan.next());
                    }catch (Exception e){
                        System.out.println("Enter valid bar code");
                        break;
                    }

                    lib.returnBook(cusid, barCode, sessionFactory);
                    break;
                case 6:
                    System.out.print("Enter Customer Id: ");
                    String cuid = scan.next().trim();

                    System.out.print("Enter Book bar code: ");
                    int barcode = 0;
                    try{
                        barcode = Integer.parseInt(scan.next().trim());
                    }catch (Exception e){
                        System.out.println("Invalid bar code");
                        break;
                    }
                    lib.issueBook(cuid, barcode, sessionFactory);
                    break;

                case 7:
                    System.out.print("Enter customer Id: ");
                    String id = scan.next().trim();
                    customerUtility.booksIssuedByMe(id, sessionFactory);

                    break;

                case 8:
                    lib.showAllCustomerInfo(sessionFactory);
                    break;

                case 9:
                    lib.showVendorList(sessionFactory);
                    break;
                case 10:
                    String vendor_id = "";
                    do {
                        System.out.print("Enter vendor id: ");
                        vendor_id = scan.next().trim();
                        if (!lib.isValidVendorId(vendor_id, sessionFactory)) System.out.println("Not a valid vendor id. ");
                    } while (!lib.isValidVendorId(vendor_id, sessionFactory));

                    lib.checkStockInVendor(vendor_id, sessionFactory);
                    break;

                case 11:
                    scan.nextLine();
                    System.out.print("Enter Subject Name: ");
                    String bookSubjectToSearch = scan.nextLine().trim();
                    commonUtility.searchBySubject(bookSubjectToSearch, sessionFactory);
                    break;

                case 12:
                    scan.nextLine();
                    System.out.print("Enter Author Name: ");
                    String authorName = scan.nextLine().trim();
                    commonUtility.searchByAuthor(authorName, sessionFactory);
                    break;

                default:
                    break;
            }

        } while (option != 13);

        if (option == 13) System.out.println("Logout Successfully. ");
    }
}
