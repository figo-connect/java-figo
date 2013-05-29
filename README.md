java-figo [![Build Status](https://travis-ci.org/figo-connect/java-figo.png)](https://travis-ci.org/figo-connect/java-figo)
===========

Java bindings for the figo connect API: http://figo.me

Simply add to your pom.xmlpip:

```xml
<dependency>
        <groupId>me.figo</groupId>
        <artifactId>java-figo</artifactId>
        <version>1.0</version>
</dependency>
```

And just as easy to use:
```java
from figo import FigoSession

session = FigoSession("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ");

// print out a list of accounts including its balance
for(Account account : session.accounts) {
 	System.out.println(account.name);
	System.out.println(account.balance.balance);
}

// print out the list of all transactions on a specific account
for(Transaction transaction: session.get_account("A1.2").transactions) {
	System.out.println(transaction.purpose);
}
```
