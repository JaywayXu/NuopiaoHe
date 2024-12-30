%交叉函数，首先找到除了首位之外的相同元素，可能不唯一。因为开始点有两个联系点 
function [cchrom1,clength1,cchrom2,clength2] = Crossover1(pchrom1,plength1,pchrom2,plength2,yellow)
yellow_num = size(yellow,1);
if yellow_num==1
a=find(pchrom1==yellow(1));
b=find(pchrom2==yellow(1));
elseif yellow_num==2
    a1=find(pchrom1==yellow(1));
    a2=find(pchrom1==yellow(2));
    if a1>a2
        a=a2;
    else
        a=a1;
    end
    b1=find(pchrom2==yellow(1));
    b2=find(pchrom2==yellow(2));
    if b1>b2
        b=b2;
    else
        b=b1;
    end
end
[samecode,ia,ib] = intersect(pchrom1(2:a-1),pchrom2(2:b-1));%从第二个到黄色点前一个之间找存在的相同点
%intersect函数：samecode返回ia,ib的交集，ia，ib返回的是交集所在数组的指标
if isempty(samecode) %判断samecode是否为空
    cchrom1 = pchrom1;
    clength1 = plength1;
    cchrom2 = pchrom2;
    clength2 = plength2;
else
    randnum = unidrnd(size(samecode,2));  %产生一组离散均匀随机整数
    cchrom1 = pchrom1(1:ia(randnum));
    cchrom1 = [cchrom1,pchrom2(ib(randnum)+1:end)];
    cchrom2 = pchrom2(1:ib(randnum));
    cchrom2 = [cchrom2,pchrom1(ia(randnum)+1:end)];
    clength1 = size(cchrom1,2);
    clength2 = size(cchrom2,2);
end
%子代产生之后要判断子代中是否存在相同的路口
temp1 = unique(cchrom1);%unique：删除矩阵中重复的元素
temp2 = unique(cchrom2);
if size(temp1,2) < clength1
    [cchrom1,clength1] = Adjustment(cchrom1,clength1);
end
if size(temp2,2) < clength2
    [cchrom2,clength2] = Adjustment(cchrom2,clength2);
end
