import java.util.*;

public class APClustering {

    public static ArrayList<ArrayList<Path>> apclustering(List<Path> paths) {
        //ArrayList<Path> result = new ArrayList<>(); // 测试
        int maxLength = FindLength(paths); // 找到种群中路径的最长长度
        int[][] clustering = extractPaths(paths,maxLength); // 提取path
        double[][] s = InitialArray(clustering,paths,maxLength); // 生成s矩阵：每个个体与其他个体的相似度（欧式距离）
        double[][] dS = new double[paths.size()][paths.size()]; // 用来存放S的对角线元素
        double p = 0; // p记录s第三列的中位数，即相似度值的中位数
        p = FindMData(s); // 查找相似度中位数

        // 生成相似度矩阵Similarity
        double[][] S = new double[paths.size()][paths.size()];
        int l = 0;
        for(int i=0;i<paths.size();i++){
            for(int j=0;j<paths.size();j++){
                if(i==j){S[i][j]=p;} // 对角线存相似度中位数
                else {S[i][j] = s[l][2];l++;} // 除对角线外元素存s矩阵第三列相似度值
            }
        }
        for(int i=0;i<paths.size();i++){
            dS[i][i] = S[i][i];
        }

        // 进入主循环迭代
        int convits = 50; // 循环终止次数（当簇中心50代没变说明迭代完成，退出循环）
        int maxits = 500; // 最大迭代次数
        boolean dn = false; // 循环结束标志变量
        int counter = 0; // 循环计数器
        double[][] R = new double[paths.size()][paths.size()]; // 责任度矩阵
        double[][] Rold = new double[paths.size()][paths.size()]; // 记录R更新前的矩阵
        double[][] A = new double[paths.size()][paths.size()]; // 可用度矩阵
        double[][] Aold = new double[paths.size()][paths.size()]; // 记录A更新前的矩阵
        double[][] AS = new double[paths.size()][paths.size()]; // 中间矩阵
        double[][] Rp = new double[paths.size()][paths.size()]; // 作为R到A变化的过渡矩阵
        ArrayList<ArrayList<Integer>> RA = new ArrayList<>(); // 决策矩阵（嵌套生成动态二维数组）
        double[][] record = new double[paths.size()][convits]; // 用来循环地记录50次迭代信息
        double[][] dR = new double[paths.size()][paths.size()]; // 用来存放R的对角线元素
        double[][] dA = new double[paths.size()][paths.size()]; // 用来存放A的对角线元素

        while (!dn){
            counter++;
            Rold = R; // 将更新前的R提前记录下来(为后续加入阻尼系数提供方便）
            AS = calMetrices(A,S,1); // 中间矩阵表示综合评价，点k作为i的簇中心的适应度

            // 查找AS中的第一大值以及第二大值
            int numRows = AS.length; // 行数
            int numCols = AS[0].length; // 列数
            double[] Y = new double[numRows]; // 每行的最大值
            int[] I = new int[numRows]; // 每行最大值的索引
            double[] Y2 = new double[numRows]; // 每行的第二大值
            int[] I2 = new int[numRows]; // 每行第二大值的索引
            // 遍历每一行
            for (int i = 0; i < numRows; i++) {
                double maxVal = Double.NEGATIVE_INFINITY; // 定义成负无穷大方便查找最大值
                double secondMaxVal = Double.NEGATIVE_INFINITY;
                int maxIndex = -1;
                int secondMaxIndex = -1;
                // 遍历当前行的每个元素
                for (int j = 0; j < numCols; j++) {
                    double currentVal = AS[i][j];
                    if (currentVal > maxVal) {
                        // 更新第二大值为之前的最大值
                        secondMaxVal = maxVal;
                        secondMaxIndex = maxIndex;
                        // 更新最大值
                        maxVal = currentVal;
                        maxIndex = j;
                    } else if (currentVal > secondMaxVal) {
                        // 更新第二大值
                        secondMaxVal = currentVal;
                        secondMaxIndex = j;
                    }
                }
                // 记录当前行的结果
                Y[i] = maxVal;
                I[i] = maxIndex;
                Y2[i] = secondMaxVal;
                I2[i] = secondMaxIndex;
                //System.out.println("i");
            }

            // 更新R责任度矩阵（将S中的每一个元素与该元素所处行最大值相减）
            double[][] Xpro = new double[paths.size()][paths.size()]; // 将每行最大值填充到一个新矩阵
            for (int i = 0; i < paths.size(); i++) {
                for (int j = 0; j < paths.size(); j++) {
                    Xpro[i][j] = Y[i];
                }
            }
            R = calMetrices(S,Xpro,2); // 按公式计算R矩阵
            // 采取手段使本来优秀的个体更加优秀
            for(int k = 0;k<paths.size();k++){
                int num = I[k]; // 用来记录S中每一行最大值的位置（列索引）
                R[k][num] = S[k][num] - Y2[k]; // 将R中原来每一行最大值替换为最大值 - 第二大值
            }
            R = addLam(R,Rold,0.5); // 加入阻尼系数(R=(1-lam)*R+lam*Rold)
            for(int i=0;i<paths.size();i++){
                dR[i][i] = R[i][i]; // 将R矩阵对角线元素保存
            }

            // 更新A可用度矩阵
            Aold = A; // 将更新前的A提前记录下来(为后续加入阻尼系数提供方便）
            Rp = R; // Rp作为过渡矩阵
            for(int i=0;i<paths.size();i++){
                for(int j=0;j<paths.size();j++){
                    if(Rp[i][j] < 0 && i != j){
                        Rp[i][j] = 0; // 除R(k,k)外，将R中的负数变为0，忽略不适合的点的不适合程度信息
                    }
                }
            }
            for(int i=0;i<paths.size();i++){
                Rp[i][i] = R[i][i]; // Rp中对角线的值替换为之前R中的对角线的值
            }
            double[][] Ypro_add = new double[1][paths.size()]; // 按列求和
            double[][] Ypro = new double[paths.size()][paths.size()]; // 将求和结果扩充成一个矩阵
            for (int j = 0; j < paths.size(); j++) {
                double a = 0;
                for (int i = 0; i < paths.size(); i++) {
                    a += Rp[i][j];
                    Ypro_add[0][j] = a; // 求和
                }
            }
            for (int i = 0; i < paths.size(); i++) {
                for (int j = 0; j < paths.size(); j++) {
                    Ypro[i][j] = Ypro_add[0][j]; // 扩充
                }
            }
            A = calMetrices(Ypro,Rp,2); // A=repmat(sum(Rp,1),[N,1])-Rp;按公式计算A矩阵
            for(int i=0;i<paths.size();i++){
                dA[i][i] = A[i][i]; // 将A矩阵对角线元素保存
            }
            for(int i=0;i<paths.size();i++){
                for(int j=0;j<paths.size();j++){
                    if(i != j && A[i][j] > 0){ A[i][j] = 0;} // 除A(k,k)外其他大于0的值置为0
                }
            }
            for(int i=0;i<paths.size();i++){
                A[i][i] = dA[i][i]; // 将之前保留的A对角线元素换到新的A中
            }
            A = addLam(A,Aold,0.5); // 加入阻尼系数A=(1-lam)*A+lam*Aold;
            for(int i=0;i<paths.size();i++){
                dA[i][i] = A[i][i]; // 将A矩阵对角线元素保存
            }

            int[] E = new int[paths.size()]; // E是一个逻辑向量，记录了哪些点被判定为簇中心
            for(int i=0;i<paths.size();i++){
                if(dA[i][i] + dR[i][i] > 0){
                    E[i] = 1;
                }
            }
            // 将循环计算结果列向量E放入矩阵e中，注意是循环存放结果
            // 即第一次循环得出的E放到N*50的e矩阵的第一列，第51次的结果又放到第一列
            double[][] e = new double[paths.size()][convits];
            // 计算列索引
            int colIndex = (counter - 1) % convits; // Java 的 % 操作符等效于 MATLAB 的 mod
            if (colIndex < 0) colIndex += convits; // 处理负数情况，确保结果在 [0, convits-1]
            colIndex++; // MATLAB 的索引从 1 开始，Java 的索引从 0 开始，所以加 1
            // 将 E 的内容赋值到 e 的第 colIndex 列
            for (int row = 0; row < E.length; row++) {
                e[row][colIndex - 1] = E[row]; // 注意 Java 的列索引从 0 开始
            }

            int K = 0;
            for (int i = 0; i < paths.size(); i++) {
                if(E[i] == 1){K++;} // 计算 E 中值为 1 的数量，即当前被判定为簇中心的点的个数K
            }

            // 最终判断
            if (counter >= convits || counter >= maxits) {
                // 将e中元素按行求和放进se中
                double[] se = new double[paths.size()]; // se是一个列向量，E的convits次迭代和
                for (int i = 0; i < paths.size(); i++) {
                    double sumse = 0;
                    for (int j = 0; j < convits; j++) {
                        sumse += e[i][j];
                    }
                    se[i] = sumse;
                }
                boolean unconverged; // 用来判断收敛点的数量是否等于总数
                double sum = 0;
                for (int i = 0; i < paths.size(); i++) {
                    if(se[i] == convits || se[i] == 0){
                        sum++;
                    }
                }
                unconverged = (sum != paths.size()); // 如果收敛的点数量不等于总点数N,说明有点还未收敛,unconverged 为真
                // 收敛
                if ((!unconverged && (K > 0)) || (counter == maxits)) {
                    dn = true; // 如果条件成立，将 dn 设置为 true
                }
            }
        }

        // 经过上面的的循环，便确定好了哪些点可以作为簇中心点，找出簇1中心点
        // int[] I = new int[paths.size()];
        ArrayList<Integer> I = new ArrayList<>();
        int K = 0; // 用来记录I中存放多少数据，及多少个簇中心点
        int sum_k = 0; // 用来方便在I中按行记录簇中心点索引
        for (int i = 0; i < paths.size(); i++) {
            // 将R与A矩阵对角线元素相加，若为正数则说明该点为簇中心点，且i反应了簇中心点的行列（位置）
            if(R[i][i] + A[i][i] > 0){
                //I[sum_k] = i;
                I.add(i);
                sum_k++;
                K++;
            }
        }

        double[] tmpidx = new double[paths.size()]; // 用来存放最后每个点的聚类中心
        double tmpnetsim = 0; // 存放各个点到聚类中心的负欧式距离的和（衡量这次聚类的适合度）
        double tmpexpref = 0; // 存放所有被选为簇中心的点的适合度

        if(K > 0){
            // [~,c]=max(S(:,I),[],2);
            // 将S中对应I的所有列取出来，例如I中是3，4，则将S的第3和第4列的所有元素取出来
            // （判断除此簇中心点外，其他的点是否以此簇中心点为中心）
            // 对取出的这几列，按行查找最大值并记录这个最大值的列索引
            double[][] Sp = new double[paths.size()][paths.size()]; // 用来存S中对应I的列
            // 将Sp内部填充负无穷大，方便后续查找最大值的列索引
            for (int i = 0; i < paths.size(); i++) {
                for (int j = 0; j < paths.size(); j++) {
                    Sp[i][j] = Double.NEGATIVE_INFINITY;
                }
            }
            int[] c = new int[paths.size()]; // 存放列索引（存放的是每个点的簇中心点是哪个）
            // 取出S对应I中的所有列
            for (int k = 0; k < I.size(); k++) {
                int sum = 0;
                //sum = I[k];
                sum = I.get(k);
                for (int i = 0; i < paths.size(); i++) {
                    Sp[i][sum] = S[i][sum];
                }
            }
            // 按行查找最大值,并记录最大值列索引
            for(int i=0;i<paths.size();i++){
                double max = Double.NEGATIVE_INFINITY; // 定义成负无穷大方便查找最大值
                int maxIndex = -1; // 记录列索引
                for(int j=0;j<paths.size();j++){
                    if(Sp[i][j] > max){
                        max = Sp[i][j];
                        //maxIndex = j+1;
                        maxIndex = j;
                    }
                }
                c[i] = maxIndex;
            }
            // 将c数组内的值换成I中的序号(索引)
            for(int i=0;i<c.length;i++){
                int numc = c[i];
                for(int j=0;j<I.size();j++){
                    if(I.get(j) == numc){
                        c[i] = j;
                    }
                }
            }
            // 将 1:K 的值赋给向量 c 的索引为 I 的位置（c(I)=1:K;）
            for (int i = 0; i < I.size(); i++) {
                c[I.get(i)] = i;
            }

            // 细化聚类中心
            // 当k=2时，在c中找到个体1、2、4都以2为聚类中心，提取S中1、2、4行和1、2、4列组成的3*3矩阵
            // 分别算出3列之和取最大值，y记录最大值，j记录最大值所在的行
            for(int k = 0; k < K; k++){
                ArrayList<Integer> ii = new ArrayList<>();
                int sum=0;
                // 首先找到同一聚类中心的每个个体
                for (int m = 0; m < c.length; m++) {
                    if(c[m] == k){
                        ii.add(m);
                        sum++;
                    }
                }
                // 提取这些个体在S中的行列，生成子矩阵
                double[][] jj = new double[ii.size()][ii.size()];
                for(int m = 0; m < ii.size(); m++){
                    for(int n = 0; n < ii.size(); n++){
                        jj[m][n] = S[ii.get(m)][ii.get(n)];
                    }
                }
                // 并计算三列之和，记录最大值与最大值的列
                int maxCols = jj.length; // 由于不知道jj为多大，首先找到jj的大小
                int[] caltemp = new int[maxCols]; // 用来记录每列最大值，且最大值所在的列数为索引
                int[] yCols = new int[1]; // 记录最大值所在的列
                double y = Double.NEGATIVE_INFINITY; // 记录最大值
                for(int m=0;m<maxCols;m++){
                    for(int n=0;n<maxCols;n++){
                        caltemp[m] += jj[m][n]; // 将jj一列的值相加存入caltemp数组
                    }
                }
                for(int m=0;m<caltemp.length;m++){
                    if(caltemp[m] > y){
                        y = caltemp[m]; // 最大值
                        yCols[0] = m; // 最大值所在的列
                    }
                }
                I.set(k, ii.get(yCols[0])); // 将子矩阵中列和最大的列对应的点在原矩阵中的索引赋值给I
            }
            // [tmp,c]=max(S(:,I),[],2);
            // 在矩阵S的指定列I中，逐行寻找最大值，并返回这些最大值及其对应的列索引
            double[] tmp = new double[paths.size()];
            for (int row = 0; row < S.length; row++) {
                double maxVal = Double.NEGATIVE_INFINITY;
                int maxIndex = -1;
                // 遍历 I 中的列索引
                for (int colIndex = 0; colIndex < I.size(); colIndex++) {
                    int actualCol = I.get(colIndex); // 转换为实际列索引
                    if (S[row][actualCol] > maxVal) {
                        maxVal = S[row][actualCol];
                        maxIndex = colIndex; // 局部索引
                    }
                }
                // 保存最大值和对应列索引
                tmp[row] = maxVal;
                c[row] = maxIndex;
            }
            // c(I)=1:K;
            for (int i = 0; i < I.size(); i++) {
                c[I.get(i)] = i;
            }
            for(int i = 0; i < paths.size(); i++){
                tmpidx[i] = I.get(c[i]); // 每个点的聚类中心是谁
            }
            // 将各点到簇中心的欧式距离负值的和来衡量这次聚类的适合度
            for (int i = 0; i < tmpidx.length; i++) {
                //int row = (int)tmpidx[i] - 1; // 转换为 0-based 索引
                int row = (int)tmpidx[i];
                tmpnetsim += S[row][i]; // 累加第 row 行的第 i 列的元素
            }
            // 表示所有被选为簇中心的点的适合度之和
            for (int i = 0; i < I.size(); i++) {
                tmpexpref += dS[I.get(i)][I.get(i)];
            }
            //int qwe = 0; // 测试
        }
        else{
            Arrays.fill(tmpidx, Double.NaN); // 用 Double.NaN 填充数组
            tmpnetsim = Double.NaN;
            tmpexpref = Double.NaN;
        }

        double netsim = tmpnetsim; // 反应这次聚类的适合度
        double expref = tmpexpref;
        double dpsim = tmpnetsim - tmpexpref;
        double[] idx = new double[tmpidx.length];
        idx = Arrays.copyOf(tmpidx, tmpidx.length);
        // 返回数组 idx 中的唯一元素（去重后的元素），并按升序排列
        // 使用 HashSet 去重
        Set<Double> uniqueSet = new HashSet<>();
        for (double element : idx) {
            uniqueSet.add(element);
        }
        // 将去重后的元素转换为数组
        double[] ans = uniqueSet.stream().mapToDouble(Double::doubleValue).toArray();
        // 对数组进行排序（升序）
        Arrays.sort(ans);

        // 根据聚类结果，将数据分配到各个簇中，并提取每个簇的中心点信息
        ArrayList<ArrayList<Path>> cluster = new ArrayList<>(); // 用来储存最后的每个簇中的路径(包含所有属性)
        int tt = 0; // 计数器，记录当前是哪个簇

        // 开始遍历所有的簇（idx中是所有个体归属于哪个聚类中心，ans是整个种群选出来的聚类中心）
        for(int k = 0; k < ans.length; k++){
            ArrayList<Integer> ii = new ArrayList<>();
            // 按照ans的顺序找到当前簇的所有点存入ii
            for(int i = 0; i < idx.length; i++){
                if(idx[i] == ans[k]){
                    ii.add(i);
                }
            }
            // 将当前簇的簇中心点表示的路径存入center
            int aum = 0;
            aum = (int) ans[tt];
            paths.get(aum).center = 1; // 若该条路径是聚类中心点，则修改其center属性为1
            // 将当前簇的每个点所对应的路径存入cluster中
            ArrayList<Path> interRow = new ArrayList<>(); // 储存当前簇内部的路径(包含属性)，并确定该聚类中的聚类中心点路径
            for(int j = 0; j < ii.size(); j++){
                int num = ii.get(j);
                interRow.add(paths.get(num));
            }
            cluster.add(interRow);
            tt++;
        }

        //return result;//测试
        return cluster;
    }

