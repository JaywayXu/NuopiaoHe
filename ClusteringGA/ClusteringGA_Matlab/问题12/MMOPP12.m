clear 
clc
warning('off')
tic
%% 加载问题
load 'Problem_12.mat'
%% 找出路口并对路口进行编码
%InterSection_Map大小等同于Map的矩阵，矩阵中路口为1，拐角为-1，其他位置为0
InterSection_Map = FindIntersection(Map);

%InterNum大小为（拐角数+路口数）*3的矩阵，矩阵第一列存放编码，二三列存放横纵坐标
%Map2大小等同于Map矩阵，仅将Map中路口、拐角上的0换为相应的编码号
[InterNum,Map2] = InterNumRange(InterSection_Map,Map);

%start为起点的编码号，goal为终点的编码号
start = Map2(START_y,START_x);
goal = Map2(GOAL_y,GOAL_x);
yellow_num=size(Yellow_areas,1);
for i=1:yellow_num
    yellow(i,:)=Map2(Yellow_areas(i,2),Yellow_areas(i,1));
end
%Relation大小为（拐角数+路口数）*6的矩阵，第一列存放编码，第二列存放关联路口数,后四列存放关联路口的编码号
Relation = InterLink(InterNum,Map2);

%Fit_List1存放两个路口间的路径长度（不包括两点），例Fit_List1[abs(n),abs(m)]=5,表示路口n到路口m之间的路径长度为5
%Fit_List2存放两个路口间的红点个数（不包括两点），例Fit_List2[abs(n),abs(m)]=2,表示路口n到路口m之间的红点个数为2
%Fit_List3暂时为空，考虑后面其他目标存放
[Fit_List1,Fit_List2,Fit_List3] = Fit_calculate1(InterNum,Relation);
%RedInter是路口且是红色区域的路口编码号
% RedInter = FindredInter(InterNum,Red_areas);
%% 参数设置
PN = 100;%种群数量
Gen_Max = 100;
Max_FES=Gen_Max*PN;
M = 3;%目标数
%% 种群初始化、适应度计算
i=1;
while i<=PN
    [chrom,len] = Initialization1(Relation,start,yellow,goal);
    if (ismember(yellow(1),chrom)&&ismember(yellow(2),chrom))
        [f1,f2,f3] = Fitness3(len,chrom,F,Fit_List1,InterNum);
        POP(i).c = chrom;
        POP(i).l = len;
        POP(i).fitness1 = f1;
        POP(i).fitness2 = f2;
        POP(i).fitness3 = f3;
        i=i+1; 
    end
end

%%清除初始化重复路径
POP=clearing(POP);
while (length(POP)<PN)
    n=length(POP);
    %%初始化新个体
    for i = n+1:1:PN
        [chrom,len] = Initialization1(Relation,start,yellow,goal);
        if (ismember(yellow(1),chrom)&&ismember(yellow(2),chrom))
            [f1,f2,f3] = Fitness3(len,chrom,F,Fit_List1,InterNum);
            POP(i).c = chrom;
            POP(i).l = len;
            POP(i).fitness1 = f1;
            POP(i).fitness2 = f2;
            POP(i).fitness3 = f3;
            i=i+1; 
        end
    end
    POP=clearing(POP);
end


%% 优化
Pareto=[];
for gen=1:Gen_Max
    [P,Num]=Clustering(POP);
    for i=1:size(P,2)
