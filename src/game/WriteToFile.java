package game;

import java.io.*;

public class WriteToFile {
	public void writeToFile() throws GiveUpException {
		while (true) {
			try {
				String tFilename = promptFile();
				File tFile = new File(tFilename);
				try {
					PrintWriter tOut = new PrintWriter(new FileWriter(tFile));
					tOut.println("Hello, World");
					tOut.close();
					return; // important!
				} catch (IOException e) {
					reportError(e);
				}
			} catch (AbortException e) {
				reportGiveUp(e);
				throw new GiveUpException();
			}
		}
	}

	private void reportGiveUp(Exception e) {
		System.err.println("User aborted action, giving up");
	}

	private void reportError(Exception e) {
		System.err.println("Could not write to file");
	}

	private String promptFile() throws AbortException {
		System.out.println("Please insert filename:");
		BufferedReader tIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			String tFilename = tIn.readLine();
			if (tFilename == null || tFilename.isEmpty())
				throw new AbortException();
			return tFilename;
		} catch (IOException e) {
			throw new AbortException();
		}
	}

}
