// Report Generation and Printing Functions

// Generate and print customer report
async function printCustomerReport() {
    const reportContent = generateCustomerReportHTML();
    const printWindow = printReport(reportContent, 'Customer Report');
    
    // Load and populate customer data
    try {
        const response = await fetch('customers?action=list', {
            headers: { 'Accept': 'application/json' }
        });
        const result = await response.json();
        
        if (result.success && result.data) {
            populateCustomerReport(printWindow, result.data);
        }
    } catch (error) {
        console.error('Error loading customer data:', error);
    }
}

// Generate and print book report
async function printBookReport() {
    const reportContent = generateBookReportHTML();
    const printWindow = printReport(reportContent, 'Book Inventory Report');
    
    // Load and populate book data
    try {
        const response = await fetch('books?action=list', {
            headers: { 'Accept': 'application/json' }
        });
        const result = await response.json();
        
        if (result.success && result.data) {
            populateBookReport(printWindow, result.data);
        }
    } catch (error) {
        console.error('Error loading book data:', error);
    }
}

// Generate and print bill report
async function printBillReport() {
    const reportContent = generateBillReportHTML();
    const printWindow = printReport(reportContent, 'Bill Report');
    
    // Load and populate bill data
    try {
        const response = await fetch('bills?action=list', {
            headers: { 'Accept': 'application/json' }
        });
        const result = await response.json();
        
        if (result.success && result.data) {
            populateBillReport(printWindow, result.data);
        }
    } catch (error) {
        console.error('Error loading bill data:', error);
    }
}

// Generate and print sales summary report
async function printSalesSummaryReport() {
    const reportContent = generateSalesSummaryReportHTML();
    const printWindow = printReport(reportContent, 'Sales Summary Report');
    
    // Load and populate all data for summary
    try {
        const [customerResponse, bookResponse, billResponse] = await Promise.all([
            fetch('customers?action=list', { headers: { 'Accept': 'application/json' } }),
            fetch('books?action=list', { headers: { 'Accept': 'application/json' } }),
            fetch('bills?action=list', { headers: { 'Accept': 'application/json' } })
        ]);
        
        const customers = await customerResponse.json();
        const books = await bookResponse.json();
        const bills = await billResponse.json();
        
        if (customers.success && books.success && bills.success) {
            populateSalesSummaryReport(printWindow, customers.data, books.data, bills.data);
        }
    } catch (error) {
        console.error('Error loading summary data:', error);
    }
}

// Generic print function
function printReport(content, title) {
    // Create a new window for printing
    const printWindow = window.open('', '_blank', 'width=800,height=600');
    
    printWindow.document.write(`
        <!DOCTYPE html>
        <html>
        <head>
            <title>${title}</title>
            <style>
                @media print {
                    body { margin: 0; }
                    .no-print { display: none !important; }
                    .print-break { page-break-before: always; }
                }
                
                body {
                    font-family: 'Arial', sans-serif;
                    margin: 20px;
                    background: white;
                    color: #333;
                }
                
                .report-header {
                    text-align: center;
                    border-bottom: 3px solid #3498db;
                    padding-bottom: 20px;
                    margin-bottom: 30px;
                }
                
                .report-title {
                    font-size: 28px;
                    font-weight: bold;
                    color: #2c3e50;
                    margin: 0;
                }
                
                .report-subtitle {
                    font-size: 16px;
                    color: #7f8c8d;
                    margin: 10px 0 0 0;
                }
                
                .report-info {
                    display: flex;
                    justify-content: space-between;
                    margin: 20px 0;
                    padding: 15px;
                    background: #f8f9fa;
                    border-radius: 8px;
                }
                
                .report-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin: 20px 0;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                }
                
                .report-table th {
                    background: #34495e;
                    color: white;
                    padding: 12px;
                    text-align: left;
                    font-weight: bold;
                }
                
                .report-table td {
                    padding: 10px 12px;
                    border-bottom: 1px solid #ecf0f1;
                }
                
                .report-table tr:nth-child(even) {
                    background: #f8f9fa;
                }
                
                .report-table tr:hover {
                    background: #e8f4f8;
                }
                
                .status-available { color: #27ae60; font-weight: bold; }
                .status-outofstock { color: #e74c3c; font-weight: bold; }
                .status-paid { color: #27ae60; font-weight: bold; }
                .status-pending { color: #f39c12; font-weight: bold; }
                
                .summary-section {
                    margin: 30px 0;
                    padding: 20px;
                    background: #ecf0f1;
                    border-radius: 8px;
                }
                
                .summary-title {
                    font-size: 20px;
                    font-weight: bold;
                    color: #2c3e50;
                    margin-bottom: 15px;
                }
                
                .summary-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 20px;
                }
                
                .summary-item {
                    text-align: center;
                    padding: 15px;
                    background: white;
                    border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                }
                
                .summary-number {
                    font-size: 24px;
                    font-weight: bold;
                    color: #3498db;
                }
                
                .summary-label {
                    font-size: 14px;
                    color: #7f8c8d;
                    margin-top: 5px;
                }
                
                .print-button {
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    background: #3498db;
                    color: white;
                    border: none;
                    padding: 10px 20px;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 16px;
                }
                
                .print-button:hover {
                    background: #2980b9;
                }
                
                .footer {
                    margin-top: 40px;
                    text-align: center;
                    color: #7f8c8d;
                    font-size: 12px;
                    border-top: 1px solid #ecf0f1;
                    padding-top: 20px;
                }
                
                .data-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin: 20px 0;
                }
                
                .data-table th {
                    background: #34495e;
                    color: white;
                    padding: 12px;
                    text-align: left;
                    font-weight: bold;
                }
                
                .data-table td {
                    padding: 10px 12px;
                    border-bottom: 1px solid #ecf0f1;
                }
                
                .data-table tr:nth-child(even) {
                    background: #f8f9fa;
                }
            </style>
        </head>
        <body>
            ${content}
            <button class="print-button no-print" onclick="window.print()">🖨️ Print Report</button>
        </body>
        </html>
    `);
    
    printWindow.document.close();
    printWindow.focus();
    return printWindow;
}