    // 提取 Path 对象中的 path 属性，并存储到二维数组中，其中path应该与最长的个体等长，不足补0
    private static int[][] extractPaths(List<Path> paths,int maxLength) {
        // 创建二维数组，每行长度为 maxLength
        int[][] clustering = new int[paths.size()][maxLength];
        // 填充二维数组，并补齐不足部分
        for (int i = 0; i < paths.size(); i++) {
            List<Integer> path = paths.get(i).path; // 获取当前 Path 的路径
            for (int j = 0; j < maxLength; j++) {
                if (j < path.size()) {
                    clustering[i][j] = path.get(j); // 使用路径中的值
                } else {
                    clustering[i][j] = 0; // 补齐部分填 0
                }
            }
        }
        return clustering;
    }

    // 将聚类拆开形成原来的Path对象
    public static List<Path> Discluster(ArrayList<ArrayList<Path>> APcluster){
        List<Path> paths = new ArrayList<>();
        int n = 0;
        for (int i = 0; i < APcluster.size(); i++) {
            for (int j = 0; j < APcluster.get(i).size(); j++) {
                paths.add(APcluster.get(i).get(j));
            }
        }
        return paths;
    }

    // 找到种群中路径的最长长度
    private static int FindLength(List<Path> paths) {
        int maxLength = 0;
        for (Path path : paths) {
            maxLength = Math.max(maxLength, path.path.size());
        }
        return maxLength;
    }

