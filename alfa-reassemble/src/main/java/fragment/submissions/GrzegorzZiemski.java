package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GrzegorzZiemski {

	Reassemble reassemble = new Reassemble();

	public static void main(String[] args) throws FileNotFoundException, IOException {
		GrzegorzZiemski app = new GrzegorzZiemski();
		app.run(args[0]);
	}

	protected void run(String filePath) throws FileNotFoundException, IOException {
		try (ReassembleReader r = new ReassembleReader(filePath)) {
			while (r.hasNextCase()) {
				List<String> lines = r.nextCase();
				try {
					System.out.println(reassemble.reassemble(lines));
				} catch (Exception e) {
					//Ignore the case if it is broken.
					System.out.println();
				}
			}
		}
	}

}

class Reassemble {

	public String reassemble(List<String> lines) {
		if (lines == null || lines.isEmpty())
			return null;

		// 1. Find the best first line.
		//StringBuilder line1 = new StringBuilder(findLongestLine(lines));
		StringBuilder line1 = new StringBuilder(lines.remove(0));

		while (!lines.isEmpty()) {

			// 2. Find the best next line to merge.
			String toMergeRight = null;
			int maxOverlapRight = -1;

			String toMergeLeft = null;
			int maxOverlapLeft = -1;

			ListIterator<String> it = lines.listIterator();
			while (it.hasNext()) {
				String line2 = it.next();

				// Delete line2 if it is contained on line1.
				if (line1.indexOf(line2) != -1) {
					it.remove();
					continue;
				}

				int overlap = calcOverlapLeft(line1, line2);
				if (overlap > maxOverlapLeft) {
					maxOverlapLeft = overlap;
					toMergeLeft = line2;
				}

				overlap = calcOverlapRight(line1, line2);
				if (overlap > maxOverlapRight) {
					maxOverlapRight = overlap;
					toMergeRight = line2;
				}
			}

			// 3. Merge the line with the highest overlap.
			if (maxOverlapLeft == -1 && maxOverlapRight == -1) {
				// skip, no lines
			} else if (maxOverlapLeft == 0 && maxOverlapRight == 0) {
				// skip
				lines.remove(toMergeLeft);
			} else if (maxOverlapLeft >= maxOverlapRight) {
				lines.remove(toMergeLeft);
				mergeLeft(line1, toMergeLeft, maxOverlapLeft);
			} else if (maxOverlapLeft < maxOverlapRight) {
				lines.remove(toMergeRight);
				mergeRight(line1, toMergeRight, maxOverlapRight);
			}
			// System.out.println(line1.toString());
		}
		return line1.toString();
	}

	protected String findLongestLine(List<String> lines) {
		String line = lines.stream().max(Comparator.comparingInt(String::length)).get();
		lines.remove(line);
		return line;
	}

	protected int calcOverlapLeft(CharSequence line1, CharSequence line2) {
		return calcOverlapRight(line2, line1);
	}

	protected int calcOverlapRight(CharSequence line1, CharSequence line2) {
		final int L = line1.length();// line1
		int R = line2.length();// line2
		int newR = R;

		int[] P = null;// previous array
		int[] N = new int[R];// next array

		int overlap = 0;

		for (int l = L - 1; l >= 0; l--) {
			char lc = line1.charAt(l);
			boolean emptyL = true;// the row has only zero values, don't check next

			for (int r = 0; r < R; r++) {
				char rc = line2.charAt(r);
				if (lc == rc) {
					if (P != null) {
						N[r] = P[r + 1] + 1;
					} else {
						N[r] = 1;
					}
					newR = r;
					emptyL = false;
				}
			}
			// System.out.println(Arrays.toString(N));
			if (emptyL) {
				break;
			} else {
				P = N;
				R = newR;
				N = new int[R + 1];
				if (P[0] >= L - l) {
					overlap = P[0];
				}
			}
		}

		// System.out.println(Arrays.toString(P));
		return overlap;
	}

	protected void mergeLeft(StringBuilder line1, String line2, int overlap) {
		line1.insert(0, line2.substring(0, line2.length() - overlap));
	}

	protected void mergeRight(StringBuilder line1, String line2, int overlap) {
		line1.delete(line1.length() - overlap, line1.length())//
				.append(line2);
	}

}

class ReassembleReader extends BufferedReader {

	public ReassembleReader(String filePath) throws FileNotFoundException {
		super(new BufferedReader(new FileReader(filePath)));
	}

	public boolean hasNextCase() throws IOException {
		this.mark(1);
		if (this.read() != -1) {
			this.reset();
			return true;
		} else {
			return false;
		}
	}

	public List<String> nextCase() throws IOException {
		List<String> lines = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		int ch;

		while ((ch = this.read()) != -1) {
			if (ch == '\n') {
				// end line - end case
				lines.add(sb.toString());
				return lines;
			} else if (ch == '\r') {
				// end line - end case
				this.mark(1);
				if (this.read() != '\n') {
					this.reset();
				}
				lines.add(sb.toString());
				return lines;
			} else if (ch == ';') {
				// add line
				lines.add(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(Character.toChars(ch));
			}
		}
		if (sb.length() > 0)
			lines.add(sb.toString());
		return lines;
	}

}