function [chrom,length] = Initialization1(Relation,start,yellow,goal)
candidate = Relation(:,3:end); %candidate保存Relation中可选联系点部分
candidate(find(candidate==goal))=0;
yellow_num=size(yellow,1);
rand1=unidrnd(yellow_num);
chrom = start;%先将起始点放入路径中
length = 1;
while(chrom(length)~=yellow(rand1))
    index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
    cannum = sum(candidate(index,:)~=0);%路径中当前点没有走过的候选路口数
    if(cannum == 0)%若当前点的候选路口都已经使用过时
        chrom(end) = [];
        length = length - 1;%将当前点从路径中移除
    else
        if(ismember(yellow(rand1),candidate(index,:))) %ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
            chrom = [chrom,yellow(rand1)];
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
yellow(rand1)=[];
% yellow_num=yellow_num-1;
% if yellow_num~=0
if(ismember(yellow,chrom));
    candidate3=Relation(:,3:end);
    while(chrom(length)~=goal)
        index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
        cannum = sum(candidate3(index,:)~=0);%路径中当前点没有走过的候选路口数
        if(cannum == 0)%若当前点的候选路口都已经使用过时
            chrom(end) = [];
            length = length - 1;%将当前点从路径中移除
        else
            if(ismember(goal,candidate3(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
                chrom = [chrom,goal];
                length = length + 1;
            else
                randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
                canindex = find(candidate3(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
                can = candidate3(index,canindex);%找到列号对应的路口号
                new = can(randnum);%new记录选出来的点序号
                if(ismember(new,chrom))
                    candidate3(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
                else
                    chrom = [chrom,new];
                    length = length +1 ;
                    candidate3(index,canindex(randnum)) = 0;
                end
            end
        end
    end
else
    while(chrom(length)~=yellow)
        index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
        cannum = sum(candidate(index,:)~=0);%路径中当前点没有走过的候选路口数
        if(cannum == 0)%若当前点的候选路口都已经使用过时
            chrom(end) = [];
            length = length - 1;%将当前点从路径中移除
        else
            if(ismember(yellow,candidate(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
                chrom = [chrom,yellow];
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
    candidate2=Relation(:,3:end);
    while(chrom(length)~=goal)
        index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
        cannum = sum(candidate2(index,:)~=0);%路径中当前点没有走过的候选路口数
        if(cannum == 0)%若当前点的候选路口都已经使用过时
            chrom(end) = [];
            length = length - 1;%将当前点从路径中移除
        else
            if(ismember(goal,candidate2(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
                chrom = [chrom,goal];
                length = length + 1;
            else
                randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
                canindex = find(candidate2(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
                can = candidate2(index,canindex);%找到列号对应的路口号
                new = can(randnum);%new记录选出来的点序号
                if(ismember(new,chrom))
                    candidate2(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
                else
                    chrom = [chrom,new];
                    length = length +1 ;
                    candidate2(index,canindex(randnum)) = 0;
                end
            end
        end
    end
end
% else
%     candidate1=Relation(:,3:end);
%     while(chrom(length)~=goal)
%         index = abs(chrom(length)) - 1;%找到该点在矩阵中对应的行，从而方便找到联系路口
%         cannum = sum(candidate1(index,:)~=0);%路径中当前点没有走过的候选路口数
%         if(cannum == 0)%若当前点的候选路口都已经使用过时
%             chrom(end) = [];
%             length = length - 1;%将当前点从路径中移除
%         else
%             if(ismember(goal,candidate1(index,:))) % ismember(a,b)是看矩阵a中的数是不是矩阵b中的成员
%                 chrom = [chrom,goal];
%                 length = length + 1;
%             else
%                 randnum = round(rand(1)*(cannum-1))+1;%随机从候选路口中选择一个
%                 canindex = find(candidate1(index,:)~=0);%找出没被挑选过的路口（不为0）的列号
%                 can = candidate1(index,canindex);%找到列号对应的路口号
%                 new = can(randnum);%new记录选出来的点序号
%                 if(ismember(new,chrom))
%                     candidate1(index,canindex(randnum)) = 0;%如果挑选出的点已经存在，则将该点置0，不做其他操作
%                 else
%                     chrom = [chrom,new];
%                     length = length +1 ;
%                     candidate1(index,canindex(randnum)) = 0;
%                 end
%             end
%         end
%     end
% end
    
    
    
