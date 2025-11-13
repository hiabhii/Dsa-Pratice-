import java.util.*;

public class ABCChall {

    // --- Read input safely (handles an empty fixed-line) ---
    private static Input readInput(Scanner sc) {
        int n = Integer.parseInt(sc.nextLine().trim());
        String[] items = sc.nextLine().trim().split("\\s+");
        String fixedLine = "";
        if (sc.hasNextLine()) fixedLine = sc.nextLine().trim();
        int[] fixedPos;
        if (fixedLine.isEmpty()) fixedPos = new int[0];
        else {
            String[] parts = fixedLine.split("\\s+");
            fixedPos = new int[parts.length];
            for (int i = 0; i < parts.length; i++) fixedPos[i] = Integer.parseInt(parts[i]);
        }
        return new Input(n, items, fixedPos);
    }

    // --- Build a target array for a given permutation string like "BCA" ---
    private static String[] buildTarget(String perm, int countA, int countB, int countC) {
        List<String> list = new ArrayList<>();
        for (char ch : perm.toCharArray()) {
            int repeat = (ch == 'A' ? countA : ch == 'B' ? countB : countC);
            for (int i = 0; i < repeat; i++) list.add(String.valueOf(ch));
        }
        return list.toArray(new String[0]);
    }

    // --- Check fixed positions compatibility for a candidate target ---
    private static boolean fixedCompatible(String[] current, String[] target, int[] fixed) {
        for (int pos : fixed) {
            if (!current[pos - 1].equals(target[pos - 1])) return false;
        }
        return true;
    }

    // --- Longest Common Subsequence length for two arrays of length n (O(n^2)) ---
    private static int lcsLength(String[] a, String[] b) {
        int n = a.length;
        int m = b.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a[i - 1].equals(b[j - 1])) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[n][m];
    }

    // --- All 6 permutations of A, B, C ---
    private static List<String> permutations() {
        return Arrays.asList("ABC","ACB","BAC","BCA","CAB","CBA");
    }

    // --- Main solver ---
    private static String solve(String[] items, int[] fixed) {
        int n = items.length;
        int countA = 0, countB = 0, countC = 0;
        for (String s : items) {
            if (s.equals("A")) countA++;
            else if (s.equals("B")) countB++;
            else countC++;
        }

        int best = Integer.MAX_VALUE;
        for (String perm : permutations()) {
            String[] target = buildTarget(perm, countA, countB, countC);
            // quick check: target must be length n (it is) and fixed positions compatible
            if (!fixedCompatible(items, target, fixed)) continue;
            int lcs = lcsLength(items, target);
            int moves = n - lcs; // minimal remove-and-insert operations
            best = Math.min(best, moves);
        }

        return best == Integer.MAX_VALUE ? "Impossible" : String.valueOf(best);
    }

    // --- main ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Input in = readInput(sc);
        System.out.println(solve(in.items, in.fixed));
    }

    // --- small input holder class ---
    private static class Input {
        int n;
        String[] items;
        int[] fixed;
        Input(int n, String[] items, int[] fixed) { this.n = n; this.items = items; this.fixed = fixed; }
    }
}