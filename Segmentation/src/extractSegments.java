import java.util.ArrayList;
public class extractSegments{
	ArrayList<point> points;
	ArrayList<point> resampledPoints;
	double S;
	BB bBox;
	ArrayList<Integer> corners;
	ArrayList<Double> straws ;
	extractSegments(ArrayList<point> points){
		this.points = points;
		resampledPoints = new ArrayList<point>();
		corners = new ArrayList<Integer>();
		straws =  new ArrayList<Double>();
	}

	void execute() throws Exception{
		calcBB();
		determineResampleSpace();
		resamplePoints();
		getCorners();

	}

	double dist(point a,point b){

		double res ;

		res = Math.sqrt((Math.pow((a.x-b.x),2) + Math.pow((a.y-b.y),2)));

		return res;


	}

	void calcBB(){

		double xmax=0,ymax=0,xmin=Double.MAX_VALUE,ymin =Double.MAX_VALUE;
		point p;
		for(int i=0;i<points.size();i++){

			p = points.get(i);
			if(p.x<xmin)
				xmin = p.x;

			if(p.y<ymin)
				ymin = p.y;

			if(p.x>xmax)
				xmax = p.x;

			if(p.y>ymax)
				ymax = p.y;


		}
		bBox = new BB(new point(xmax,ymax,0),new point(xmin,ymin,0));

	}
	void determineResampleSpace(){

		double diagonalLength = dist(bBox.min,bBox.max);
		S = diagonalLength/40;


	}

	BB getbBox(){
		return bBox;
	}

	void resamplePoints() throws Exception{

		double D = 0;
		resampledPoints.add(points.get(0));
		double d;
		for(int i= 1;i< points.size();i++){
			point last = points.get(i-1);
			point curr = points.get(i);
			d = dist(last,curr);
			if(D +d >=S){

				double x = last.x + (S - D)/d *(curr.x- last.x);
				double y = last.y + (S - D)/d *(curr.y- last.y);
				point q = new point(x,y,0);
				resampledPoints.add(q);
				points.add(i, q);
				D=0;
			}
			else
				D = D + d;

		}

	}

	void getCorners(){

		corners.add(0);
		int w = 3;   //window
		for(int i = w;i < resampledPoints.size()-w;i++){
			double dist = dist(resampledPoints.get(i-w),resampledPoints.get(i+w));
			straws.add(dist);
		}
		double t = 0.95 * straws.get(straws.size()/2);
		double localMin = Double.MAX_VALUE;
		int minIndex ;
		for(int i = 0;i<straws.size();i++){

			if(straws.get(i)<t){

				localMin = straws.get(i);
				minIndex = i+w;
				while(i<straws.size() && straws.get(i)<t){
					if(straws.get(i)<localMin){
						localMin = straws.get(i);
						minIndex = i+w;
					}
					i++;
				}
				corners.add(minIndex);
			}
		}
		corners.add(resampledPoints.size()-1);
		postProcess();
	}
	void postProcess(){

		boolean cont = true;
		int c1,c2;
		do{
			cont = true;
			for(int i =1;i<corners.size();i++){

				c1 = corners.get(i-1);
				c2 = corners.get(i);
				if(!isLine(c1,c2)){

					int newCorner = halfWayCorner(c1,c2);
					if(newCorner != -1){
						corners.add(i, newCorner);
						cont = false;
					}
				}

			}

		}
		while(!cont);
		for(int i= 1;i<corners.size()-1;i++){

			c1 = corners.get(i-1);
			c2 = corners.get(i+1);

			if(isLine(c1,c2)){
				corners.remove(i);
				i--;
			}

		}



	}

	double pathDist(int a,int b){

		double d =0;

		for(int i =a;i<b;i++)
			d += dist(resampledPoints.get(i),resampledPoints.get(i+1)); 

		return d;

	}

	boolean isLine(int a ,int b){

		double t = 0.95;
		double d = dist(resampledPoints.get(a),resampledPoints.get(b));
		double p = pathDist(a,b);

		if(d/p < t)
			return false;
		else
			return true;
	}

	int halfWayCorner(int a,int b){

		int quarter = (b-a)/2;
		int w =3;
		double minValue  = Double.MAX_VALUE; 
		int minIndex = -1;
		for(int i = a+quarter; i<= b-quarter; i++){
			if(i-w<straws.size() && (i-w)>=0)
				if(straws.get(i-w)<minValue){
					minValue = straws.get(i-w);
					minIndex = i;
				}
		}
		return minIndex;
	}

	ArrayList<ArrayList<point>> createCurrSubStrokes(){

		ArrayList<ArrayList<point>> currSubStroke = new ArrayList<ArrayList<point>>();
		for(int i=0;i< corners.size()-1;i++){

			ArrayList<point> temp = new ArrayList<point>();
			point e1 = resampledPoints.get(corners.get(i));
			point e2 = resampledPoints.get(corners.get(i+1));
			for(int j = 0; j < points.size();j++ ){

				if((e1.x == points.get(j).x) && (e1.y == points.get(j).y)){

					temp.add(e1);j++;
					while(!((e2.x == points.get(j).x) && (e2.y == points.get(j).y))){
						temp.add(points.get(j));
						j++;
					}
					temp.add(e2);
					break;
				}



			}
			currSubStroke.add(temp);




		}
		return currSubStroke;

	}





}
class BB{
	point min,max;
	BB(point max,point min){
		this.max = max;
		this.min = min;	
	}
}