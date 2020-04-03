package fragment.submissions;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GrzegorzZiemskiTest {

	GrzegorzZiemski app = new GrzegorzZiemski();

	Reassemble reassemble = new Reassemble();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

	@Test
	public void emptyFileTest() throws Exception {
		File file = new File("src/test/resources/1emptyFile.txt");
		app.main(new String[] {file.getPath()});
	}

	@Test
	public void singleLineTest() throws Exception {
		File file = new File("src/test/resources/2singleLine.txt");
		app.main(new String[] {file.getPath()});
		assertEquals("singleLine\r\n", outContent.toString());
	}

	@Test
	public void semicolonTest() throws Exception {
		File file = new File("src/test/resources/3semicolon.txt");
		app.main(new String[] {file.getPath()});
		assertEquals("", outContent.toString());
	}

	@Test
	public void test4Test() throws Exception {
		File file = new File("src/test/resources/4.txt");
		File fileExpected = new File("src/test/resources/4expected.txt");

		app.main(new String[] {file.getPath()});
		assertEquals(FileUtils.readFileToString(fileExpected), outContent.toString());
	}

	@Test
	public void main5Test() throws Exception {
		File file = new File("src/test/resources/reassemble-text-fragments-example.txt");
		File fileExpected = new File("src/test/resources/reassemble-text-fragments-example-expected.txt");

		app.main(new String[] {file.getPath()});
		assertEquals(FileUtils.readFileToString(fileExpected), outContent.toString());
	}

	@Test
	public void main6Test() throws Exception {
		File file = new File("src/test/resources/6.txt");
		File fileExpected = new File("src/test/resources/6expected.txt");

		app.main(new String[] {file.getPath()});
		assertEquals(FileUtils.readFileToString(fileExpected), outContent.toString());
	}

	@Test
	public void calcOverlapRightTest() {
		assertEquals(3, reassemble.calcOverlapRight("ABCDEF", "DEF"));

		assertEquals(1, reassemble.calcOverlapRight("ABCDX", "XABC"));
		assertEquals(0, reassemble.calcOverlapRight("ABCDX", "AXBC"));

		assertEquals(2, reassemble.calcOverlapRight("ABCDXX", "XXABC"));
		assertEquals(0, reassemble.calcOverlapRight("ABCDXX", "AXXBC"));

		assertEquals(4, reassemble.calcOverlapRight("ABCDXYXY", "XYXYABC"));
		assertEquals(0, reassemble.calcOverlapRight("ABCDXYXY", "AXYXYBC"));

		assertEquals(4, reassemble.calcOverlapRight("ABAB", "ABAB"));

		assertEquals(0, reassemble.calcOverlapRight("X", "AXA"));
		assertEquals(1, reassemble.calcOverlapRight("XA", "AXA"));

		assertEquals(0, reassemble.calcOverlapRight("ABX", "AB"));

		assertEquals(0, reassemble.calcOverlapRight("XBA", "BYBA"));
	}


	@Test
	public void mergeRightTest() {
		assertEquals("O draconian devil", mergeRight("O draconia", "conian devil", 5));
	}

	protected String mergeRight(String line1, String line2, int overlap) {
		StringBuilder sb = new StringBuilder(line1);
		reassemble.mergeRight(sb, line2, overlap);
		return sb.toString();
	}

	@Test
	public void mergeLeftTest() {
		assertEquals("O draconian devil", mergeLeft("conian devil", "O draconia", 5));
		assertEquals("AXXXXX", mergeLeft("XXXXX", "ABCDE", 4));
	}

	protected String mergeLeft(String line1, String line2, int overlap) {
		StringBuilder sb = new StringBuilder(line1);
		reassemble.mergeLeft(sb, line2, overlap);
		return sb.toString();
	}
}