function X=Position(Pareto,InterNum)
L=cat(1,Pareto.l);
len=length(L);%路径数量
Index_road=InterNum(:,1);%路口编号
X=cell(1,len);
for i=1:len
    a=Pareto(i).c;%第i条路径
    for j=1:length(a)
        [m,~]=find(Index_road==a(j));%找出第i条路径中第j个位置在Index_road的位置
        X{1,i}(j,1:2)=InterNum(m,2:3);
    end
end
end