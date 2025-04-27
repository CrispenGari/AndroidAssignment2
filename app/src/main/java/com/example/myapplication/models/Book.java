package com.example.myapplication.models;
import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(
        tableName = "books",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = {"title"}, unique = true),
                @Index(value = {"userId"})
        }
)
public class Book {
    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "sellerName")
    private String sellerName;

    @ColumnInfo(name = "numberOfCopies")
    private int numberOfCopies;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "bankInfo")
    private String bankInfo;

    @ColumnInfo(name = "coverPage")
    private String coverPage;
    @ColumnInfo(name = "userId")
    private Long userId;

    @Override
    @NonNull
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                ", price=" + price +
                ", bankInfo='" + bankInfo + '\'' +
                ", coverPage='" + coverPage + '\'' +
                ", userId=" + userId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }


    public Book(String title, String author, String sellerName, int numberOfCopies, double price, String bankInfo, String coverPage,
                Long userId) {
        this.title = title;
        this.author = author;
        this.sellerName = sellerName;
        this.numberOfCopies = numberOfCopies;
        this.price = price;
        this.bankInfo = bankInfo;
        this.userId = userId;
        this.coverPage = coverPage;
    }
}
