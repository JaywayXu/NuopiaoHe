import java.util.*;
import java.util.stream.Collectors;

public class ClusteringGAMutation {

    public static List<Integer> Mutation(int QUESTION_NUM, HashMap<Integer, List<SubPath>> distanceMap, Path path) {
        //传入的第一个参数是一个HashMap类型的distanceMap，第二个参数是一条路径
        Random random = new Random();
        List<Path> pathList = new ArrayList<>(2);
//       path的结构类似于 Path{path=[30010, 30012, 30015, 32015, 32005, 35005, 35027, 35035, 21035, 7035, 5035, 5027, 5005, 7005, 7018, 10018, 10021, 10027, 17027, 17025, 15025], distance=149, block=8, cross=15, objectives=[]}


        //向后变异
        int mutationPoint = random.nextInt(path.path.size() - 2);//变异点位不可以是起点、终点以及终点前一个点
        int goal = path.path.get(path.path.size() - 1);//目标终点
        Path before = new Path();
        //刚开始的时候是before的长度为0,第二个条件是要达到目标点
        while (before.path.size() == 0 || before.path.get(before.path.size() - 1) != goal) {
//            System.out.println("=================>"+"before-beforeDomutation"+"===============>");
            before = doMutation(QUESTION_NUM, "before", goal, mutationPoint, distanceMap, path.path);
//            System.out.println("=================>"+"after-beforeDomutation"+"===============>");
            //System.out.println(before);
        }//end while
        pathList.add(before);

        //向前变异
        int mutationPointAfter = random.nextInt(path.path.size() - 2) + 2;//变异点位移除起点、终点以及终点前一个点
        int goalAfter = path.path.get(0);//目标起点
        Path after = new Path();
        while (after.path.size() == 0 || after.path.get(0) != goalAfter) {
//            System.out.println("=================>"+"before-afterDomutation"+"===============>");
            after = doMutation(QUESTION_NUM, "after", goalAfter, mutationPointAfter, distanceMap, path.path);
//            System.out.println("=================>"+"after-afterDomutation"+"===============>");
            //System.out.println(after);
        }//end while
        pathList.add(after);
        return path.path;
    }

    //传入参数包括，向前或者向后变异的方法，目标点位，随机变异点位，全局有向地图，变异前的路径path
    public static Path doMutation(int QUESTION_NUM, String method, int goal, int randomPoint, HashMap<Integer, List<SubPath>> distanceMap, List<Integer> path) {
        Random r = new Random();
        List<Integer> newPath = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        //before是向后变异，而保存前面的部分
        //因此将randompoint之前的都保存下来，而randompoint之后的重新进行生成
        //因此，此时的newPath的长度就是randomPoint+1
        if (method.equals("before")) {
            for (int i = 0; i <= randomPoint; i++) {
                set.add(path.get(i));
                newPath.add(path.get(i));
            }
        } else {
            //after是向前变异，而保存后面的部分
            for (int i = path.size() - 1; i >= randomPoint; i--) {
                set.add(path.get(i));
                newPath.add(path.get(i));
            }
        }
        //PreKey之前的部分已经生成好了，接下来就是从PreKey之后开始补全
        int preKey = newPath.get(newPath.size() - 1);
        //补全到目标点的路径
        while (preKey != goal) {
            //set的设置是为了防止重复路径
            //因此除了pro12不需要使用set进行判断，其余算例需要使用set集合进行判断
            List<SubPath> subPaths;
            if (QUESTION_NUM == 12) {
                subPaths = distanceMap.get(preKey).stream().collect(Collectors.toList());
            } else {
                subPaths = distanceMap.get(preKey).stream().filter(subPath -> !set.contains(subPath.getKey())).collect(Collectors.toList());
            }

//            subPaths = distanceMap.get(preKey).stream().filter(subPath -> !set.contains(subPath.getKey())).collect(Collectors.toList());

            if (subPaths.size() == 0) {
                break;
            }
            SubPath subPath = subPaths.get(r.nextInt(subPaths.size()));
            preKey = subPath.getKey();
            newPath.add(preKey);

            set.add(preKey);
        }
        //因为如果是after状态，则是反着的，因此需要有一个反向的操作
        if (method.equals("after")) {
            Collections.reverse(newPath);
        }
        return Path.calculatePath(newPath, distanceMap);
    }

}
