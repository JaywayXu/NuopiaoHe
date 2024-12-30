%% 优化开始
Population = InitPop(PopSize);
gen=1;
while gen<MaxGen
    Offspring  = GA(Population);
    [Population,FrontNo] = EnvironmentalSelection([Population,Offspring],PopSize);
    gen = gen + 1;
    if ~mod(gen,10)
       disp(['迭代次数:' num2str(gen) ' 解个数:' num2str(sum(FrontNo==1))]) 
    end
end

Arc = UpdateArc(Population,[]);