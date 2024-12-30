clear all;
clc
warning('off')
tic
%% ��������
load 'Problem_4.mat'
%% �ҳ�·�ڲ���·�ڽ��б���
%InterSection_Map��С��ͬ��Map�ľ��󣬾�����·��Ϊ1���ս�Ϊ-1������λ��Ϊ0
InterSection_Map = FindIntersection(Map);

%InterNum��СΪ���ս���+·������*3�ľ��󣬾����һ�д�ű��룬�����д�ź�������
%Map2��С��ͬ��Map���󣬽���Map��·�ڡ��ս��ϵ�0��Ϊ��Ӧ�ı����
[InterNum,Map2] = InterNumRange(InterSection_Map,Map);

%startΪ���ı���ţ�goalΪ�յ�ı����
start = Map2(START_y,START_x);
goal = Map2(GOAL_y,GOAL_x);

%Relation��СΪ���ս���+·������*6�ľ��󣬵�һ�д�ű��룬�ڶ��д�Ź���·����,�����д�Ź���·�ڵı����
Relation = InterLink(InterNum,Map2);

%Fit_List1�������·�ڼ��·�����ȣ����������㣩����Fit_List1[abs(n),abs(m)]=5,��ʾ·��n��·��m֮���·������Ϊ5
%Fit_List2�������·�ڼ�ĺ����������������㣩����Fit_List2[abs(n),abs(m)]=2,��ʾ·��n��·��m֮��ĺ�����Ϊ2
%Fit_List3��ʱΪ�գ����Ǻ�������Ŀ����
[Fit_List1,Fit_List2,Fit_List3] = Fit_calculate(Red_areas,InterNum,Relation);
%RedInter��·�����Ǻ�ɫ�����·�ڱ����
RedInter = FindredInter(InterNum,Red_areas);
%% ��������
PN = 100;%��Ⱥ����
Gen_Max = 100;
Max_FES=Gen_Max*PN;
M = 3;%Ŀ����
%% ��Ⱥ��ʼ������Ӧ�ȼ���
for i = 1:1:PN
    [chrom,len] = Initialization(Relation,start,goal);
    [f1,f2,f3] = Fitness1(len,chrom,RedInter,Fit_List1,Fit_List2);
    POP(i).c = chrom;
    POP(i).l = len;
    POP(i).fitness1 = f1;
    POP(i).fitness2 = f2;
    POP(i).fitness3 = f3;
end

%%�����ʼ���ظ�·��
POP=clearing(POP);
while (length(POP)<PN)
    n=length(POP);
    %%��ʼ���¸���
    for i = n+1:1:PN
        [chrom,len] = Initialization(Relation,start,goal);
        %%%%%%%%%%%%%%%����1%%%%%%%%%%%%%%%%%%%%%%%
        [f1,f2,f3] = Fitness1(len,chrom,RedInter,Fit_List1,Fit_List2);
        POP(i).c = chrom;
        POP(i).l = len;
        POP(i).fitness1 = f1;
        POP(i).fitness2 = f2;
        POP(i).fitness3 = f3;
    end
    POP=clearing(POP);
end
%% �Ż�
Pareto_no_ns=[];
for gen=1:Gen_Max
    [P,Num]=Clustering(POP);
    for i=1:size(P,2)
        ss=Num(i);
        if ss==1
            [child(1).c,child(1).l,~,~] = Crossover(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(1).c,P{1,i}(1).l);
        elseif ss==2
            [child(1).c,child(1).l,~,~] = Crossover(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(2).c,P{1,i}(2).l);
            [child(2).c,child(2).l,~,~] = Crossover(P{1,i}(2).c,P{1,i}(2).l,P{1,i}(1).c,P{1,i}(1).l);
        else
            for j=1:ss
                if j==1
                    [child(j).c,child(j).l,~,~] = Crossover(P{1,i}(1).c,P{1,i}(1).l,P{1,i}(2).c,P{1,i}(2).l);
                elseif j==ss
                    [child(j).c,child(j).l,~,~] = Crossover(P{1,i}(ss).c,P{1,i}(ss).l,P{1,i}(1).c,P{1,i}(1).l);
                else
                    [child(j).c,child(j).l,~,~] = Crossover(P{1,i}(j).c,P{1,i}(j).l,P{1,i}(j+1).c,P{1,i}(j+1).l);
                end
            end
        end
        for j=1:ss
            [child(j).c,child(j).l] = Mutation(child(j).c,child(j).l,Relation,goal);
            [f1,f2,f3] = Fitness1(child(j).l,child(j).c,RedInter,Fit_List1,Fit_List2);
            child(j).fitness1 = f1;
            child(j).fitness2 = f2;
            child(j).fitness3 = f3;
            POP=[POP,child(j)];
        end 
        clear child;
    end
    POP=clearing(POP);
    POP=NDsort_SCD(POP);
    POP=POP(1:PN);

    
    %%���³�ʼ���¸���
    while (length(POP)<PN)
        n=length(POP);
        %%��ʼ���¸���
        for i = n+1:1:PN
            [chrom,len] = Initialization(Relation,start,goal);
            %%%%%%%%%%%%%%%����1%%%%%%%%%%%%%%%%%%%%%%%
            [f1,f2,f3] = Fitness1(len,chrom,RedInter,Fit_List1,Fit_List2);
            POP(i).c = chrom;
            POP(i).l = len;
            POP(i).fitness1 = f1;
            POP(i).fitness2 = f2;
            POP(i).fitness3 = f3;
        end
        POP=clearing(POP);
    end
    
end



Pobjs=[POP.fitness1;POP.fitness2;POP.fitness3]';
[FrontNo,MaxFNo]=NDSort(Pobjs,size(POP,2));
Pareto=POP(find(FrontNo==1));
Pareto=clearing(Pareto);
save('Pareto4.mat','Pareto')
toc


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%չʾ���%%%%%%%%%%%%%%%%%%%%%%
len1=length(Pareto)
%%���ÿһ��·���ڵ�ͼ�е�����
Path_coordinate=Position(Pareto,InterNum);
save('Problem4_Path_coordinate.mat','Path_coordinate');

%%չʾ���·��ͼƬ
len=length(Pareto)
for i=1:len
    figure(i)
    ShowPath(Map,Pareto(i).c,Pareto(i).l,InterNum,START_x,START_y,GOAL_x,GOAL_y)
    pause(1);
    % showIntersection(Map,InterSection_Map)
end
pause(5);
for i=len:-1:1
    close(figure(i));
    pause(1);
end

%%��Ŀ��ֵ���򷽱�۲��Ƿ�Ϊ��Ч
Pareto=sort_fitness(Pareto);



    


