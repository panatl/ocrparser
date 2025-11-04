# ocrparser

## ⚠️ Important Notice

This repository contains **implementation files** to fix a Spring Boot startup issue in the `panatl/open-receivable` repository.

### The Issue
The `open-receivable` application fails to start with:
```
Parameter 0 of constructor in org.openreceivable.graphql.resolver.MutationResolver 
required a bean of type 'org.openreceivable.repository.CustomerRepository' that could not be found.
```

### The Solution
This repository provides three Java implementation files that need to be copied to `panatl/open-receivable`:
- `InMemoryCustomerRepository.java`
- `InMemoryPaymentRepository.java`
- `InMemoryReceivableRepository.java`

### Quick Start
See [QUICKSTART.md](QUICKSTART.md) for simple step-by-step instructions.

### Full Documentation
See [IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md) for complete details including production database setup.