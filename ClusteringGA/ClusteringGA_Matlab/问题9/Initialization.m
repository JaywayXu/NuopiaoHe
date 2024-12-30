function [chrom,length] = Initialization(Relation,start,goal)
candidate = Relation(:,3:end); %candidate保存Relation中可选联系点部分
chrom = start;%先将起始点放入路径中
length = 1;
while(chrom(length)~=goal)
    index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
    cannum = sum(candidate(index,:)~=0);%路径中当前点没有走过的候选路口数
    if(cannum == 0)%当点前点的候选路口都已经使用过时
        chrom(end) = [];
        length = length - 1;%将当前点从路径中移除
    else
        if(ismember(goal,candidate(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
            chrom = [chrom,goal];
            length = length + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
            canindex = find(candidate(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
            can = candidate(index,canindex);%找到列号对应的路口号
            new = can(randnum);%new记录选出来的点序号
            if(ismember(new,chrom))
                candidate(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
            else
                chrom = [chrom,new];
                length = length +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end