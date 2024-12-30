%% �Ż���ʼ
Population = InitPop(PopSize);
gen=1;
while gen<MaxGen
    Offspring  = GA(Population);
    [Population,FrontNo] = EnvironmentalSelection([Population,Offspring],PopSize);
    gen = gen + 1;
    if ~mod(gen,10)
       disp(['��������:' num2str(gen) ' �����:' num2str(sum(FrontNo==1))]) 
    end
end

Arc = UpdateArc(Population,[]);