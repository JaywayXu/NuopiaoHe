import java.util.*;

public class Crossover {
    public static List<Path> crossover(int QUESTION_NUM, List<Integer> path1, List<Integer> path2, HashMap<Integer, List<SubPath>> distanceMap) {
        //对于12号问题，是可以允许具有重复路径的，因此此处传入子问题的编号QUESTION_NUM

        List<Path> pathList = new ArrayList<>(2);
        Map<Integer, Integer> map = new HashMap<>();
        //复制path1中的信息到map中
        for (int i = 2; i < path1.size() - 2; i++) {
            map.put(path1.get(i), i);
//        map是一个 映射，前面是path上的节点，后面对应着节点所连接的序号
        }

        List<Integer> crossPoint1 = new ArrayList<>();
        List<Integer> crossPoint2 = new ArrayList<>();

        for (int i = 2; i < path2.size() - 2; i++) {
            int key = path2.get(i);//遍历path2中的每个节点
            //如果map中包含key节点，并且不包含Key的前一个节点
            if (map.containsKey(key) && !map.containsKey(path2.get(i - 1))) {
                crossPoint1.add(map.get(key));
                //crossPoint1中对应着path1中满足条件的交叉点的索引
                crossPoint2.add(i);
                //i中对应着path2中此点的索引，因此实际上path2.get(i)==path1.get(map.get(k))
            }
        }

        if (crossPoint1.isEmpty()) {
            return null;
        }

        //对于 path1中可行的所有交叉点
        for (int i = 0; i < crossPoint1.size(); i++) {
            //将path1的前半部分和path2的后半部分重新拼接
            List<Integer> list1 = new ArrayList<>(path1.subList(0, crossPoint1.get(i)));
            list1.addAll(path2.subList(crossPoint2.get(i), path2.size()));
            //System.out.println(list1);

            //在pro1-pro11中需要判断判断是否有重复路径，如果有重复路径，删除重复路径
            //但是如果对pro12做相应约束，会找不到解决方案

//           if(check(list1))  pathList.add(Path.calculatePath(list1, distanceMap));

            if (QUESTION_NUM == 12) {
                    pathList.add(Path.calculatePath(list1, distanceMap));
            } else if (check(list1)) pathList.add(Path.calculatePath(list1, distanceMap));
            List<Integer> list2 = new ArrayList<>(path2.subList(0, crossPoint2.get(i)));
            list2.addAll(path1.subList(crossPoint1.get(i), path1.size()));
            //System.out.println(list2);
            //在pro1-pro11中需要判断判断是否有重复路径，如果有重复路径，删除重复路径

//            if (check(list2)) pathList.add(Path.calculatePath(list2, distanceMap));

            if (QUESTION_NUM == 12) {
                    pathList.add(Path.calculatePath(list2, distanceMap));
            } else if (check(list2)) pathList.add(Path.calculatePath(list2, distanceMap));
        }

        return pathList;
    }

    public static boolean check(List<Integer> list) {
        Set<Integer> set = new HashSet<>();
        for (Integer integer : list) {
            if (set.contains(integer)) {
                return false;
            } else {
                set.add(integer);
            }
        }
        return true;
    }


}

