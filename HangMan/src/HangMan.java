import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

public class HangMan {
	static String dictPath = "/Users/varindergrewal/Documents/workspace/HangMan/src/Dictionary.txt";
	static ArrayList<String> words = null;
	static int[] guessed = new int[26];
	static int[] frequency_initial = new int[26];

	public static void main(String[] args) {

		String status = null;
		String token = null;
		String remaining_guesses = null;
		String state = null;
		String right = null;
		String wrong = null;
		if (args.length != 0)
			dictPath = args[0];
		while (true) {
			try {
				Thread.sleep(1000);
				readDictionary(dictPath);
				String responseString = startNewGame();
				JSONObject jsonObj;
				jsonObj = new JSONObject(responseString);
				status = jsonObj.get("status").toString();
				token = jsonObj.get("token").toString();
				remaining_guesses = jsonObj.get("remaining_guesses").toString();
				state = jsonObj.get("state").toString();
				right = null;
				wrong = null;
				for (int i = 0; i < 26; i++) {
					guessed[i] = 0;
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String response = null;
			while (status.equals("ALIVE")) {

				try {
					String guess = findNextGuess(right, wrong);
					guessed[guess.charAt(0) - 65] = 1;
					Thread.sleep(500);
					response = guess(token, guess);
					JSONObject jsonObj2;
					jsonObj2 = new JSONObject(response);
					status = jsonObj2.get("status").toString();
					if (!jsonObj2.get("remaining_guesses").toString().equals(remaining_guesses)) {
						if (wrong == null)
							wrong = guess;
						else
							wrong = wrong + guess;
						remaining_guesses = jsonObj2.get("remaining_guesses").toString();
					} else {
						if (right == null)
							right = guess;
						else
							right = right + guess;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public static String startNewGame() {
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet("http://gallows.hulu.com/play?code=grewal@tamu.edu");
		String responseString = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			responseString = EntityUtils.toString(responseEntity, "UTF-8");
			System.out.println(responseString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;
	}

	public static String findNextGuess(String right, String wrong) {
		String guess = null;
		boolean isDeadlock = true;
		if (right != null || wrong != null)
			updateWords(right, wrong);
		int[] frequency = calcFrequency(words);
		int largestFrequency = 0, index = 0;
		for (int i = 0; i < 26; i++) {
			if ((frequency[i] > largestFrequency) && (guessed[i] == 0)) {
				isDeadlock = false;
				largestFrequency = frequency[i];
				index = i;
			}
		}
		if (isDeadlock) {
			for (int i = 0; i < 26; i++) {
				if (guessed[i] == 0 && frequency_initial[i] > largestFrequency) {
					largestFrequency = frequency_initial[i];
					index = i;
				}
			}

		}
		guess = Character.toString((char) (index + 65));
		return guess;
	}

	public static void updateWords(String right, String wrong) {
		String word = null;
		char rightGuess, wrongGuess;
		if (right != null) {
			for (int j = 0; j < right.length(); j++) {
				rightGuess = right.charAt(j);
				int upperLimit = words.size() - 1;
				for (int i = upperLimit; i >= 0; i--) {

					word = words.get(i);
					if (word.indexOf(rightGuess) == -1) {
						words.remove(i);
					}

				}
			}
		}
		if (wrong != null) {
			for (int j = 0; j < wrong.length(); j++) {
				wrongGuess = wrong.charAt(j);
				for (int i = words.size() - 1; i >= 0; i--) {

					word = words.get(i);
					if (word.indexOf(wrongGuess) != -1) {
						words.remove(i);
					}

				}
			}
		}

	}

	public static String guess(String token, String guess) {
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet(
				"http://gallows.hulu.com/play?code=grewal@tamu.edu&token=" + token + "&guess=" + guess);
		HttpResponse response;
		String responseString = null;
		try {
			response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			responseString = EntityUtils.toString(responseEntity, "UTF-8");
			System.out.println(responseString);
			return responseString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;

	}

	public static void parseJSON(JSONObject jsonObj) {
		try {
			System.out.println(jsonObj.get("status"));
			System.out.println(jsonObj.get("state"));
			String state = jsonObj.get("state").toString();
			String[] words = state.split(" ");
			System.out.println(words[1]);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readDictionary(String dictPath) {
		FileReaderUtility fileReader = new FileReaderUtility(dictPath);

		words = fileReader.ReadFile();
		frequency_initial = calcFrequency(words);
	}

	public static int[] calcFrequency(ArrayList<String> words) {
		int[] frequency = new int[26];
		int key;
		for (int i = 0; i < 26; i++)
			frequency[i] = 0;
		String word;
		for (int i = 0; i < words.size(); i++) {
			word = words.get(i);
			for (int j = 0; j < word.length(); j++) {
				key = word.charAt(j) - 65;
				if (key < 26 && key >= 0)
					frequency[key]++;

			}

		}
		return frequency;
	}
}

final class FileReaderUtility {
	private String _path;

	public FileReaderUtility(String Path)

	{
		_path = Path;
	}

	public ArrayList<String> ReadFile() {
		ArrayList<String> clauses = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(_path);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				clauses.add(line.trim());
			}

			bufferedReader.close();
			return clauses;
		}

		catch (FileNotFoundException ex) {

		}

		catch (IOException ex) {
		}

		return clauses;
	}
}
