☕ The Rustic Beans Cafe — Billing System

A Professional Java Swing POS (Point-of-Sale) Application
Generate bills, create PDF receipts, and manage daily sales with a beautiful cafe-style desktop interface.

✨ Overview

The Rustic Beans Cafe Billing System is a desktop billing software designed for cafes and small restaurants.
It helps staff quickly select menu items, calculate bills with taxes and service charges, and generate professional PDF receipts in seconds.

Built using Java Swing with a clean object-oriented architecture, this project is perfect for learning desktop development and real-world billing logic.

🚀 Key Features
🖥️ Full-screen modern cafe-style UI
🛒 25 menu items across 5 categories
🧮 Real-time billing calculation
📄 Automatic PDF receipt generation
👤 Optional customer details in receipt
💾 Daily sales logging (daily_sales.txt)
🔄 One-click reset for new orders
🎨 Warm cafe themed design
🛠️ Tech Stack
Technology	Usage
Java (JDK 8+)	Core application
Java Swing	GUI interface
iText PDF 5.x	PDF receipt generation
File Handling	Daily sales logging
📂 Project Structure
Cafe Billing System
│
├── src/
│   ├── Main.java
│   ├── CafeBillingUI.java
│   ├── MenuItem.java
│   ├── BillCalculator.java
│   ├── PDFGenerator.java
│   └── FileSaver.java
│
├── lib/
│   └── itextpdf.jar
│
├── images/
│   └── (menu & logo images)
│
└── daily_sales.txt
🍽️ Menu Categories
☕ Drinks
🎂 Cakes
🍔 Fast Food
🥐 Bakery & Coolers
🌟 Premium Specials

Total 25 pre-loaded items ready to use.

🧮 Billing Formula
Subtotal = Sum of (Price × Quantity)
Tax = 5% of Subtotal
Service Charge = 10% of Subtotal
Grand Total = Subtotal + Tax + Service Charge
⚙️ How to Run

2️⃣ Compile Project

Windows

javac -cp ".;lib/itextpdf.jar" -d bin src/*.java

Linux / Mac

javac -cp ".:lib/itextpdf.jar" -d bin src/*.java
3️⃣ Run Application

Windows

java -cp ".;bin;lib/itextpdf.jar" src.Main

Linux / Mac

java -cp ".:bin:lib/itextpdf.jar" src.Main
💡 How To Use
Launch the application
Select items using checkboxes
Adjust quantity using + / − buttons
Click TOTAL to calculate bill
Click PRINT RECEIPT to generate PDF
Click SAVE to log daily sales
Click RESET for new order
🧾 PDF Receipt Includes
Cafe logo & header
Date and time
Customer details (optional)
Itemized order table
Tax & service charge breakdown
Grand total & thank-you message

Receipts are saved in Downloads folder automatically.

🎯 Project Purpose

This project demonstrates:

Desktop GUI development
Object-Oriented Programming
File handling in Java
Third-party library integration
Real-world POS billing logic

 Author

Made with love and coffee ☕
Harshvardhan Gaikwad
