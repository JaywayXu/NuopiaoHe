%为InterSection中所有路口和拐角编号
%InterNum为拐点数和路口数总和*3的矩阵，第一列为编号，第二、三列分别为在Map中对应的横、纵坐标
%Map2将Map中，拐角和路口所在点变成相应的编号，方便后续联系和计算适应度
function [InterNum,Map2] = InterNumRange(InterSection,Map)
k=2;
num = sum(sum(InterSection~=0));
InterNum = zeros(num,3);
Map2 = Map;
for i=1:1:size(InterSection,1)
    for j=1:1:size(InterSection,1)
        if(InterSection(i,j)~=0)
            InterNum(k,1)=k*InterSection(i,j);
            Map2(i,j)=k*InterSection(i,j);
            InterNum(k,2)=j;
            InterNum(k,3)=i;
            k=k+1;
        end
    end
end
InterNum(1,:)=[];