# PurchaseTransactions
Java Spring Boot Application

Application can add transactions that persist over a database - **/add**
 - Transactions cannot have an empty description
 - Transaction amount should have a proper rounded decimal
 - Transaction Date can be any valid date in YYYY-MM-dd format
 - Transaction that is added has a unique identifier UUID

Application can list all added transactions - **/list**

Application returns transactions that match an id - **/get/{id}**
  - Invalid ids or those that do not exist return an empty transaction

Application retuns exchange rate and exchange amount for transaction dated from 6 months from transaction date - **/get/{id}/{Country-Currency}**
 - Invalid Ids return empty conversion object

