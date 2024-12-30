function Pareto=sort_fitness(Pareto)
fit1=cat(1,Pareto.fitness1);
fit2=cat(1,Pareto.fitness2);
fit3=cat(1,Pareto.fitness3);
fit4=cat(1,Pareto.fitness4);
Fit=[fit1,fit2,fit3,fit4];
len=size(Fit,1);
P=[];
num=1;
for i=1:len
    if isequal(Fit(i,:),zeros(1,4))==1
        continue;
    else
        X=Fit(i,:);
        P(num).c=Pareto(i).c;
        P(num).l=Pareto(i).l;
        P(num).fitness1=Pareto(i).fitness1;
        P(num).fitness2=Pareto(i).fitness2;
        P(num).fitness3=Pareto(i).fitness3;
        P(num).fitness4=Pareto(i).fitness4;
        num=num+1;
        Fit(i,1:4)=zeros(1,4);
        for j=i+1:len
            if isequal(Fit(j,1:4),X)==1
                P(num).c=Pareto(j).c;
                P(num).l=Pareto(j).l;
                P(num).fitness1=Pareto(j).fitness1;
                P(num).fitness2=Pareto(j).fitness2;
                P(num).fitness3=Pareto(j).fitness3;
                P(num).fitness4=Pareto(j).fitness4;
                num=num+1;
                Fit(j,1:4)=zeros(1,4);
            else
                continue;
            end
        end
    end
end
Pareto=P;
end