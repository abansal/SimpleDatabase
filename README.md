# SimpleDatabase

Simple Database is a simple in-memory database created as a solution to a coding challenge.

## How to compile and run (without IDE)

```
$ git clone https://github.com/abansal/SimpleDatabase.git
$ cd SimpleDatabase/src
$ javac SimpleDatabase.java
$ java SimpleDatabase
```
## Batch input from a file

Create an input files with commands. Example:
```
$ cat test.txt
SET a 50
BEGIN
GET a
SET a 60
BEGIN
UNSET a
GET a
ROLLBACK
GET a
COMMIT
GET a
END
```
```
$ java SimpleDatabase < test.txt
<Output>
```

## Supported Commands
```
* SET name value – Set the variable name to the value value. Neither variable names nor values will contain spaces.
* GET name – Print out the value of the variable name, or NULL if that variable is not set.
* UNSET name – Unset the variable name, making it just like that variable was never set.
* NUMEQUALTO value – Print out the number of variables that are currently set to value. If no variables equal that value, print 0.
* END – Exit the program. Your program will always receive this as its last command.
```
### Transaction Commands:
```
* BEGIN – Open a new transaction block. Transaction blocks can be nested; a BEGIN can be issued inside of an existing block.
* ROLLBACK – Undo all of the commands issued in the most recent transaction block, and close the block. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
* COMMIT – Close all open transaction blocks, permanently applying the changes made in them. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
```

## Design

```

* Command Class:
Its a simple wrapper for acceptable commands. There is an action associated with the command and a list of arguments.
Command also exposes functions to check if a command lies in category like: update commands, transaction commands.

* Database Class:
This is where data is stored. Apart from exposing the basic methods, there are few additional methods like:
-- executeCommand(): Takes a command and executes it.
-- transaction() : Takes a list of commands and executes a transaction. It also handles the nested Transactions
-- rollback() : Takes a stack of commands and executes the rollback

* Driver Program:
Waits for user input and then either executes a command or a transaction.
```
## Further Improvements

* The error/exception handling can be improved. Very little time has been spent on that.
* The database needs to be Singleton and threadsafe for parallel execution. It was ignored because challenge states so.
* The transaction() could be made very simple by looking at last command and if its not commit ignore the whole set.
It is written in this fashion because problem statement demanded to go through simulation of executing state updates
and then rolling back (his added to unnecessary code complexity).

