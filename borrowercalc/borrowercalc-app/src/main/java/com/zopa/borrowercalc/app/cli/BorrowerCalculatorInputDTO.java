package com.zopa.borrowercalc.app.cli;

import java.io.File;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.zopa.borrowercalc.commons.validators.FileExists;
import com.zopa.borrowercalc.commons.validators.MultipleOf;

/**
 * DTO from the CMD input.
 */
public class BorrowerCalculatorInputDTO {

	@NotNull
	@FileExists
	private File marketFile;

	@NotNull
	@Min(1000)
	@Max(15000)
	@MultipleOf(100)
	private Integer loanAmount;

	public File getMarketFile() {
		return marketFile;
	}

	public void setMarketFile(File marketFile) {
		this.marketFile = marketFile;
	}

	public Integer getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}

}
