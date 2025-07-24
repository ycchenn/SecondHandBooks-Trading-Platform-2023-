# 二手書交易平台 - 數據庫設計文檔

## 概述
本文檔描述了二手書交易平台的數據庫設計，包含實體關係圖(ERD)的詳細說明和各表之間的關聯關係。

## 核心實體

### 1. 用戶 (users)
- **用途**: 存儲所有用戶的基本信息
- **主要字段**: 
  - `id`: 主鍵
  - `username`: 用戶名（唯一）
  - `email`: 電子郵件（唯一）
  - `user_type`: 用戶類型（BUYER, SELLER, BOTH）
  - `phone`, `address`: 聯繫信息
- **關聯**: 一對多關聯到銀行帳戶、書籍、訂單

### 2. 銀行帳戶 (bank_accounts)
- **用途**: 管理用戶的銀行帳戶信息和餘額
- **主要字段**:
  - `account_number`: 帳戶號碼（唯一）
  - `balance`: 帳戶餘額
  - `bank_name`: 銀行名稱
- **關聯**: 多對一關聯到用戶

### 3. 書籍分類 (categories)
- **用途**: 書籍分類管理
- **主要字段**:
  - `name`: 分類名稱（唯一）
  - `description`: 分類描述
- **關聯**: 一對多關聯到書籍

### 4. 書籍 (books)
- **用途**: 存儲所有待售書籍信息
- **主要字段**:
  - `title`: 書名
  - `author`: 作者
  - `isbn`: ISBN號碼
  - `condition_rating`: 書況評級
  - `selling_price`: 售價
  - `stock_quantity`: 庫存數量
- **關聯**: 
  - 多對一關聯到賣家(用戶)
  - 多對一關聯到分類
  - 一對多關聯到書籍圖片、訂單項目、願望清單

### 5. 訂單 (orders)
- **用途**: 管理交易訂單
- **主要字段**:
  - `total_amount`: 訂單總金額
  - `order_status`: 訂單狀態
  - `payment_status`: 付款狀態
  - `shipping_address`: 配送地址
- **關聯**:
  - 多對一關聯到買家和賣家
  - 一對多關聯到訂單項目、交易記錄、評價

### 6. 訂單項目 (order_items)
- **用途**: 訂單的詳細項目
- **主要字段**:
  - `quantity`: 購買數量
  - `unit_price`: 單價
  - `subtotal`: 小計
- **關聯**: 多對一關聯到訂單和書籍

### 7. 交易記錄 (transactions)
- **用途**: 記錄所有金融交易
- **主要字段**:
  - `amount`: 交易金額
  - `transaction_type`: 交易類型（PAYMENT, REFUND）
  - `transaction_status`: 交易狀態
  - `reference_number`: 交易參考號
- **關聯**: 多對一關聯到訂單和買賣雙方銀行帳戶

## 輔助實體

### 8. 書籍圖片 (book_images)
- **用途**: 存儲書籍相關圖片
- **關聯**: 多對一關聯到書籍

### 9. 用戶評價 (reviews)
- **用途**: 用戶間的評價系統
- **關聯**: 多對一關聯到訂單、評價者、被評價者

### 10. 願望清單 (wishlists)
- **用途**: 用戶的書籍收藏功能
- **關聯**: 多對一關聯到用戶和書籍

## 關係圖總結

```
用戶 (users) 1:N 銀行帳戶 (bank_accounts)
用戶 (users) 1:N 書籍 (books) [作為賣家]
用戶 (users) 1:N 訂單 (orders) [作為買家]
用戶 (users) 1:N 訂單 (orders) [作為賣家]

分類 (categories) 1:N 書籍 (books)
書籍 (books) 1:N 書籍圖片 (book_images)
書籍 (books) 1:N 願望清單 (wishlists)

訂單 (orders) 1:N 訂單項目 (order_items)
訂單 (orders) 1:N 交易記錄 (transactions)
訂單 (orders) 1:N 評價 (reviews)

書籍 (books) 1:N 訂單項目 (order_items)
銀行帳戶 (bank_accounts) 1:N 交易記錄 (transactions) [買家帳戶]
銀行帳戶 (bank_accounts) 1:N 交易記錄 (transactions) [賣家帳戶]
```

## 業務規則

1. **用戶類型**: 用戶可以是純買家、純賣家或兼具兩種身份
2. **庫存管理**: 書籍售出後自動減少庫存，庫存為0時標記為不可用
3. **交易流程**: 訂單 → 付款 → 發貨 → 完成 → 評價
4. **餘額檢查**: 付款前檢查買家帳戶餘額是否充足
5. **評價系統**: 交易完成後買賣雙方可互相評價
6. **願望清單**: 每個用戶對同一本書只能添加一次到願望清單

## 索引策略

- 用戶表: `username`, `email` 唯一索引
- 書籍表: `title`, `author`, `isbn`, `seller_id` 索引
- 訂單表: `buyer_id`, `seller_id`, `order_status` 索引
- 交易表: `transaction_date`, `reference_number` 索引

## 文件結構

- `schema.sql`: 完整的數據庫建表語句
- `src/main/java/com/example/trade/entity/`: JPA實體類
- `application.properties`: 數據庫連接配置

## 使用說明

1. 確保MySQL服務正在運行
2. 創建數據庫: `CREATE DATABASE secondhand_books;`
3. 執行`schema.sql`創建表結構
4. 啟動Spring Boot應用，JPA會自動同步實體類到數據庫