package acm;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * NYPD Cordon Blue
 * Problem B
 * 
 * 1997 Mid-Atlantic Regional Programming Contest
 */
public class CrimeScene {

	// Application
	
	public static void main(String[] args) {	
		process(new Scanner(System.in));	
	}
	
	public static void process(Scanner in) {
		
		// Read Input
		int sceneCount = in.nextInt();
		ArrayList<CrimeScene> scenes = new ArrayList<CrimeScene>(sceneCount);
		for (int i = 0; i < sceneCount; i++) {
			int itemCount = in.nextInt();
			CrimeScene scene = new CrimeScene(itemCount);
			for (int p = 0; p < itemCount; p++) {
				scene.addItem(in.nextInt(), in.nextInt());
			}
			scenes.add(scene);
		}

		// Print Output
		for (int i = 0; i < sceneCount; i++) {
			System.out.printf("CRIME SCENE %d: %.3f feet\n", i+1, scenes.get(i).getPerimeter());
		}
		System.out.println("END OF OUTPUT");
	}
	
	// API
	
	private ArrayList<Point> items;

	public CrimeScene(int itemCount) {
		this.items = new ArrayList<Point>(itemCount);
	}
	
	public void addItem(int x, int y) {
		this.items.add(new Point(x, y));
	}
	
	public double getPerimeter() {
		
		Point start = this.items.get(0);
		for (Point item : this.items) {
			if (item.getY() < start.getY())
				start = item;
		}
		
		double peri = 0.0;
		Point current = start;
		double lastAngle = 0.0;
		do {
			Point next = null;
			for (Point item : this.items) {
				if (item == current)
					continue;
				
				if (next == null)
					next = item;
				
				double nextAngle = (current.angleTo(item) + lastAngle) % (Math.PI*2);
				double currentAngle = (current.angleTo(next) + lastAngle) % (Math.PI*2);
				if (nextAngle != lastAngle && nextAngle < currentAngle)
					next = item;
			}
			peri += current.distanceTo(next);
			lastAngle = next.angleTo(current);
			current = next;		
		} while (current != start);
		
		return peri;
	}
	
	// Helper Class
	
	static class Point {
		
		private int x;
		private int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		
		public double angleTo(Point other) {
			double angle = Math.atan2(other.getY()-this.getY(), other.getX()-this.getX());
			if (Double.compare(angle, 0.0) < 0) {
				angle += 2*Math.PI;
			}
			return angle;
		}
		
		public double distanceTo(Point other) {
			return Math.sqrt(Math.pow(other.getX()-this.getX(), 2.0) + Math.pow(other.getY()-this.getY(), 2.0));
		}	
	}

}
