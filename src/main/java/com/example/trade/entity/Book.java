package com.example.trade.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 100)
    private String author;
    
    @Column(length = 20)
    private String isbn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_rating")
    private ConditionRating conditionRating = ConditionRating.GOOD;
    
    @Column(name = "original_price", precision = 8, scale = 2)
    private BigDecimal originalPrice;
    
    @Column(name = "selling_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal sellingPrice;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 1;
    
    @Column(name = "is_available")
    private Boolean isAvailable = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 關聯關係
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookImage> images;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists;
    
    // 枚舉
    public enum ConditionRating {
        NEW, LIKE_NEW, GOOD, FAIR, POOR
    }
    
    // JPA生命週期方法
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // 建構子
    public Book() {}
    
    public Book(User seller, String title, String author, BigDecimal sellingPrice) {
        this.seller = seller;
        this.title = title;
        this.author = author;
        this.sellingPrice = sellingPrice;
    }
    
    // 業務方法
    public boolean isInStock() {
        return isAvailable && stockQuantity > 0;
    }
    
    public boolean hasStock(int quantity) {
        return isInStock() && stockQuantity >= quantity;
    }
    
    public void reduceStock(int quantity) {
        if (hasStock(quantity)) {
            stockQuantity -= quantity;
            if (stockQuantity == 0) {
                isAvailable = false;
            }
        } else {
            throw new IllegalArgumentException("庫存不足");
        }
    }
    
    public void addStock(int quantity) {
        stockQuantity += quantity;
        if (stockQuantity > 0) {
            isAvailable = true;
        }
    }
    
    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public ConditionRating getConditionRating() { return conditionRating; }
    public void setConditionRating(ConditionRating conditionRating) { this.conditionRating = conditionRating; }
    
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<BookImage> getImages() { return images; }
    public void setImages(List<BookImage> images) { this.images = images; }
    
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    
    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> wishlists) { this.wishlists = wishlists; }
}