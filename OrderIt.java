import java.util.*;

public class OrderIt {

    // Generate all possible next states after one cut-and-insert operation
    static List<List<Integer>> nextStates(List<Integer> arr, int n) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // remove contiguous segment arr[i..j]
                List<Integer> temp = new ArrayList<>();
                for (int k = 0; k < i; k++) temp.add(arr.get(k));
                for (int k = j + 1; k < n; k++) temp.add(arr.get(k));

                // reinsert the cut segment at all other positions
                for (int pos = 0; pos <= temp.size(); pos++) {
                    if (pos == i) continue; // skip reinserting in the same place
                    List<Integer> cur = new ArrayList<>(temp);
                    cur.addAll(pos, arr.subList(i, j + 1));
                    res.add(cur);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read input
        int n = Integer.parseInt(sc.nextLine().trim());
        sc.nextLine(); // skip "shuffled"
        List<String> shuffled = new ArrayList<>();
        for (int i = 0; i < n; i++) shuffled.add(sc.nextLine());

        sc.nextLine(); // skip "original"
        List<String> original = new ArrayList<>();
        for (int i = 0; i < n; i++) original.add(sc.nextLine());

        // Already sorted case
        if (shuffled.equals(original)) {
            System.out.println(0);
            return;
        }

        // Map original instructions to indices
        Map<String, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < n; i++) idxMap.put(original.get(i), i);

        // Represent both lists as integer sequences
        List<Integer> start = new ArrayList<>();
        for (String s : shuffled) start.add(idxMap.get(s));

        List<Integer> target = new ArrayList<>();
        for (int i = 0; i < n; i++) target.add(i);

        // Bidirectional BFS setup
        Map<List<Integer>, Integer> dist1 = new HashMap<>();
        Map<List<Integer>, Integer> dist2 = new HashMap<>();
        Queue<List<Integer>> q1 = new LinkedList<>();
        Queue<List<Integer>> q2 = new LinkedList<>();

        dist1.put(start, 0);
        dist2.put(target, 0);
        q1.add(start);
        q2.add(target);

        // BFS from both ends
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.size() <= q2.size()) {
                int sz = q1.size();
                for (int s = 0; s < sz; s++) {
                    List<Integer> cur = q1.poll();
                    int step = dist1.get(cur);
                    for (List<Integer> nxt : nextStates(cur, n)) {
                        if (dist1.containsKey(nxt)) continue;
                        if (dist2.containsKey(nxt)) {
                            System.out.println(step + 1 + dist2.get(nxt));
                            return;
                        }
                        dist1.put(nxt, step + 1);
                        q1.add(nxt);
                    }
                }
            } else {
                int sz = q2.size();
                for (int s = 0; s < sz; s++) {
                    List<Integer> cur = q2.poll();
                    int step = dist2.get(cur);
                    for (List<Integer> nxt : nextStates(cur, n)) {
                        if (dist2.containsKey(nxt)) continue;
                        if (dist1.containsKey(nxt)) {
                            System.out.println(step + 1 + dist1.get(nxt));
                            return;
                        }
                        dist2.put(nxt, step + 1);
                        q2.add(nxt);
                    }
                }
            }
        }

        // Fallback (shouldn't happen)
        System.out.println(n);
    }
}