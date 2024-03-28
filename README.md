# PurchaseTransactions
Java Spring Boot Application


Application can add transactions that persist over a database - **/add**

 - Transactions cannot have an empty description
 - Transaction amount should have a proper rounded decimal
 - Transaction Date can be any valid date in YYYY-MM-dd format
 - Transaction that is added has a unique identifier UUID

Application can list all added transactions - **/list**

Application returns transactions that match an id - **/get/{id}**

 - Invalid Ids or those that do not exist return an empty transaction

Application returns exchange rate and exchange amount for transaction dated from 6 months from transaction date - **/get/{id}/{Country-Currency}**

 - Invalid Ids return empty conversion object
 
Validations not added for future date or date range and for Country-Currency

Valid Country-Currency values:
	- Euro Zone-Euro
	- India-Rupee
	- United Kingdom-Pound

CICD build to AWS configured with Codepipeline and GitHub Actions.

Application links

	Endpoint - http://purchase-transactions.eu-west-2.elasticbeanstalk.com/
	add transaction - http://purchase-transactions.eu-west-2.elasticbeanstalk.com/add
	list transactions - http://purchase-transactions.eu-west-2.elasticbeanstalk.com/list
	get transaction - http://purchase-transactions.eu-west-2.elasticbeanstalk.com/get/{uuid}
	get exchange amount - http://purchase-transactions.eu-west-2.elasticbeanstalk.com/get/{uuid}/{Country-Currency}
