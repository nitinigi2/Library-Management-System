package com.info.view;

import com.info.bean.Book;
import com.info.bean.BookEntity;
import com.info.bean.Customer;
import com.info.utility.CommonUtility;
import com.info.utility.CustomerUtility;
import com.info.utility.LibraryUtility;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.util.Scanner;

public class LibraryView {

    public void view(String librarianId) {
        Scanner scan = new Scanner(System.in);

        LibraryUtility lib = new LibraryUtility();

        CommonUtility commonUtility = new CommonUtility();
        CustomerUtility customerUtility = new CustomerUtility();

        System.out.println("Choose action to be performed: \n");

        long option = 0;


        do {
            System.out.println();
            System.out.println(" 1. Add Customer\n "
                    + "2. Add BookType\n "
                    + "3. Show All available books\n "
                    + "4. Search BookType By Name\n "
                    + "5. Order BookType From Vendor\n "
                    + "6. Return BookType\n "
                    + "7. Issue BookType\n "
                    + "8. Books issued by customer\n "
                    + "9. Show All Customers List\n "
                    + "10. Show Vendors List\n "
                    + "11. Check stock of books vendor has\n "
                    + "12. Exit \n ");


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
                    if (lib.isValidCustomerId(id)) {
                        System.out.println("Customer already exist. Cannot add this customer. ");
                        break;
                    }

                    String password = lib.generatePassword(name, dob);

                    newCustomer.setName(name);
                    newCustomer.setId(id);
                    newCustomer.setMobNumber(mobileNumber);
                    newCustomer.setAddress(address);
                    newCustomer.setPassword(password);

                    //  System.out.println(newCustomer.getName() + "  " + newCustomer.getId() + " " + newCustomer.getMobNumber() + " " + newCustomer.getAddress());

                    lib.addCustomer(newCustomer);
                }

                break;

                case 2:

                    BookEntity newBookEntity = new BookEntity();
                    scan.nextLine();
                    System.out.print("Enter BookType Name: ");
                    String bookName = scan.nextLine().trim();
                    //scan.nextLine();
                    System.out.print("Enter Author Name: ");
                    String authorName = scan.nextLine().trim();
                    //scan.nextLine();
                    System.out.print("Enter BookType ID: ");
                    String bookId = scan.nextLine().trim();
                    int bookQuantity = 0;
                    System.out.print("Enter BookType Quantity. ");
                    try {
                        bookQuantity = Integer.parseInt(scan.next().trim());
                    } catch (Exception e) {
                        System.out.println("Please enter valid no of books. ");
                        break;
                    }

                    System.out.print("Enter BookType Price: ");
                    double price = scan.nextDouble();

                    //System.out.println(bookName);
                    newBookEntity.setBookName(bookName);
                    newBookEntity.setBookId(bookId);
                    newBookEntity.setAuthor(authorName);
                    newBookEntity.setBookQuantity(bookQuantity);
                    newBookEntity.setPrice(price);
                    // System.out.println(bookQuantity + " " + newBookEntity.getBookQuantity());
                    //	System.out.println(newBookEntity.getBookName() + " " + newBookEntity.getBookId() + " " + newBookEntity.getAuthor());
                    lib.addEntityBook(newBookEntity);
                    break;

                case 3:

                    commonUtility.showAllAvailableBooks();
                    break;

                case 4:
                    System.out.print("Enter BookType Name: ");
                    String bookNameToSearch = scan.next().trim();
                    commonUtility.searchBookByName(bookNameToSearch);
                    break;

                case 5:
                    scan.nextLine();
                    String vendorId = "";
                    System.out.print("Enter Vendor Id: ");
                    do {
                        vendorId = scan.nextLine().trim();
                        if (!lib.isValidVendorId(vendorId)) {
                            System.out.println("This vendor id is not valid. ");
                        }
                    } while (!lib.isValidVendorId(vendorId));

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
                    if (lib.orderBook(vendorId, bookname, bookauthor, quant)) {
                        System.out.println("BookType ordered Successfully. ");
                    } else {
                        System.out.println("Please enter correct details. ");
                    }
                    break;
                case 6:
                    System.out.print("Enter Customer Id: ");
                    String cusid = scan.next().trim();
                    //check if customer id is valid or not.
                    if (!lib.isValidCustomerId(cusid)) {
                        System.out.println("Customer id doesn't exist. ");
                        break;
                    }

                    System.out.print("Enter Book barCode ");
                    String barCode = scan.next().trim();

                    if (!lib.isValidBarCode(barCode)) {
                        System.out.println("Book doesn't exist");
                        break;
                    }
                    if (lib.returnBook(cusid, barCode)) {
                        System.out.println("BookType Returned Successfully. ");
                    } else {
                        System.out.println("Some error Occurred. Cannot returned book.....");
                    }
                    break;
                case 7:
                    System.out.print("Enter Customer Id: ");
                    String cuid = scan.next().trim();
                    //check if customer id is valid or not.
                    if (!lib.isValidCustomerId(cuid)) {
                        System.out.println("Customer id doesn't exist. ");
                        break;
                    }

                    System.out.print("Enter Book bar code: ");
                    int barcode=0;
                    try {
                        barcode = Integer.parseInt(scan.next().trim());
                    }

                    if (!lib.isValidBarCode(barcode)) {
                        System.out.println("Book doesn't exist");
                        break;
                    }
                    lib.issueBook(cuid, barcode);
                    break;

                case 8:
                    System.out.print("Enter customer Id: ");
                    String id = scan.next().trim();
                    customerUtility.booksIssuedByMe(id);

                    break;

                case 9:
                    lib.showAllCustomerInfo();
                    break;

                case 10:
                    lib.showVendorList();
                    break;
                case 11:
                    String vendorid = "";
                    do {
                        System.out.print("Enter vendor id: ");
                        vendorid = scan.next().trim();
                        if (!lib.isValidVendorId(vendorid)) System.out.println("Not a valid vendor id. ");
                    } while (!lib.isValidVendorId(vendorid));

                    lib.checkStockInVendor(vendorid);
                    break;

                default:
                    break;
            }

        } while (option != 12);

        if (option == 12) System.out.println("Logout Successfully. ");
    }
}
