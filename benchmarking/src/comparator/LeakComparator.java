package comparator;

import leaks.Leak;

import java.util.*;

public class LeakComparator {

    Comparator<Leak> sortByClassName
            = (leak1, leak2) -> leak1.getClassName().compareToIgnoreCase(leak2.getClassName());

    Comparator<Leak> sortByMethodName
            = (leak1, leak2) -> leak1.getMethodName().compareToIgnoreCase(leak2.getMethodName());

    Comparator<Leak> sortBySinkMethod
            = (leak1, leak2) -> leak1.getSinkMethod().compareToIgnoreCase(leak2.getSinkMethod());


    private ArrayList<Leak> sortLeaks(ArrayList<Leak> leaks){
        ArrayList<Leak> sortedLeaks = new ArrayList<>();
        leaks
                .stream()
                .sorted(
                       sortByClassName
                               .thenComparing(sortByMethodName)
                               .thenComparing(sortBySinkMethod)
                )
                .forEach(leak -> sortedLeaks.add(leak));

        return sortedLeaks;
    }


    public ArrayList<Leak> groupLeaks(ArrayList<Leak> leaks){
        if(leaks.isEmpty()){return leaks;}
        leaks = sortLeaks(leaks);
        ArrayList<Leak> groupedLeaks = new ArrayList<>();
        Iterator<Leak> iterator = leaks.iterator();
        int i = 0;
        groupedLeaks.add(leaks.get(i));
        iterator.next();
        while(iterator.hasNext()){
            Leak currentLeak = groupedLeaks.get(i);
            Leak nextLeak = iterator.next();

            if(currentLeak.compareTo(nextLeak) == 0){
                currentLeak.enhanceFields(nextLeak);
            }else {
                groupedLeaks.add(nextLeak);
                i++;
            }
        }

        return groupedLeaks;
    }
}




