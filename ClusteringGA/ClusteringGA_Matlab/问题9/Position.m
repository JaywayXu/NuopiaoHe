function X=Position(Pareto,InterNum)
L=cat(1,Pareto.l);
len=length(L);%·������
Index_road=InterNum(:,1);%·�ڱ��
X=cell(1,len);
for i=1:len
    a=Pareto(i).c;%��i��·��
    for j=1:length(a)
        [m,~]=find(Index_road==a(j));%�ҳ���i��·���е�j��λ����Index_road��λ��
        X{1,i}(j,1:2)=InterNum(m,2:3);
    end
end
end