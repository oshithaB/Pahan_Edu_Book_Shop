# Pahana Edu Book Shop - Restructured with Design Patterns

## Project Structure

This project has been restructured to follow domain-driven design principles with proper separation of concerns.

### Design Patterns Implemented

1. **Singleton Pattern** - `DatabaseConnection` class ensures single instance with connection pooling
2. **Factory Pattern** - Multiple factory implementations for different domains (Book, Customer, Bill, User)
3. **Observer Pattern** - `SessionManager` with `SessionObserver` for session event handling

### Collections Used

1. **List** - Used in services, DAOs, and observers for ordered data
2. **Map** - Used for connection pooling, session management, and search field mapping
3. **Set** - Used for tracking active connections and expired sessions
4. **Queue** - Used in connection pool for available connections

### File Structure

```
src/main/java/com/pahanaedu/bookshop/
├── book/
│   ├── dto/           # Data Transfer Objects
│   ├── mapper/        # Entity-DTO mapping
│   ├── service/       # Business logic
│   └── model/         # Domain models
├── customer/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   └── model/
├── bill/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   └── model/
├── user/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   └── model/
├── resource/          # Shared resources
│   ├── factory/
│   │   └── impl/      # Factory implementations
│   ├── model/         # Common interfaces
│   └── util/          # Utilities
├── common/            # Cross-cutting concerns
│   ├── session/       # Session management
│   └── observer/      # Observer implementations
├── dao/               # Data Access Objects
├── servlet/           # Web controllers
└── filter/            # Web filters
```

### Key Features

- **Domain-based organization** following lecturer's pattern
- **Connection pooling** with Map, Queue, and Set collections
- **Session management** with cookies and observers
- **Factory pattern** with multiple implementations
- **Unit testing** covering 40% of functionality
- **Same frontend experience** maintained

### Testing

Unit tests are located in `src/test/java/` and cover:
- Book service and model validation
- Customer service and model validation
- Factory pattern implementations
- Database connection singleton
- Session manager with observer pattern

Run tests with: `mvn test`

### Collections Usage Summary

1. **Map<String, Connection>** - Connection pool management
2. **Queue<Connection>** - Available connections queue
3. **Set<Connection>** - Active connections tracking
4. **List<SessionObserver>** - Observer pattern implementation
5. **Map<String, SessionInfo>** - Session storage
6. **Set<String>** - Expired sessions tracking
7. **List<Book/Customer/etc>** - Data retrieval and processing
8. **Map<String, String>** - Search field mapping in DAOs

### Design Patterns Justification

1. **Singleton (DatabaseConnection)**: Ensures single database connection manager with resource pooling
2. **Factory (Product creation)**: Provides flexible object creation with validation and different implementations per domain
3. **Observer (Session management)**: Enables loose coupling for session event handling and auditing

All patterns are used appropriately for their intended purposes and provide real value to the application architecture.