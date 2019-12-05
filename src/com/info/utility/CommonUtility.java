package com.info.utility;

import com.info.bean.BookEntity;

import java.util.ArrayList;
import java.util.Map;

public class CommonUtility{
    LibraryUtility libraryUtility = new LibraryUtility();


    Map<String, ArrayList<BookEntity>> bookListByName = libraryUtility.getSearchBookByBookNameList();

    public void showAllAvailableBooks() {
        ArrayList<BookEntity> bookEntities = libraryUtility.getBookList();
        // System.out.println(bookmap);
        System.out.println("All available Books in Library : ");
        System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();
        for (BookEntity bookEntity : bookEntities) {
            System.out.format("%16s%16s%16s%32s", bookEntity.getBookName(), bookEntity.getAuthor(), bookEntity.getBookId(), bookEntity.getBookQuantity());
            System.out.println();
        }

    }

    public void searchBookByName(String bookName){
        if(!bookListByName.containsKey(bookName)){
            System.out.println(bookName + " book is not available. ");
            return;
        }

        System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();
        if(bookListByName.containsKey(bookName)) {
            for(BookEntity bookEntity : bookListByName.get(bookName)){
                System.out.format("%16s%16s%16s%32s", bookEntity.getBookName(), bookEntity.getAuthor(), bookEntity.getBookId(), bookEntity.getBookQuantity());
                System.out.println();
            }
        }
    }


}
