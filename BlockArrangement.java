import java.util.*;

public class BlockArrangement {

    // --- Read input into an array ---
    private static String[] grabTokens(Scanner in, int size) {
        String[] toks = new String[size];
        for (int ix = 0; ix < size; ix++) toks[ix] = in.next();
        return toks;
    }

    // --- Count X, Y, Z for three types ---
    private static int[] getCounts(String[] arr) {
        int[] ct = new int[3];
        for (String s : arr) {
            if (s.equals("A")) ct[0]++;
            else if (s.equals("B")) ct[1]++;
            else ct[2]++;
        }
        return ct;
    }

    // --- Check if fixed positions make order unreachable ---
    private static boolean invalidFixedPos(String[] arr, int[] pos, int[] ct) {
        int aLimit = ct[0], bLimit = aLimit + ct[1], cLimit = bLimit + ct[2];
        for (int x : pos) {
            String cur = arr[x - 1];
            if (cur.equals("A") && x > aLimit) return true;
            if (cur.equals("B") && (x <= aLimit || x > bLimit)) return true;
            if (cur.equals("C") && x <= bLimit) return true;
        }
        return false;
    }

    // --- Calculate min swaps to match a layout ---
    private static int calcMinSwaps(String[] src, String[] desired) {
        List<Integer> idxOrigin = new ArrayList<>();
        List<Integer> idxTarget = new ArrayList<>();
        Map<String, Queue<Integer>> place = new HashMap<>();
        for (int i = 0; i < src.length; i++) {
            place.computeIfAbsent(src[i], k -> new LinkedList<>()).add(i);
        }
        for (int i = 0; i < desired.length; i++) {
            int id = place.get(desired[i]).poll();
            idxOrigin.add(id);
            idxTarget.add(i);
        }
        int swaps = 0;
        for (int i = 0; i < idxOrigin.size(); i++) {
            if (!idxOrigin.get(i).equals(idxTarget.get(i))) swaps++;
        }
        return swaps;
    }

    // --- Return all block orders possible ---
    private static List<String> allArrangements() {
        return Arrays.asList("ABC", "ACB", "BAC", "BCA", "CAB", "CBA");
    }

    // --- Build a layout for a specific arrangement ---
    private static String[] produceLayout(String pattern, int[] counts) {
        List<String> res = new ArrayList<>();
        for (char ch : pattern.toCharArray()) {
            int lim = ch == 'A' ? counts[0] : ch == 'B' ? counts[1] : counts[2];
            for (int i = 0; i < lim; i++) res.add(String.valueOf(ch));
        }
        return res.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int len = scan.nextInt();
        String[] sequence = grabTokens(scan, len);
        scan.nextLine();
        String positionInput = scan.nextLine().trim();
        int[] lockedPos = positionInput.isEmpty() ? new int[0] : Arrays.stream(positionInput.split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] blockSizes = getCounts(sequence);
        int minResult = Integer.MAX_VALUE;
        boolean possible = false;

        for (String perm : allArrangements()) {
            if (invalidFixedPos(sequence, lockedPos, blockSizes)) continue;
            String[] finalForm = produceLayout(perm, blockSizes);
            int val = calcMinSwaps(sequence, finalForm);
            minResult = Math.min(minResult, val);
            possible = true;
        }
        System.out.println(possible && minResult != Integer.MAX_VALUE ? minResult : "Impossible");
    }
}
