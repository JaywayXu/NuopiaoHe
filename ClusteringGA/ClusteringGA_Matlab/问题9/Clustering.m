function [P,N]=Clustering(pop)
L=cat(1,pop.l);
len=length(L);
m=max(L);
C=zeros(len,m+5);
C1=zeros(len,1);
for i=1:len
    C(i,1:pop(i).l)=pop(i).c;
    C(i,m+1:m+5)=[pop(i).fitness1,pop(i).fitness2,pop(i).fitness3,pop(i).fitness4,pop(i).fitness5];
    C1(i,1)=pop(i).l;
end
D=C(:,1:m);
[cluster,center]=APClustering(D); 
P=cell(1,size(cluster,1));
for i=1:size(cluster,1)
    num_cluster=size(cluster{i,1},1);
    S=[];
    for j=1:num_cluster
        row=find((ismember(D,cluster{i,1}(j,:),'rows')==1));
        nn=sum(sum(D(row,:)~=0));
        S(j).c=D(row,1:nn);
        S(j).l=C1(row,1);
        S(j).fitness1=C(row,m+1);
        S(j).fitness2=C(row,m+2);
        S(j).fitness3=C(row,m+3);
        S(j).fitness4=C(row,m+4);
        S(j).fitness5=C(row,m+5);
    end
    P{1,i}=S;
    N(1,i)=num_cluster;
end
end