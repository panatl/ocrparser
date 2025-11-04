# Quick Start - Applying the Fix

## TL;DR
Copy the three Java files from this repository's `src/main/java/org/openreceivable/repository/impl/` directory to the same location in your `panatl/open-receivable` repository, then rebuild and run.

## Step-by-Step Instructions

### 1. Clone or navigate to your open-receivable repository
```bash
cd /path/to/open-receivable
```

### 2. Create the impl directory
```bash
mkdir -p src/main/java/org/openreceivable/repository/impl
```

### 3. Copy the implementation files
From this repository (`panatl/ocrparser`), copy these three files:
- `InMemoryCustomerRepository.java`
- `InMemoryPaymentRepository.java`  
- `InMemoryReceivableRepository.java`

To: `panatl/open-receivable/src/main/java/org/openreceivable/repository/impl/`

### 4. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

### 5. Verify the fix
The application should start without the error:
```
Parameter 0 of constructor in org.openreceivable.graphql.resolver.MutationResolver 
required a bean of type 'org.openreceivable.repository.CustomerRepository' that could not be found.
```

## Alternative: Using git commands
If both repositories are cloned locally:

```bash
# Copy files from ocrparser to open-receivable
cp -r /path/to/ocrparser/src/main/java/org/openreceivable/repository/impl/* \
      /path/to/open-receivable/src/main/java/org/openreceivable/repository/impl/

# Navigate to open-receivable and run
cd /path/to/open-receivable
mvn clean install
mvn spring-boot:run
```

## What This Fixes
This provides concrete implementations for the three repository interfaces required by `MutationResolver`:
1. ✅ `CustomerRepository` → `InMemoryCustomerRepository`
2. ✅ `PaymentRepository` → `InMemoryPaymentRepository`
3. ✅ `ReceivableRepository` → `InMemoryReceivableRepository`

## Important Notes
- These are **in-memory** implementations - data will be lost on restart
- Suitable for **development and testing**
- For production, see `IMPLEMENTATION_GUIDE.md` for database setup instructions
- All implementations use thread-safe `ConcurrentHashMap` storage

## Need Help?
See `IMPLEMENTATION_GUIDE.md` for detailed information including:
- Full explanation of the problem
- Database-backed implementation guide for production
- JPA/Spring Data alternative approach
