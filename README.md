java-figo [![Build Status](https://travis-ci.org/figo-connect/java-figo.png)](https://travis-ci.org/figo-connect/java-figo)
===========

Java bindings for the figo connect API: http://figo.me

Simply add to your pom.xml:

```xml
<dependency>
        <groupId>me.figo</groupId>
        <artifactId>java-figo</artifactId>
        <version>1.0</version>
</dependency>
```

And just as easy to use:
```java
import java.io.IOException;

import me.figo.FigoException;
import me.figo.FigoSession;
import me.figo.models.Account;
import me.figo.models.Transaction;


public class FigoExample {

	public static void main(String[] args) throws FigoException, IOException {
		FigoSession session = new FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");

		// print out a list of accounts including its balance
		for (Account account : session.getAccounts()) {
			System.out.println(account.getName());
			System.out.println(session.getAccountBalance(account).getBalance());
		}

		// print out the list of all transactions on a specific account
		for (Transaction transaction : session.getTransactions(session.getAccount("A1.2"))) {
			System.out.println(transaction.getPurposeText());
		}
	}

}
```

A more detailed documentation of the figo connect API can be found at http://developer.figo.me.
