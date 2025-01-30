# cadec2025-hexagonal

This project complements my presentation on Hexagonal Architecture at Cadec 2025.
The source code aims to show the impact of applying the Ports and Adapters style
to an existing code base, a fictive "Product" component:

```plantuml
package "Product" {
  ProductService - [Product]
}

database "Postgres" as postgres {
  [Products]
}

queue stock

package "InventoryMock" {
  InventoryService - [InventoryMock]
}

[Product] --> [Products]
[Product] --> [InventoryService]
[Product] --> [stock]
```

## Contents

- `original/product` contains the original implementation, using a traditional, 3-tier layering.
- `hexagonal/product` is the result of applying ports and adapters.
- `inventory-api` contains an api to an external component, used by both projects.

## Building the examples

Build the `inventory-api` jar by running `mvn install` in the `inventory-api` folder.

The `original/product` and `hexagonal/product` should now load correctly into an IDE such as
Visual Studio Code or IntelliJ.