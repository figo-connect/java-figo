/**
 * 
 */
package me.figo.internal;

/**
 * Defines the scope of the synchronization.<br>
 *   "ACCOUNTS": Only fetch basic account data (e.g. holder name, IBAN etc.). Add new provider accounts to previously synced accesses.<br>
 *   "TRANSACTIONS": Fetch transaction and account data.
 * @author Daniel
 *
 */
public enum SyncScope {
	ACCOUNTS,TRANSACTIONS
}
