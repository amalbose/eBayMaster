package com.axatrikx.controllers;

import com.axatrikx.beans.Response;
import com.axatrikx.beans.TransactionItem;

/**
 * The controller class which controls the validation and saving of Transaction items.
 * 
 * @author Amal Bose B S
 * 
 */
public class TransactionItemController {

	private TransactionItem transactionItemObj;

	public TransactionItemController(TransactionItem transactionItemObj) {
		this.transactionItemObj = transactionItemObj;
	}

	/**
	 * Saves the transaction item to file if data is valid.
	 * 
	 * @return Returns Response object based on whether item is saved or not.
	 */
	public Response saveTransactionItem() {
		Response response = validateData();
		if (response.isValid()) {
			saveItemToFile();
		}
		return response;
	}

	private void saveItemToFile() {
		// TODO Auto-generated method stub

	}

	/**
	 * Validates the transaction data
	 */
	private Response validateData() {
		// TODO validate
		return new Response();
	}

}
