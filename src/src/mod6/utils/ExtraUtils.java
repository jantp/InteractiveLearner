package mod6.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 12/3/2015.
 */
public class ExtraUtils {
    public static <E> List<E> mergeLists (List<E> list1, List<E> list2) {
        list1.addAll(list2);
        return list1;
    }
    public static <E> List<E> mergeListsNoDups (List<E> list1, List<E> list2) {
        list1.removeAll(list2);
        list1.addAll(list2);
        return list1;
    }

}
