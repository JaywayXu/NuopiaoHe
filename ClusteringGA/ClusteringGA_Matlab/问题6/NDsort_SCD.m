function P=NDsort_SCD(P)
L=cat(1,P.l);
len=length(L);
m=max(L);
C=zeros(len,m+2);
for i=1:len
    C(i,1:P(i).l)=P(i).c;
    C(i,m+1:m+2)=[P(i).fitness1,P(i).fitness2];
end
n_obj=2;
n_var=m;
X = non_domination_scd_sort(C, n_obj, n_var);
for i=1:size(C,1)
    row=find((ismember(C,X(i,1:m+2),'rows')==1));
    n=sum(sum(C(row,1:n_var)~=0));
    P(i).c=C(row,1:n);
    P(i).l=n;
    P(i).fitness1=C(row,m+1);
    P(i).fitness2=C(row,m+2);
end
end