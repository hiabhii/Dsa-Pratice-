import java.util.*;

public class Main1 {

    static class Person {
        String id;
        int ability;
        Person(String i, int a) { id = i; ability = a; }
    }

    static class Duo {
        int x, y;
        Duo(int _x, int _y) { x = _x; y = _y; }
    }

    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        
        List<Person> peopleList = getPeople(inputReader);
        
        Map<String, Integer> idToPosition = createMapping(peopleList);
        
        int friendCount = inputReader.nextInt();
        List<Duo> friendPairs = getDuos(inputReader, friendCount, idToPosition);
        
        int rivalCount = inputReader.nextInt();
        List<Duo> rivalPairs = getDuos(inputReader, rivalCount, idToPosition);
        
        int maxAbility = inputReader.nextInt();
        
        List<List<Integer>> squads = formSquads(peopleList, friendPairs);
        Map<Integer, Set<Integer>> opponentMap = createOpponentMap(peopleList.size(), rivalPairs);
        
        System.out.println(findBest(peopleList, squads, opponentMap, maxAbility));
    }

    static List<Person> getPeople(Scanner sc) {
        int count = sc.nextInt();
        List<Person> tempList = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            tempList.add(new Person(sc.next(), 0));
        }
        for (int i = 0; i < count; i++) {
            tempList.get(i).ability = sc.nextInt();
        }
        
        return tempList;
    }

    static Map<String, Integer> createMapping(List<Person> persons) {
        Map<String, Integer> mapping = new HashMap<>();
        for (int i = 0; i < persons.size(); i++) {
            mapping.put(persons.get(i).id, i);
        }
        return mapping;
    }

    static List<Duo> getDuos(Scanner sc, int count, Map<String, Integer> mapping) {
        List<Duo> duoList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String first = sc.next();
            String second = sc.next();
            duoList.add(new Duo(mapping.get(first), mapping.get(second)));
        }
        return duoList;
    }

    static List<List<Integer>> formSquads(List<Person> persons, List<Duo> friends) {
        int total = persons.size();
        int[] leaders = new int[total];
        
        for (int i = 0; i < total; i++) {
            leaders[i] = i;
        }
        
        for (Duo fr : friends) {
            int lead1 = getLeader(leaders, fr.x);
            int lead2 = getLeader(leaders, fr.y);
            if (lead1 != lead2) {
                leaders[lead2] = lead1;
            }
        }
        
        return groupPeople(leaders, total);
    }

    static int getLeader(int[] leaders, int person) {
        if (leaders[person] != person) {
            leaders[person] = getLeader(leaders, leaders[person]);
        }
        return leaders[person];
    }

    static List<List<Integer>> groupPeople(int[] leaders, int total) {
        Map<Integer, List<Integer>> grouping = new HashMap<>();
        
        for (int i = 0; i < total; i++) {
            int lead = getLeader(leaders, i);
            grouping.computeIfAbsent(lead, k -> new ArrayList<>()).add(i);
        }
        
        return new ArrayList<>(grouping.values());
    }

    static Map<Integer, Set<Integer>> createOpponentMap(int size, List<Duo> opponents) {
        Map<Integer, Set<Integer>> opponentNetwork = new HashMap<>();
        
        for (int i = 0; i < size; i++) {
            opponentNetwork.put(i, new HashSet<>());
        }
        
        for (Duo opp : opponents) {
            opponentNetwork.get(opp.x).add(opp.y);
            opponentNetwork.get(opp.y).add(opp.x);
        }
        
        return opponentNetwork;
    }

    static boolean isValidSelection(List<List<Integer>> chosenGroups, 
                                   Map<Integer, Set<Integer>> opponentNetwork) {
        Set<Integer> included = new HashSet<>();
        
        for (List<Integer> group : chosenGroups) {
            for (int person : group) {
                for (int opponent : opponentNetwork.get(person)) {
                    if (included.contains(opponent)) {
                        return false;
                    }
                }
            }
            included.addAll(group);
        }
        
        return true;
    }

    static int calculateTotalAbility(List<List<Integer>> chosenGroups, List<Person> persons) {
        int total = 0;
        for (List<Integer> group : chosenGroups) {
            for (int index : group) {
                total += persons.get(index).ability;
            }
        }
        return total;
    }

    static int findBest(List<Person> persons, List<List<Integer>> squads,
                       Map<Integer, Set<Integer>> opponentNetwork, int abilityLimit) {
        int maxCount = 0;
        int squadCount = squads.size();
        
        for (int selection = 0; selection < (1 << squadCount); selection++) {
            List<List<Integer>> picked = new ArrayList<>();
            int personCount = 0;
            
            for (int i = 0; i < squadCount; i++) {
                if ((selection & (1 << i)) != 0) {
                    picked.add(squads.get(i));
                    personCount += squads.get(i).size();
                }
            }
            
            if (isValidSelection(picked, opponentNetwork) && 
                calculateTotalAbility(picked, persons) <= abilityLimit) {
                maxCount = Math.max(maxCount, personCount);
            }
        }
        
        return maxCount;
    }
}