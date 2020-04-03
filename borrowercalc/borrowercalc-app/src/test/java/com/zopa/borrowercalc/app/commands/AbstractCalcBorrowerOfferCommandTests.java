package com.zopa.borrowercalc.app.commands;

import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public abstract class AbstractCalcBorrowerOfferCommandTests {

	public static final int MIN_LOAN_AMOUNT = 1000;

	public static final int MAX_LOAN_AMOUNT = 15000;

	@Rule
	public final OutputCapture output = new OutputCapture();

	@Autowired
	CalcBorrowerOfferCommand calcBorrowerOfferCommand;

	@Before
	public void init() {
		this.output.reset();
		Locale.setDefault(Locale.ENGLISH);
	}

}