// Generate customer report HTML
function generateCustomerReportHTML() {
    const currentDate = new Date().toLocaleDateString();
    const currentTime = new Date().toLocaleTimeString();
    
    return `
        <div class="report-header">
            <h1 class="report-title">Customer Report</h1>
            <p class="report-subtitle">Pahana Edu Book Shop</p>
        </div>
        
        <div class="report-info">
            <div>
                <strong>Generated:</strong> ${currentDate} at ${currentTime}
            </div>
            <div>
                <strong>Report Type:</strong> Customer Management
            </div>
        </div>
        
        <div class="summary-section">
            <div class="summary-title">Customer Summary</div>
            <div class="summary-grid">
                <div class="summary-item">
                    <div class="summary-number" id="totalCustomers">Loading...</div>
                    <div class="summary-label">Total Customers</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="activeCustomers">Loading...</div>
                    <div class="summary-label">Active Customers</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="newCustomers">Loading...</div>
                    <div class="summary-label">New This Month</div>
                </div>
            </div>
        </div>
        
        <div id="customerTableContainer">
            <div style="text-align: center; padding: 40px; color: #7f8c8d;">
                <div style="font-size: 18px; margin-bottom: 10px;">📊 Loading Customer Data...</div>
                <div style="font-size: 14px;">Please wait while we fetch the latest information</div>
            </div>
        </div>
        
        <div class="footer">
            <p>This report was generated automatically by Pahana Edu Book Shop Management System</p>
            <p>© ${new Date().getFullYear()} Pahana Edu Book Shop. All rights reserved.</p>
        </div>
    `;
}

// Generate book report HTML
function generateBookReportHTML() {
    const currentDate = new Date().toLocaleDateString();
    const currentTime = new Date().toLocaleTimeString();
    
    return `
        <div class="report-header">
            <h1 class="report-title">Book Inventory Report</h1>
            <p class="report-subtitle">Pahana Edu Book Shop</p>
        </div>
        
        <div class="report-info">
            <div>
                <strong>Generated:</strong> ${currentDate} at ${currentTime}
            </div>
            <div>
                <strong>Report Type:</strong> Inventory Management
            </div>
        </div>
        
        <div class="summary-section">
            <div class="summary-title">Inventory Summary</div>
            <div class="summary-grid">
                <div class="summary-item">
                    <div class="summary-number" id="totalBooks">Loading...</div>
                    <div class="summary-label">Total Books</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="availableBooks">Loading...</div>
                    <div class="summary-label">Available</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="outOfStock">Loading...</div>
                    <div class="summary-label">Out of Stock</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="totalValue">Loading...</div>
                    <div class="summary-label">Total Value</div>
                </div>
            </div>
        </div>
        
        <div id="bookTableContainer">
            <div style="text-align: center; padding: 40px; color: #7f8c8d;">
                <div style="font-size: 18px; margin-bottom: 10px;">📚 Loading Book Data...</div>
                <div style="font-size: 14px;">Please wait while we fetch the latest inventory information</div>
            </div>
        </div>
        
        <div class="footer">
            <p>This report was generated automatically by Pahana Edu Book Shop Management System</p>
            <p>© ${new Date().getFullYear()} Pahana Edu Book Shop. All rights reserved.</p>
        </div>
    `;
}

