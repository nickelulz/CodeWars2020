import java.util.*;
import java.io.*;

public class prob30 {
	static char[][] solution;
	
	static boolean in_bounds(int r, int c, char[][] grid) {
		return r >= 0 && r < grid.length && c >= 0 && c < grid[r].length;
	}

	static boolean sameTypeSquare(char[][] grid) {
		for (int r = 1; r < grid.length-1; r++)
			for (int c = 1; c < grid[r].length-1; c++)
				if (grid[r][c] == grid[r-1][c  ] && // up
					grid[r][c] == grid[r-1][c-1] && // up left
					grid[r][c] == grid[r  ][c-1] || // left

					grid[r][c] == grid[r-1][c  ] && // up
					grid[r][c] == grid[r-1][c+1] && // up right
					grid[r][c] == grid[r  ][c+1] || // right

					grid[r][c] == grid[r+1][c  ] && // down
					grid[r][c] == grid[r+1][c-1] && // down left
					grid[r][c] == grid[r  ][c-1] || // left

					grid[r][c] == grid[r+1][c  ] && // down
					grid[r][c] == grid[r+1][c+1] && // down right
					grid[r][c] == grid[r  ][c+1])   // right
					return false;
		return true;
	}

	static boolean contains(char[][] grid, char search_for) {
		for (char[] row: grid)
			for (char c: row)
				if (c == search_for)
					return true;
		return false;
	}

	static void loopRecur(char search_for, int r, int c, char[][] grid) {
		if (in_bounds(r, c, grid) && grid[r][c] == search_for) {
			grid[r][c] = 'V';
			loopRecur(search_for, r+1, c, grid);
			loopRecur(search_for, r-1, c, grid);
			loopRecur(search_for, r, c+1, grid);
			loopRecur(search_for, r, c-1, grid);
		}
	}

	static boolean all_connected(char[][] grid) {
		for (int i = 0; i < 2; i++) {
			char search_for = i == 0 ? '#' : '?';
			int start_r = 0, start_c = 0;
			for (int r = 0; r < grid.length; r++)
				for (int c = 0; c < grid[r].length; c++)
					if (grid[r][c] == search_for) {
						start_r = r;
						start_c = c;
					}
			loopRecur(search_for, start_r, start_c, grid);
			if (contains(grid, search_for))
				return false;
		}
		return true;
	}

	static boolean goal(char[][] grid) {
		return !all_connected(grid) && !sameTypeSquare(grid);
	}

	static void solve(char[][] grid) {
		if (!contains(grid, '?')) {
			print(grid);
			if (goal(grid.clone()))
				solution = grid;
			return;
		}

		for (int r = 0; r < grid.length; r++)
			for (int c = 0; c < grid[r].length; c++)
				if (grid[r][c] == '?') {
					char[][] wcopy = grid.clone(), ccopy = grid.clone();
					wcopy[r][c] = '.';
					ccopy[r][c] = '#';
					solve(wcopy);
					solve(ccopy);
				}
	}

	static void print(char[][] grid) {
		for (char[] row: grid)
			System.out.println(String.valueOf(row));
		System.out.println("\n\n");
	}

	public static void main(String[] args) throws FileNotFoundException {
		for (int test_case = 1; test_case <= 8; test_case++) {
			Scanner in = new Scanner(new File("input/prob30-" + test_case + "-in.txt"));
			int size = in.nextInt();
			in.nextLine();
			char[][] grid = new char[size][0];
			for (int i = 0; i < size; i++)
				grid[i] = in.nextLine().toCharArray();
			in.close();

			print(grid);
			solve(grid);
		}
	}
}