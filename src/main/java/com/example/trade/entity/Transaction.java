package com.example.trade.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_account_id", nullable = false)
    private BankAccount buyerAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_account_id", nullable = false)
    private BankAccount sellerAccount;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType = TransactionType.PAYMENT;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;
    
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    
    @Column(name = "reference_number", unique = true, length = 50)
    private String referenceNumber;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    // 枚舉
    public enum TransactionType {
        PAYMENT, REFUND
    }
    
    public enum TransactionStatus {
        PENDING, SUCCESS, FAILED
    }
    
    // JPA生命週期方法
    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
        if (referenceNumber == null) {
            referenceNumber = generateReferenceNumber();
        }
    }
    
    // 建構子
    public Transaction() {}
    
    public Transaction(Order order, BankAccount buyerAccount, BankAccount sellerAccount, BigDecimal amount) {
        this.order = order;
        this.buyerAccount = buyerAccount;
        this.sellerAccount = sellerAccount;
        this.amount = amount;
    }
    
    // 業務方法
    private String generateReferenceNumber() {
        return "TXN" + System.currentTimeMillis();
    }
    
    public boolean isSuccessful() {
        return transactionStatus == TransactionStatus.SUCCESS;
    }
    
    public boolean canBeRefunded() {
        return isSuccessful() && transactionType == TransactionType.PAYMENT;
    }
    
    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    
    public BankAccount getBuyerAccount() { return buyerAccount; }
    public void setBuyerAccount(BankAccount buyerAccount) { this.buyerAccount = buyerAccount; }
    
    public BankAccount getSellerAccount() { return sellerAccount; }
    public void setSellerAccount(BankAccount sellerAccount) { this.sellerAccount = sellerAccount; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    
    public TransactionStatus getTransactionStatus() { return transactionStatus; }
    public void setTransactionStatus(TransactionStatus transactionStatus) { this.transactionStatus = transactionStatus; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}