%         ss=length(cat(1,P{1,i}.l));
        ss=Num(i);
        if ss==1
            [child(1).c,child(1).l,~,~] = Crossover1(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(1).c,P{1,i}(1).l,yellow);
        elseif ss==2
            [child(1).c,child(1).l,~,~] = Crossover1(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(2).c,P{1,i}(2).l,yellow);
            [child(2).c,child(2).l,~,~] = Crossover1(P{1,i}(2).c,P{1,i}(2).l,P{1,i}(1).c,P{1,i}(1).l,yellow);
        else
            for j=1:ss
                if j==1
                    [child(j).c,child(j).l,~,~] = Crossover1(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(2).c,P{1,i}(2).l,yellow);
                elseif j==ss
                    [child(j).c,child(j).l,~,~] = Crossover1(P{1,i}(ss).c,P{1,i}(ss).l,P{1,i}(1).c,P{1,i}(1).l,yellow);
                else
                    [child(j).c,child(j).l,~,~] = Crossover1(P{1,i}(j).c,P{1,i}(j).l,P{1,i}(j+1).c,P{1,i}(j+1).l,yellow);
                end
            end
        end
        for j=1:ss
            if (~ismember(yellow(1),child(j).c)||~ismember(yellow(2),child(j).c))
                child(j).c=P{1,i}(j).c;
                child(j).l=P{1,i}(j).l;
                child(j).fitness1=P{1,i}(j).fitness1;
                child(j).fitness2=P{1,i}(j).fitness2;
                child(j).fitness3=P{1,i}(j).fitness3;
            else
                [f1,f2,f3] = Fitness3(child(j).l,child(j).c,F,Fit_List1,InterNum);
                child(j).fitness1 = f1;
                child(j).fitness2 = f2;
                child(j).fitness3 = f3; 
            end 
            [child(j).c,child(j).l] = Mutation1(child(j).c,Relation,yellow,goal);
        end 
        
        clear child;
    end
    POP=clearing(POP);
    POP=NDsort_SCD(POP);
    POP=POP(1:PN);
    
    
    %%初始化新个体
    while (length(POP)<PN)
        n=length(POP);
        %%初始化新个体
%         for i = n+1:1:PN
%             yy=1;
%             [chrom,len] = Initialization1(Relation,start,yellow,goal);
%             while(~ismember(yellow(1),chrom)||~ismember(yellow(2),chrom))
%                 [chrom,len] = Initialization1(Relation,start,yellow,goal);
%                 if (ismember(yellow(1),chrom)&&ismember(yellow(2),chrom))
%                     [f1,f2,f3] = Fitness3(len,chrom,F,Fit_List1,InterNum);
%                     child(i).c = chrom;
%                     child(i).l = len;
%                     child(i).fitness1 = f1;
%                     child(i).fitness2 = f2;
%                     child(i).fitness3 = f3;
%                     yy=0;
%                 end
%             end 
%             if yy==1
%                 [f1,f2,f3] = Fitness3(len,chrom,F,Fit_List1,InterNum);
%                 child(i).c = chrom;
%                 child(i).l = len;
%                 child(i).fitness1 = f1;
%                 child(i).fitness2 = f2;
%                 child(i).fitness3 = f3;               
%             end
%         end
        
        
        
        for i = n+1:1:PN
            [chrom,len] = Initialization1(Relation,start,yellow,goal);
            if (ismember(yellow(1),chrom)&&ismember(yellow(2),chrom))
                [f1,f2,f3] = Fitness3(len,chrom,F,Fit_List1,InterNum);
                POP(i).c = chrom;
                POP(i).l = len;
                POP(i).fitness1 = f1;
                POP(i).fitness2 = f2;
                POP(i).fitness3 = f3;
                i=i+1; 
            end
        end
        POP=clearing(POP);
    end
end

Pobjs=[POP.fitness1;POP.fitness2;POP.fitness3]';
[FrontNo,MaxFNo]=NDSort(Pobjs,size(POP,2));
Pareto=POP(find(FrontNo==1));
Pareto=clearing(Pareto);
save('Pareto11.mat','Pareto')
toc


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%展示结果%%%%%%%%%%%%%%%%%%%%%%
len1=length(Pareto)
%%获得每一天路径在地图中的坐标
Path_coordinate=Position(Pareto,InterNum);
save('Problem12_Path_coordinate.mat','Path_coordinate');

%%展示获得路径图片
len=length(Pareto)
for i=1:len
    figure(i)
    ShowPath(Map,Pareto(i).c,Pareto(i).l,InterNum,START_x,START_y,GOAL_x,GOAL_y)
    pause(3);
    % showIntersection(Map,InterSection_Map)
end
pause(10);
for i=len:-1:1
    close(figure(i));
    pause(3);
end

%%对目标值排序方便观察是否为等效
Pareto=sort_fitness(Pareto);









    


