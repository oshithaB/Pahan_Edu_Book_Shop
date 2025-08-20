// Global variables
let currentBills = [];
let searchTerm = '';

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    loadBills();
});

// Load bills from web service
async function loadBills() {
    showLoading(true);
    try {
        const response = await fetch('bills?action=list', {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentBills = result.data;
            displayBills(currentBills);
            showAlert('success', `Loaded ${result.count} bills successfully`);
        } else {
            showAlert('error', result.message || 'Failed to load bills');
        }
    } catch (error) {
        console.error('Error loading bills:', error);
        showAlert('error', 'Error loading bills: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Search bills using web service
async function searchBills() {
    searchTerm = document.getElementById('searchInput').value.trim();
    
    if (!searchTerm) {
        loadBills(); // Load all if no search term
        return;
    }

    showLoading(true);
    try {
        const response = await fetch(`bills?action=search&term=${encodeURIComponent(searchTerm)}`, {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentBills = result.data;
            displayBills(currentBills);
            showAlert('success', `Found ${result.count} bills matching "${searchTerm}"`);
        } else {
            showAlert('error', result.message || 'Search failed');
        }
    } catch (error) {
        console.error('Error searching bills:', error);
        showAlert('error', 'Error searching bills: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Display bills in table
function displayBills(bills) {
    const container = document.getElementById('billsTableContainer');
    
    if (!bills || bills.length === 0) {
        container.innerHTML = '<p>No bills found.</p>';
        return;
    }

    let tableHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>Bill Number</th>
                    <th>Customer</th>
                    <th>Cashier</th>
                    <th>Total Amount</th>
                    <th>Status</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
    `;

    bills.forEach(bill => {
        const status = bill.status || 'Paid';
        const statusClass = getStatusClass(status);
        
        tableHTML += `
            <tr>
                <td>${bill.billNumber || ''}</td>
                <td>${bill.customerName || ''}</td>
                <td>${bill.cashierName || ''}</td>
                <td>$${bill.totalAmount || '0.00'}</td>
                <td class="${statusClass}">${status}</td>
                <td>${bill.createdAt || ''}</td>
                <td>
                    <a href="bills?action=view&id=${bill.id}" class="btn btn-info btn-sm">View</a>
                </td>
            </tr>
        `;
    });

    tableHTML += `
            </tbody>
        </table>
    `;

    container.innerHTML = tableHTML;
}

// Get CSS class for bill status
function getStatusClass(status) {
    switch(status.toLowerCase()) {
        case 'paid':
            return 'text-success';
        case 'pending':
            return 'text-warning';
        case 'cancelled':
            return 'text-danger';
        default:
            return 'text-info';
    }
}

// Show/hide loading indicator
function showLoading(show) {
    document.getElementById('loadingIndicator').style.display = show ? 'block' : 'none';
}

// Show alert messages
function showAlert(type, message) {
    const alertContainer = document.getElementById('alertContainer');
    const alertClass = type === 'success' ? 'alert alert-success' : 'alert alert-error';
    
    alertContainer.innerHTML = `<div class="${alertClass}">${message}</div>`;
    
    // Auto-hide after 5 seconds
    setTimeout(() => {
        alertContainer.innerHTML = '';
    }, 5000);
}

// Check user role and show/hide admin elements
function checkUserRole() {
    // Get user role from the page (this should be set by JSP)
    const userRoleElement = document.querySelector('.user-info span');
    if (userRoleElement) {
        const roleText = userRoleElement.textContent;
        if (roleText.includes('ADMIN')) {
            // Show admin-only elements
            document.querySelectorAll('.admin-only').forEach(el => {
                el.style.display = 'inline';
            });
        }
        if (roleText.includes('CASHIER')) {
            // Show cashier-only elements
            document.querySelectorAll('.cashier-only').forEach(el => {
                el.style.display = 'inline';
            });
        }
    }
}

// Search on Enter key
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchBills();
            }
        });
    }
    
    // Check user role on page load
    checkUserRole();
}); 