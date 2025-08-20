// Global variables
let currentBooks = [];
let searchTerm = '';

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    loadBooks();
});

// Load books from web service
async function loadBooks() {
    showLoading(true);
    try {
        const response = await fetch('books?action=list', {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentBooks = result.data;
            displayBooks(currentBooks);
            showAlert('success', `Loaded ${result.count} books successfully`);
        } else {
            showAlert('error', result.message || 'Failed to load books');
        }
    } catch (error) {
        console.error('Error loading books:', error);
        showAlert('error', 'Error loading books: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Search books using web service
async function searchBooks() {
    searchTerm = document.getElementById('searchInput').value.trim();
    
    if (!searchTerm) {
        loadBooks(); // Load all if no search term
        return;
    }

    showLoading(true);
    try {
        const response = await fetch(`books?action=search&term=${encodeURIComponent(searchTerm)}`, {
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            currentBooks = result.data;
            displayBooks(currentBooks);
            showAlert('success', `Found ${result.count} books matching "${searchTerm}"`);
        } else {
            showAlert('error', result.message || 'Search failed');
        }
    } catch (error) {
        console.error('Error searching books:', error);
        showAlert('error', 'Error searching books: ' + error.message);
    } finally {
        showLoading(false);
    }
}

// Display books in table
function displayBooks(books) {
    const container = document.getElementById('booksTableContainer');
    
    if (!books || books.length === 0) {
        container.innerHTML = '<p>No books found.</p>';
        return;
    }

    let tableHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Status</th>
                    ${isAdmin ? '<th>Actions</th>' : ''}
                </tr>
            </thead>
            <tbody>
    `;

    books.forEach(book => {
        const status = book.quantity > 0 ? 'Available' : 'Out of Stock';
        const statusClass = book.quantity > 0 ? 'text-success' : 'text-danger';
        
        tableHTML += `
            <tr>
                <td>${book.title || ''}</td>
                <td>${book.author || ''}</td>
                <td>${book.isbn || ''}</td>
                <td>${book.category || ''}</td>
                <td>$${book.price || '0.00'}</td>
                <td>${book.quantity || '0'}</td>
                <td class="${statusClass}">${status}</td>
                ${isAdmin ? `
                    <td>
                        <a href="books?action=edit&id=${book.id}" class="btn btn-warning btn-sm" style="margin-right: 5px;">Edit</a>
                        <a href="books?action=delete&id=${book.id}" 
                           class="btn btn-danger btn-sm" 
                           onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                    </td>` : ''}
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
                searchBooks();
            }
        });
    }
    
    // Check user role on page load
    checkUserRole();
});