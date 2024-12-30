function Pareto=clearing(Pareto)
L=cat(1,Pareto.l);
len=length(L);
m=max(L);
C=zeros(len,m+6);
for i=1:len
    C(i,1:Pareto(i).l)=Pareto(i).c;
    C(i,m+1:m+6)=[Pareto(i).l,Pareto(i).fitness1,Pareto(i).fitness2,Pareto(i).fitness3,Pareto(i).fitness4,Pareto(i).fitness5];
end
C=unique(C,'rows');
for i=1:size(C,1)
    n=C(i,m+1);
    P(i).c=C(i,1:n);
    P(i).l=C(i,m+1);
    P(i).fitness1=C(i,m+2);
    P(i).fitness2=C(i,m+3);
    P(i).fitness3=C(i,m+4);
    P(i).fitness4=C(i,m+5);
    P(i).fitness5=C(i,m+6);
end
Pareto=P;