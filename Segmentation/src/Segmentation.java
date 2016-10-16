import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Segmentation{

	static HashMap<String,ArrayList<ArrayList<point>>> origSubstroke = new HashMap<String,ArrayList<ArrayList<point>>>();
	static HashMap<String,ArrayList<ArrayList<point>>> currSubstroke = new HashMap<String,ArrayList<ArrayList<point>>>();
	static HashMap<String,BB> BBMap = new HashMap<String,BB>();


	public static void main(String args[]){

		try {



			sendGet();

			System.out.println("Done!!! Accuracy is : "+ calcAccuracy());





		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static void sendGet() throws Exception{

		String url = "http://srl-prod1.cs.tamu.edu:7750/getSketches?domain=MechanixCleaned&interpretation=force";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		//request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " +
				response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		String output = result.toString();
		//String op = output.substring(1, output.length()-1);


		JSONArray sketches = new JSONArray(output);
		result = null;
		output = null;
		for(int i = 0;i< sketches.length();i++){

			JSONArray strokes =  (JSONArray) sketches.getJSONObject(i).get("strokes");
			JSONArray jpoints = (JSONArray) sketches.getJSONObject(i).get("points");
			getOrigSubstroke(sketches.getJSONObject(i),jpoints);
			for(int j =0; j<strokes.length();j++){

				JSONObject stroke = strokes.getJSONObject(j);
				ArrayList<point> points =  createStroke(stroke,jpoints);
				extractSegments exSeg = new extractSegments(points);
				exSeg.execute();
				currSubstroke.put(stroke.getString("id"), exSeg.createCurrSubStrokes());
				BBMap.put(stroke.getString("id"), exSeg.getbBox());


				//System.out.println("no of points in " + i + "th sketch and " + j + "th stroke are :" + points.size());


			}

		}




	}

	static ArrayList<point>  createStroke(JSONObject stroke,JSONArray jpoints) throws Exception{

		ArrayList<point> points = new ArrayList<point>();
		JSONArray strokePoints = (JSONArray) stroke.get("points");
		int index = 0;
		for(int i = 0;i<strokePoints.length();i++){
			String stroke_id =  strokePoints.getString(i);
			for(int j = 0;j< jpoints.length();j++){
				if(stroke_id.equals(jpoints.getJSONObject(j).getString("id"))){
					index = j;
					break;
				}

			}

			point newPoint = JsonToPoint(jpoints.getJSONObject(index));
			points.add(newPoint);


		}
		return points;



	}
	static point JsonToPoint(JSONObject object) throws Exception{

		double x = (double)((Integer)object.get("x")).intValue();
		double y = (double)((Integer)object.get("y")).intValue();
		long time = Long.parseLong((String)object.get("time"));

		point n = new point(x,y,time); 

		return n;

	}
	static void getOrigSubstroke(JSONObject sketch,JSONArray jpoints) throws Exception{

		JSONArray jSubStrokes = sketch.getJSONArray("substrokes");

		for(int i=0;i<jSubStrokes.length();i++){

			ArrayList<point> subStroke = createStroke(jSubStrokes.getJSONObject(i), jpoints);
			String parent_id = jSubStrokes.getJSONObject(i).getString("parent");
			if(origSubstroke.keySet().contains(parent_id)){
				origSubstroke.get(parent_id).add(subStroke);
			}
			else
			{
				ArrayList<ArrayList<point>> temp = new ArrayList<ArrayList<point>>();
				temp.add(subStroke);
				origSubstroke.put(parent_id, temp);
			}


		}





	}

	static double calcAccuracy(){
		// Metrics
		double	Total = 0;
		double	AllNoth = 0;
		double TP = 0;
		double FP = 0;
		double TN = 0;
		double FN = 0;
		boolean failures = false;
		// Attempt to match substrokes from the same parent
		// If only 1 substroke from parent -- no segmentation;
		// All matches are True Negatives
		// All extras in current are False Positives
		// If more than 1 substroke from parent -- segmentation; 
		// All matches are True Positives
		// All extras in current are False Positives
		// All extras in original are False Negatives
		for (String key : origSubstroke.keySet()) { // Due to a small bug, there's a few sketches where the stroke didn't get added, so it is missing in current; this makes original a superset at worse when they don't match, so just go through current's strokes
			ArrayList<ArrayList<point>> origList = origSubstroke.get(key);
			ArrayList<ArrayList<point>> currList = currSubstroke.get(key);


			// Get a threshold for corner distance customized for the stroke size
			BB bb = BBMap.get(key);
			if(bb == null)
				continue;
			double threshold = Math.sqrt(Math.pow(bb.max.x - bb.min.x, 2) + Math.pow(bb.max.y - bb.min.y, 2)) / 40.0;

			if (origList.size() == 1) { // No segmentation
				if (currList.size() == 1) {
					TN = TN + 1; // Correctly determine no segmentation
				} else {
					FP = FP + (currList.size() - 1) / 2; // All extra strokes are FP
					failures = true;
				}
			} else { // Segmentation
				// Try to find matches
				double[][] distances = new double[origList.size()][currList.size()];

				for (int j = 0; j < origList.size(); j++) {
					//distances[j] = [];
					for (int  k = 0; k < currList.size(); k++) {
						// distance[j][k] = distance between start and end points summed
						distances[j][k] = dist(origList.get(j).get(0),currList.get(k).get(0)) + dist(origList.get(j).get(origList.get(j).size()-1),currList.get(k).get(currList.get(k).size()-1));
						//distances[j][k] = origList[j].getPoints()[0].distance(currList[k].getPoints()[0]) + origList[j].getPoints()[origList[j].getPoints().length - 1].distance(currList[k].getPoints()[currList[k].getPoints().length - 1]);
					}
				}
				// Use distance matrix to compute metrics
				for (int j = 0; j < distances.length; j++) {
					int k = indexOfMin(distances[j]);
					if (distances[j][k] < threshold * 10) {
						TP = TP + 1; // A match that was cumulatively within 3 pixels of both start and end points
					} else {
						FN = FN + 0.5; // Else, no close match; we assume a miss, which is false negative
						failures = true;
					}
				}
				if (currList.size() > origList.size()) {
					FP = FP + (currList.size() - origList.size()) / 2; // Any extra in currList are FPs
					failures = true;
				}
			}
		}

		Total = Total + 1;
		if (!failures) {
			AllNoth = AllNoth + 1;
		}

		double Accuracy = (TP +TN) / (TP +TN +FP +FN) ;
		System.out.println("TP :"+ TP);
		System.out.println("TN :"+ TN);

		System.out.println("FP :"+ FP);
		System.out.println("FN :"+ FN);

		return (Accuracy*100);


	}
	static double dist(point a,point b){

		double res ;

		res = Math.sqrt((Math.pow((a.x-b.x),2) + Math.pow((a.y-b.y),2)));

		return res;


	}
	static int indexOfMin(double[] arr){

		int index =0;
		double minValue = Double.MAX_VALUE;
		for(int i =0;i<arr.length;i++){
			if(arr[i] < minValue){
				index = i;
				minValue = arr[i];
			}

		}

		return index;



	}
}