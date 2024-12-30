import java.util.*;

public class GA {
    public static List<Path> ga(int INITSIZE, int LOOP, int QUESTION_NUM, Random random,
                                List<Path> paths, HashMap<Integer, List<SubPath>> distanceMap,
                                Set<Path> Non_repeating_sets) {
        for (int i = 1; i <= LOOP; i++) {
//            if(paths.size() == 1 || paths.size() == 0){
//                break;
//            }
            if(paths.size() <= 3){
                break;
            }

//            if (i % 100 == 0)
//                System.out.println("=================>" + i + "/" + LOOP + "===============>");
            List<Path> temp = new ArrayList<>();
            while (temp.size() < INITSIZE) {
                float v = random.nextFloat();
                if (v < 0.3) {//这里最好取0.3

                    //变异
                    int rand = random.nextInt(paths.size());
//                    System.out.println("=================>"+ i + "/" + LOOP +"beforemutation"+"===============>");
                    List<Path> mutation = Mutation.mutation(QUESTION_NUM, distanceMap, paths.get(rand));
//                    System.out.println("=================>"+ i + "/" + LOOP +"aftermutation"+"===============>");
                    for (Path path : mutation) {
                        if (!Non_repeating_sets.contains(path)) {
                            Non_repeating_sets.add(path);
                            temp.add(path);
                        }
                    }
                } else {
                    //交叉
                    int rand1 = random.nextInt(paths.size());
                    int rand2 = random.nextInt(paths.size());
                    while (rand1 == rand2) {
                        rand1 = random.nextInt(paths.size());
                    }
//                    System.out.println("=================>"+ i + "/" + LOOP +"beforecrossover"+"===============>");
                    List<Path> crossover = Crossover.crossover(QUESTION_NUM, paths.get(rand1).path, paths.get(rand2).path, distanceMap);
//                    System.out.println("=================>"+ i + "/" + LOOP +"aftercrossover"+"===============>");
                    for (int k = 0; crossover != null && k < crossover.size(); k++) {
                        if (!Non_repeating_sets.contains(crossover.get(k))) {
                            Non_repeating_sets.add(crossover.get(k));
                            temp.add(crossover.get(k));
                        }
                    }
                }
            }//end while 对整个种群进行进化

            paths.addAll(temp);//paths相当于是P，这里的操作相当于PUQ

            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, INITSIZE);

            //这个步骤是输出最终的路径
            //我们思考一个问题，是不是有的路径其实一直在我们的最后一个种群中，但是因为这个步骤的失误，所以没有输出呢？我们可以定向的找一找
            //当NSort的参数为-1时表示输出最终的路径
            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, -1);
            //System.out.println("-----"+i+"-----"+paths.size()+"-----"+Non_repeating_sets.size());
        }//end LOOP
        return paths;
    }
}
