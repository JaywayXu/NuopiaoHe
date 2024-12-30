%变异方式：在基因序列上第一个点到黄色点之间初始化一个位置
%找到该节点后续可以到达的路口集合，找到不同于当前并可行的路口加入当序列当中，并使用初始化的方式完善
function [chrom2,length2] = Mutation1(chrom,Relation,yellow,goal)
yellow_num=size(yellow,1);
if yellow_num==1
    y=find(chrom==yellow);
elseif yellow_num==2
    a=find(chrom==yellow(1));
    b=find(chrom==yellow(2));
    if a>b
        y=b;
    else
        y=a;
    end
end
chrom1 = chrom(1:y);%第一个点到黄色点的路径
length1 = y;%第一个点到黄色点的路径长度
randnum = unidrnd(length1-3)+1;%去头尾以及倒数第二个节点
chrom2 = chrom1(1:randnum);
length2 = randnum;
candidate = Relation(:,3:end);
candidate(find(candidate==goal))=0;
n = abs(chrom1(randnum)) - 1;
next = chrom1(randnum+1);
m = find(candidate(n,:) == next);
candidate(n,m) = 0;
while(chrom2(length2)~=chrom1(y))
    index = abs(chrom2(length2)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
    cannum = sum(candidate(index,:)~=0);%路径中当前点没有走过的候选路口数
    if(cannum == 0)%当当前点的候选路口都已经使用过时
        chrom2(end) = [];
        length2 = length2 - 1;%将当前点从路径中移除
    else
        if(ismember(chrom1(y),candidate(index,:)))
            chrom2 = [chrom2,chrom1(y)];
            length2 = length2 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
            canindex = find(candidate(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
            can = candidate(index,canindex);%找到列号对应的路口号
            new = can(randnum);%new记录选出来的点序号
            if(ismember(new,chrom2))
                candidate(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
            else
                chrom2 = [chrom2,new];
                length2 = length2 +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end
candidate1=Relation(:,3:end);
while(chrom2(length2)~=goal)
    index = abs(chrom2(length2)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
    cannum = sum(candidate1(index,:)~=0);%路径中当前点没有走过的候选路口数
    if(cannum == 0)%若当前点的候选路口都已经使用过时
        chrom2(end) = [];
        length2 = length2 - 1;%将当前点从路径中移除
    else
        if(ismember(goal,candidate1(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
            chrom2 = [chrom2,goal];
            length2 = length2 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
            canindex = find(candidate1(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
            can = candidate1(index,canindex);%找到列号对应的路口号
            new = can(randnum);%new记录选出来的点序号
            if(ismember(new,chrom2))
                candidate1(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
            else
                chrom2 = [chrom2,new];
                length2 = length2 +1 ;
                candidate1(index,canindex(randnum)) = 0;
            end
        end
    end
end