    // 创建初始化s数组
    private static double[][] InitialArray(int[][] clustering, List<Path> paths, int maxLength) {
        int N = paths.size();
        int M = N * (N - 1); // 排除对角线
        double[][] s = new double[M][3]; // 创建一个 M 行 3 列的矩阵，用于存放个体之间的相似度
        int j = 0;

        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                if(i == k) continue; // 跳过自己
                double sum = 0; // 每对 (i, k) 重新计算 sum
                for (int l = 0; l < maxLength; l++) {
                    double first = clustering[i][l];
                    double second = clustering[k][l];
                    if(first == 0 && second == 0) {continue;} // 若x、y都为0，则没必要进行操作直接跳出
                    // 将路径点拆分为x、y坐标方式进行之后的欧式距离计算
                    double first_x = (double) first / 1000;
                    double first_y = (double) first % 1000;
                    double second_x = (double) second / 1000;
                    double second_y = (double) second % 1000;
                    // 计算欧式距离
                    double aum = Math.sqrt(Math.pow(first_x - second_x, 2) + Math.pow(first_y - second_y, 2));
                    sum += aum * aum; // 欧式距离平方累加
                }
                s[j][0] = i+1;
                s[j][1] = k+1;
                s[j][2] = -sum;// 负欧式距离
                j++;
            }
        }
        return s;
    }

    // 查找中位数
    private static double FindMData(double[][] s) {
        int rows = s.length; // 获取二维数组的行数
        double[] similarity = new double[rows];
        for(int i = 0; i < rows; i++) {
            similarity[i] = s[i][2];
        }
        // 对提取的列数据进行排序
        Arrays.sort(similarity);
        // 计算中位数
        if (rows % 2 == 1) {
            // 奇数：返回中间值
            return similarity[rows / 2];
        } else {
            // 偶数：返回中间两个值的平均
            int mid = rows / 2;
            return (similarity[mid - 1] + similarity[mid]) / 2.0;
        }
    }

    //同规格矩阵相加减  num=1--相加 num=2--相减
    private static double[][] calMetrices(double[][] metrix1, double[][] metrix2,int num) {
        // 创建存储结果的矩阵
        int rows = metrix1.length;
        int cols = metrix1[0].length;
        double[][] result = new double[rows][cols];
        if(num == 1) {
            // 遍历每个元素进行相加
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    result[i][j] = metrix1[i][j] + metrix2[i][j];
                }
            }
        }
        else if(num == 2) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    result[i][j] = metrix1[i][j] - metrix2[i][j];
                }
            }
        }
        return result;
    }

    //加入阻尼系数
    private static double[][] addLam(double[][] metrix1,double[][] matrix2,double lam) {
        int rows = metrix1.length;
        int cols = metrix1[0].length;
        double[][] result = new double[rows][cols]; // 创建存储结果的矩阵
        double[][] temp1 = new double[rows][cols]; // 新矩阵（更新后的矩阵）
        double[][] temp2 = new double[rows][cols]; // 旧矩阵（更新前的矩阵）
        for (int i = 0; i < metrix1.length; i++) { // 遍历行
            for (int j = 0; j < metrix1[i].length; j++) { // 遍历列
                temp1[i][j] = metrix1[i][j] * (1-lam); // 新矩阵元素乘以系数
            }
        }
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                temp2[i][j] = matrix2[i][j] * lam; // 旧矩阵元素乘以系数
            }
        }
        result = calMetrices(temp1,temp2,1); // 将乘以系数后的两个新矩阵相加生成最终结果矩阵
        return result;
    }

}
