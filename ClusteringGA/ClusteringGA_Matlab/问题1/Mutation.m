%变异方式：在基因序列上随机初始化一个位置（不包括头尾节点，以及倒数第二个节点，
%%因为初始化方式的原因,如果终点在倒数第二个节点的联通表里，直接选择终点）
%找到该节点后续可以到达的路口集合，找到不同于当前并可行的路口加入当序列当中，
%%并使用初始化的方式完善
function [chrom1,length1] = Mutation(chrom,length,Relation,goal)
randnum = unidrnd(length-3)+1;%去头尾以及倒数第二个节点
chrom1 = chrom(1:randnum);
length1 = randnum;
candidate = Relation(:,3:end);
n = abs(chrom(randnum)) - 1;
next = chrom(randnum+1);
m = find(candidate(n,:) == next);
candidate(n,m) = 0;
while(chrom1(length1)~=goal)
    index = abs(chrom1(length1)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
    cannum = sum(candidate(index,:)~=0);%路径中当前点没有走过的候选路口数
    if(cannum == 0)%当当前点的候选路口都已经使用过时
        chrom1(end) = [];
        length1 = length1 - 1;%将当前点从路径中移除
    else
        if(ismember(goal,candidate(index,:)))
            chrom1 = [chrom1,goal];
            length1 = length1 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
            canindex = find(candidate(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
            can = candidate(index,canindex);%找到列号对应的路口号
            new = can(randnum);%new记录选出来的点序号
            if(ismember(new,chrom1))
                candidate(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
            else
                chrom1 = [chrom1,new];
                length1 = length1 +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end