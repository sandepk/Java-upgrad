package com.sessions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class LibraryTest {
    Library library;

//    @BeforeAll
//    public static void beforeAll() {
//        System.out.println("Before All" + '\n');
//    }
//
    @BeforeEach
    public void beforeEach() {
        library = new Library();
    }

//    @AfterEach
//    public void afterEach() {
//        System.out.println("After Each" + '\n');
//    }

//    @AfterAll
//    public static void afterAll() {
//        System.out.println("After all");
//    }

    @Test
    public void default_number_of_books_in_Library_is_initially_1() {

        int totalNumberOfBooks  = library.getBooks().size();
        System.out.println(totalNumberOfBooks);
        assertEquals(1, totalNumberOfBooks);
    }

    @Test
    public void adding_to_catalogue_should_increase_book_size_by_1_and_new_id_should_be_2(
    ) {

        Book newlyBookCreated = library.addToCatalogue("You can win", "Shiv Khera", 300, 350.50);
        Integer totalBooks = library.getBooks().size();
        List<Book> availableBooks = library.getBooks();

        assertEquals(2, newlyBookCreated.getId());

        assertEquals(2, library.getBooks().size());

        assertThat(totalBooks, equalTo(2));

        assertThat(availableBooks, hasItem(newlyBookCreated));

    }

    @Test
    public void findBookByName_when_searching_book_name_existing_in_library_should_return_book() {

        String bookNameToBeSearched = "The God Of Small Things";
        Book searchedBook = library.findBookByName(bookNameToBeSearched);
        assertNotNull(searchedBook);
    }

    @Test
    public void findBookByName_when_searching_book_not_existing_in_library_should_return_null() {

        Book book = library.findBookByName("Some invalid name");
        assertNull(book);
    }

    @Test
    public void calculateBookRent_should_return_2_when_number_of_days_is_4() {

        RentedBook rentedBook = Mockito.mock(RentedBook.class);
        LocalDate fourDaysBeforeFromToday = LocalDate.now().minusDays(4);

        Mockito.when(rentedBook.getRentedDate()).thenReturn(fourDaysBeforeFromToday);

        Double calculatePrice = library.calculateBookRent(rentedBook);
        assertThat(calculatePrice, equalTo(Double.valueOf(2)));
        Mockito.verify(rentedBook, Mockito.times(2)).getRentedDate();
    }

    @Test
    public  void calculateBookRent_should_return_6_when_number_of_days_are_6() {
        RentedBook rentedBook = Mockito.mock(RentedBook.class);
        LocalDate sixDaysBeforeFromToday = LocalDate.now().minusDays(6);
        Mockito.when(rentedBook.getRentedDate()).thenReturn(sixDaysBeforeFromToday);

        Double calculatePrice = library.calculateBookRent(rentedBook);
        assertThat(calculatePrice, equalTo(Double.valueOf(6)));
    }

    //TDO

    // when returning book with amount
        // The receipt should be returned, it should have
            // the date of receipt - should be current date
            // book name
            // amount given , actual amount
            // amountToBeReturned
    // and the book should be available again in library

    // if the amount provided is less than amount
        // An exception should be thrown mentioning lower amount

    @Test
    public void when_returning_book_receipt_should_be_returned() {

        RentedBook rentedBook = library.rent("The God Of Small Things");

        Integer numberOfBooksInLibraryBeforeReturn = library.getBooks().size();
        Double givenAmount = 3.0;


        LocalDate getFourDaysBeforeFromToday = LocalDate.now().minusDays(4);
        rentedBook.setRentedDate(getFourDaysBeforeFromToday);
        Receipt receipt = library.returnBook(rentedBook, givenAmount);


        assertNotNull(receipt);
        assertThat(receipt.bookName,equalTo("The God Of Small Things"));
        assertThat(receipt.givenAmount, equalTo(3.0));
        assertThat(receipt.actualAmount, greaterThan(0.0));
        Double expectedAmountToBeReturned = receipt.givenAmount - receipt.actualAmount;
        assertThat(receipt.amountToBeReturned, equalTo(expectedAmountToBeReturned));
        Integer numberOfBooksInLibraryAfterReturn = library.getBooks().size();
        assertThat(numberOfBooksInLibraryAfterReturn, equalTo(numberOfBooksInLibraryBeforeReturn + 1));


    }

    @Test
    public void when_amount_provided_isLower_than_actual_amount_An_exception_to_be_thrown() {
        RentedBook rentedBook = library.rent("The God Of Small Things");
        Double givenAmount = 1.0;

        LocalDate getFourDaysBeforeFromToday = LocalDate.now().minusDays(4);
        rentedBook.setRentedDate(getFourDaysBeforeFromToday);

        assertThrows(LowerAmountException.class, () -> {
            library.returnBook(rentedBook, givenAmount);
        });
    }



















}