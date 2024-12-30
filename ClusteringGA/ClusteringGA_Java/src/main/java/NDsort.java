import java.util.*;

public class NDsort {
    //questionNum中是问题的编号,pathList是需要排序的path,int是种群大小
    public static List<Path> sort(int questionNum, List<Path> pathList, int num) {
        List<Path> res = new ArrayList<>();
        int n = pathList.size();
        int[] np = new int[n]; //被支配的个体数量（被多少个个体支配了）
        List<List<Integer>> sp = new ArrayList<>();//sp中保存的是被当前个体支配的点
        for (int i = 0; i < pathList.size(); i++) {
            sp.add(new ArrayList<>());//表示支配个体的集合
        }
        List<Integer> font = new ArrayList<>();
        //结束对整个种群进行支配关系索引的过程
        for (int i = 0; i < n; i++) {
            //统计种群中个体i被其他个体支配的情况
            for (int j = i + 1; j < n; j++) {
                //i支配j
                if (is_dominated(questionNum, pathList.get(i), pathList.get(j))) {
                    sp.get(i).add(j);
                    np[j]++;
                }
                //j支配i
                if (is_dominated(questionNum, pathList.get(j), pathList.get(i))) {
                    sp.get(j).add(i);
                    np[i]++;
                }
            }//end for j


            //如果统计了一轮发现没有人能够支配个体i,
            //即np[i]==0,并且res的集合中还有余量
            //就把这些PF前沿上的解直接放到res种群中
            if (np[i] == 0 && res.size() < num) {
                res.add(pathList.get(i));
                font.add(i);
            }
            //num==-1的情况
            //就把这些PF前沿上的解直接放到res种群中

            if (np[i] == 0 && num == -1) {
                res.add(pathList.get(i));
                font.add(i);
            }
        }//end for 结束对整个种群进行支配关系索引的过程

        //num==-1的情况
        //即是在最后仅仅返回PF上所有的解
        if (num == -1) {
            return res;
        }

        //font.size() != 0，也就是说已经有当前种群的PF了
        while (font.size() != 0) {
            List<Integer> nextLevel = new ArrayList<>();
            for (int i = 0; i < font.size(); i++) {//遍历当前font中所有的个体
                for (int j = 0; j < sp.get(font.get(i)).size(); j++) {//j遍历的时当前font(i)所支配的所有个体
                    int k = sp.get(font.get(i)).get(j);
                    np[k]--;//减去已经进入PF中个体的支配度，则余下的还被支配的数量
                    if (np[k] == 0) {
                        nextLevel.add(k);
                    }
                }
            }//end for

            font.clear();
            font.addAll(nextLevel);
            //表示非支配排序还接的住的情况
            if (res.size() + nextLevel.size() <= num) {
                for (Integer integer : nextLevel) {
                    res.add(pathList.get(integer));
                }
            } else {
                //数量已经比num中数量多了，则使用similaritysort
                // System.out.println("sortNums" + nextLevel.size());
                List<Path> paths = new ArrayList<>();
                for (Integer integer : nextLevel) {
                    paths.add(pathList.get(integer));
                }

                //如果想要测试，不含SimilaritySort的版本，去掉此处的SimilaritySort即可
                similaritySort(paths);

                for (int i = 0; i < paths.size() && res.size() < num; i++) {
                    res.add(paths.get(i));
                }
                //return res;
            }//end if
            nextLevel.clear();
        }//end while
        return res;
    }

    //相似度计算
    public static void similaritySort(List<Path> paths) {

        Object[][] pathArray = new Object[paths.size()][2];
        int n = paths.size();//传入路径的数量
        for (int i = 0; i < n; i++) {
            pathArray[i][0] = paths.get(i);
            pathArray[i][1] = 0;
        }
        //计算相似度
        for (int i = 0; i < n; i++) {
            //遍历路径
            Set<String> set = new HashSet<>();
            //path.get(i)即取出第i条路径.path.size即是路径中中转点的数量
            for (int j = 0; j < paths.get(i).path.size() - 1; j++) {
                //遍历中转点
                //会发现以下这两行中存的是一样的路径的置换，例如set.get(0)=3501735005,而set.get(1)=3500535017
                set.add("" + paths.get(i).path.get(j) + paths.get(i).path.get(j + 1));
//                set.add("" + paths.get(i).path.get(j + 1) + paths.get(i).path.get(j));
            }
            //此时ipass是i的后一条路径
            //同样的方式去遍历i的后一个个体ipass
            for (int ipass = i + 1; ipass < n; ipass++) {
                for (int j = 0; j < paths.get(ipass).path.size() - 1; j++) {
                    String cur = "" + paths.get(ipass).path.get(j) + paths.get(ipass).path.get(j + 1);
                    if (set.contains(cur)) {
                        pathArray[i][1] = (int) pathArray[i][1] + 1;
                        pathArray[ipass][1] = (int) pathArray[ipass][1] + 1;
                    }
                }
            }
            pathArray[i][1] = (float) ((int) pathArray[i][1] / (float) paths.get(i).path.size());
        }
        Arrays.sort(pathArray, Comparator.comparingDouble(o -> (float) o[1]));
        paths.clear();
        for (int i = 0; i < pathArray.length; i++) {
            paths.add((Path) pathArray[i][0]);
        }
    }

    private static boolean is_dominated(int questionNum, Path path1, Path path2) {
        if (questionNum == 1 || questionNum == 101)
            return is_dominated1(path1, path2);
        else if (questionNum <= 5)
            return is_dominated2(path1, path2);
        else
            return is_dominated3(path1, path2);
    }

    private static boolean is_dominated1(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.block < path2.block) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.block <= path2.block) {
            small_and_equal = small_and_equal + 1;
        }
        if (small_and_equal == 2 && small >= 1) {
            return true;
        } else return false;
    }

    private static boolean is_dominated2(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.block < path2.block) {
            small = small + 1;
        }
        if (path1.cross < path2.cross) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.block <= path2.block) {
            small_and_equal = small_and_equal + 1;
        }
        if (path1.cross <= path2.cross) {
            small_and_equal = small_and_equal + 1;
        }
        if (small_and_equal == 3 && small >= 1) {
            return true;
        } else return false;
    }

    //这里应该有七个目标，即通过路径的长度，和六个方向的目标
    private static boolean is_dominated3(Path path1, Path path2) {
        int small_and_equal = 0;
        int small = 0;
        if (path1.distance < path2.distance) {
            small = small + 1;
        }
        if (path1.distance <= path2.distance) {
            small_and_equal = small_and_equal + 1;
        }
        for (int i = 0; i < path1.objectives.length; i++) {
            if (path1.objectives[i] < path2.objectives[i]) {
                small = small + 1;
            }
            if (path1.objectives[i] <= path2.objectives[i]) {
                small_and_equal = small_and_equal + 1;
            }
        }

        if (small_and_equal == path1.objectives.length + 1 && small >= 1) {
            return true;
        } else return false;
    }

}
