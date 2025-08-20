// Global variables
let currentCustomers = [];
let searchTerm = '';

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    loadCustomers();
});

// Load customers from web service
async function loadCustomers() {
    showLoading(true);
    try {
        const response = await fetch('customers?action=list', {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentCustomers = result.data;
            displayCustomers(currentCustomers);
            showAlert('success', `Loaded ${result.count} customers successfully`);
        } else {
            showAlert('error', result.message || 'Failed to load customers');
        }
    } catch (error) {
        console.error('Error loading customers:', error);
        showAlert('error', 'Error loading customers: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Search customers using web service
async function searchCustomers() {
    searchTerm = document.getElementById('searchInput').value.trim();
    
    if (!searchTerm) {
        loadCustomers(); // Load all if no search term
        return;
    }

    showLoading(true);
    try {
        const response = await fetch(`customers?action=search&term=${encodeURIComponent(searchTerm)}`, {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentCustomers = result.data;
            displayCustomers(currentCustomers);
            showAlert('success', `Found ${result.count} customers matching "${searchTerm}"`);
        } else {
            showAlert('error', result.message || 'Search failed');
        }
    } catch (error) {
        console.error('Error searching customers:', error);
        showAlert('error', 'Error searching customers: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Display customers in table
function displayCustomers(customers) {
    const container = document.getElementById('customersTableContainer');
    
    if (!customers || customers.length === 0) {
        container.innerHTML = '<p>No customers found.</p>';
        return;
    }

    let tableHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>Account Number</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Telephone</th>
                    <th>Email</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
    `;

    customers.forEach(customer => {
        tableHTML += `
            <tr>
                <td>${customer.accountNumber || ''}</td>
                <td>${customer.name || ''}</td>
                <td>${customer.address || ''}</td>
                <td>${customer.telephone || ''}</td>
                <td>${customer.email || ''}</td>
                <td>${customer.createdAt || ''}</td>
                <td>
                    <a href="customers?action=edit&id=${customer.id}" class="btn btn-warning btn-sm">Edit</a>
                    <span class="admin-only" style="display: none;">
                        <a href="customers?action=delete&id=${customer.id}" 
                           class="btn btn-danger btn-sm" 
                           onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                    </span>
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
                searchCustomers();
            }
        });
    }
    
    // Check user role on page load
    checkUserRole();
});