// Generate bill report HTML
function generateBillReportHTML() {
    const currentDate = new Date().toLocaleDateString();
    const currentTime = new Date().toLocaleTimeString();
    
    return `
        <div class="report-header">
            <h1 class="report-title">Bill Report</h1>
            <p class="report-subtitle">Pahana Edu Book Shop</p>
        </div>
        
        <div class="report-info">
            <div>
                <strong>Generated:</strong> ${currentDate} at ${currentTime}
            </div>
            <div>
                <strong>Report Type:</strong> Billing & Sales
            </div>
        </div>
        
        <div class="summary-section">
            <div class="summary-title">Sales Summary</div>
            <div class="summary-grid">
                <div class="summary-item">
                    <div class="summary-number" id="totalBills">Loading...</div>
                    <div class="summary-label">Total Bills</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="totalRevenue">Loading...</div>
                    <div class="summary-label">Total Revenue</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="paidBills">Loading...</div>
                    <div class="summary-label">Paid Bills</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="pendingBills">Loading...</div>
                    <div class="summary-label">Pending Bills</div>
                </div>
            </div>
        </div>
        
        <div id="billTableContainer">
            <div style="text-align: center; padding: 40px; color: #7f8c8d;">
                <div style="font-size: 18px; margin-bottom: 10px;">🧾 Loading Bill Data...</div>
                <div style="font-size: 14px;">Please wait while we fetch the latest billing information</div>
            </div>
        </div>
        
        <div class="footer">
            <p>This report was generated automatically by Pahana Edu Book Shop Management System</p>
            <p>© ${new Date().getFullYear()} Pahana Edu Book Shop. All rights reserved.</p>
        </div>
    `;
}

// Generate sales summary report HTML
function generateSalesSummaryReportHTML() {
    const currentDate = new Date().toLocaleDateString();
    const currentTime = new Date().toLocaleTimeString();
    
    return `
        <div class="report-header">
            <h1 class="report-title">Sales Summary Report</h1>
            <p class="report-subtitle">Pahana Edu Book Shop</p>
        </div>
        
        <div class="report-info">
            <div>
                <strong>Generated:</strong> ${currentDate} at ${currentTime}
            </div>
            <div>
                <strong>Report Type:</strong> Sales Analysis
            </div>
        </div>
        
        <div class="summary-section">
            <div class="summary-title">Overall Business Summary</div>
            <div class="summary-grid">
                <div class="summary-item">
                    <div class="summary-number" id="totalCustomers">Loading...</div>
                    <div class="summary-label">Total Customers</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="totalBooks">Loading...</div>
                    <div class="summary-label">Total Books</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="totalBills">Loading...</div>
                    <div class="summary-label">Total Bills</div>
                </div>
                <div class="summary-item">
                    <div class="summary-number" id="totalRevenue">Loading...</div>
                    <div class="summary-label">Total Revenue</div>
                </div>
            </div>
        </div>
        
        <div class="summary-section">
            <div class="summary-title">Monthly Performance</div>
            <div id="monthlyChartContainer">
                <div style="text-align: center; padding: 40px; color: #7f8c8d;">
                    <div style="font-size: 18px; margin-bottom: 10px;">📊 Loading Summary Data...</div>
                    <div style="font-size: 14px;">Please wait while we fetch the latest business metrics</div>
                </div>
            </div>
        </div>
        
        <div class="footer">
            <p>This report was generated automatically by Pahana Edu Book Shop Management System</p>
            <p>© ${new Date().getFullYear()} Pahana Edu Book Shop. All rights reserved.</p>
        </div>
    `;
}

// Populate customer report with actual data
function populateCustomerReport(printWindow, customers) {
    const doc = printWindow.document;
    
    // Update summary statistics
    const totalCustomers = customers.length;
    const activeCustomers = customers.filter(c => c.status === 'active' || !c.status).length;
    const thisMonth = new Date().getMonth();
    const newCustomers = customers.filter(c => {
        const customerDate = new Date(c.createdAt || c.registrationDate || Date.now());
        return customerDate.getMonth() === thisMonth;
    }).length;
    
    doc.getElementById('totalCustomers').textContent = totalCustomers;
    doc.getElementById('activeCustomers').textContent = activeCustomers;
    doc.getElementById('newCustomers').textContent = newCustomers;
    
    // Create customer table
    const tableContainer = doc.getElementById('customerTableContainer');
    let tableHTML = `
        <table class="data-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Registration Date</th>
                </tr>
            </thead>
            <tbody>
    `;
    
    customers.forEach(customer => {
        const regDate = customer.createdAt || customer.registrationDate || 'N/A';
        tableHTML += `
            <tr>
                <td>${customer.name || customer.fullName || 'N/A'}</td>
                <td>${customer.email || 'N/A'}</td>
                <td>${customer.phone || 'N/A'}</td>
                <td>${customer.address || 'N/A'}</td>
                <td>${regDate}</td>
            </tr>
        `;
    });
    
    tableHTML += `
            </tbody>
        </table>
    `;
    
    tableContainer.innerHTML = tableHTML;
    
    // Trigger print dialog after data is populated
    setTimeout(() => {
        printWindow.print();
    }, 100);
}

// Populate book report with actual data
function populateBookReport(printWindow, books) {
    const doc = printWindow.document;
    
    // Calculate summary statistics
    const totalBooks = books.length;
    const availableBooks = books.filter(b => b.quantity > 0).length;
    const outOfStock = books.filter(b => b.quantity <= 0).length;
    const totalValue = books.reduce((sum, book) => sum + (book.price * book.quantity), 0);
    
    doc.getElementById('totalBooks').textContent = totalBooks;
    doc.getElementById('availableBooks').textContent = availableBooks;
    doc.getElementById('outOfStock').textContent = outOfStock;
    doc.getElementById('totalValue').textContent = `$${totalValue.toFixed(2)}`;
    
    // Create book table
    const tableContainer = doc.getElementById('bookTableContainer');
    let tableHTML = `
        <table class="data-table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
    `;
    
    books.forEach(book => {
        const status = book.quantity > 0 ? 'Available' : 'Out of Stock';
        const statusClass = book.quantity > 0 ? 'status-available' : 'status-outofstock';
        
        tableHTML += `
            <tr>
                <td>${book.title || 'N/A'}</td>
                <td>${book.author || 'N/A'}</td>
                <td>${book.isbn || 'N/A'}</td>
                <td>${book.category || 'N/A'}</td>
                <td>$${book.price || '0.00'}</td>
                <td>${book.quantity || '0'}</td>
                <td class="${statusClass}">${status}</td>
            </tr>
        `;
    });
    
    tableHTML += `
            </tbody>
        </table>
    `;
    
    tableContainer.innerHTML = tableHTML;
    
    // Trigger print dialog after data is populated
    setTimeout(() => {
        printWindow.print();
    }, 100);
}

// Populate bill report with actual data
function populateBillReport(printWindow, bills) {
    const doc = printWindow.document;
    
    // Filter bills for this week
    const now = new Date();
    const startOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay());
    const thisWeekBills = bills.filter(bill => {
        const billDate = new Date(bill.createdAt || bill.billDate || Date.now());
        return billDate >= startOfWeek;
    });
    
    // Calculate summary statistics
    const totalBills = bills.length;
    const thisWeekCount = thisWeekBills.length;
    const totalRevenue = bills.reduce((sum, bill) => sum + (bill.totalAmount || 0), 0);
    const thisWeekRevenue = thisWeekBills.reduce((sum, bill) => sum + (bill.totalAmount || 0), 0);
    
    doc.getElementById('totalBills').textContent = totalBills;
    doc.getElementById('totalRevenue').textContent = `$${totalRevenue.toFixed(2)}`;
    doc.getElementById('paidBills').textContent = totalBills; // All bills are paid
    doc.getElementById('pendingBills').textContent = '0'; // No pending bills
    
    // Create bill table (showing this week's bills)
    const tableContainer = doc.getElementById('billTableContainer');
    let tableHTML = `
        <h3>This Week's Bills (${thisWeekBills.length} bills)</h3>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Bill Number</th>
                    <th>Customer</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
    `;
    
    if (thisWeekBills.length > 0) {
        thisWeekBills.forEach(bill => {
            const billDate = new Date(bill.createdAt || bill.billDate || Date.now());
            const formattedDate = billDate.toLocaleDateString();
            
            tableHTML += `
                <tr>
                    <td>${bill.billNumber || bill.id || 'N/A'}</td>
                    <td>${bill.customerName || bill.customer?.name || 'N/A'}</td>
                    <td>$${bill.totalAmount || '0.00'}</td>
                    <td>${formattedDate}</td>
                    <td class="status-paid">Paid</td>
                </tr>
            `;
        });
    } else {
        tableHTML += `
            <tr>
                <td colspan="5" style="text-align: center;">No bills this week</td>
            </tr>
        `;
    }
    
    tableHTML += `
            </tbody>
        </table>
        
        <h3>Overall Bill Summary</h3>
        <p><strong>Total Bills:</strong> ${totalBills}</p>
        <p><strong>Total Revenue:</strong> $${totalRevenue.toFixed(2)}</p>
        <p><strong>This Week's Bills:</strong> ${thisWeekCount}</p>
        <p><strong>This Week's Revenue:</strong> $${thisWeekRevenue.toFixed(2)}</p>
    `;
    
    tableContainer.innerHTML = tableHTML;
    
    // Trigger print dialog after data is populated
    setTimeout(() => {
        printWindow.print();
    }, 100);
}

// Populate sales summary report with actual data
function populateSalesSummaryReport(printWindow, customers, books, bills) {
    const doc = printWindow.document;
    
    // Calculate overall statistics
    const totalCustomers = customers.length;
    const totalBooks = books.length;
    const totalBills = bills.length;
    const totalRevenue = bills.reduce((sum, bill) => sum + (bill.totalAmount || 0), 0);
    
    // Calculate this week's performance
    const now = new Date();
    const startOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay());
    const thisWeekBills = bills.filter(bill => {
        const billDate = new Date(bill.createdAt || bill.billDate || Date.now());
        return billDate >= startOfWeek;
    });
    const thisWeekRevenue = thisWeekBills.reduce((sum, bill) => sum + (bill.totalAmount || 0), 0);
    
    // Update summary numbers
    doc.getElementById('totalCustomers').textContent = totalCustomers;
    doc.getElementById('totalBooks').textContent = totalBooks;
    doc.getElementById('totalBills').textContent = totalBills;
    doc.getElementById('totalRevenue').textContent = `$${totalRevenue.toFixed(2)}`;
    
    // Update monthly performance section
    const monthlyContainer = doc.getElementById('monthlyChartContainer');
    monthlyContainer.innerHTML = `
        <div class="summary-grid">
            <div class="summary-item">
                <div class="summary-number">${thisWeekBills.length}</div>
                <div class="summary-label">This Week's Bills</div>
            </div>
            <div class="summary-item">
                <div class="summary-number">$${thisWeekRevenue.toFixed(2)}</div>
                <div class="summary-label">This Week's Revenue</div>
            </div>
            <div class="summary-item">
                <div class="summary-number">${totalBooks}</div>
                <div class="summary-label">Total Inventory</div>
            </div>
            <div class="summary-item">
                <div class="summary-number">${totalCustomers}</div>
                <div class="summary-label">Total Customers</div>
            </div>
        </div>
        
        <h4>Recent Activity</h4>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Bills Generated</th>
                    <th>Revenue</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>This Week</td>
                    <td>${thisWeekBills.length}</td>
                    <td>$${thisWeekRevenue.toFixed(2)}</td>
                </tr>
                <tr>
                    <td>Total</td>
                    <td>${totalBills}</td>
                    <td>$${totalRevenue.toFixed(2)}</td>
                </tr>
            </tbody>
        </table>
    `;
    
    // Trigger print dialog after data is populated
    setTimeout(() => {
        printWindow.print();
    }, 100);
} 