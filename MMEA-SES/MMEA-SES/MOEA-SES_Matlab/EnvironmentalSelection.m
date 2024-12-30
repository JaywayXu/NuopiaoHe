function [Population,FrontNo] = EnvironmentalSelection(Population,N)
% The environmental selection of NSGA-II

%--------------------------------------------------------------------------
% Copyright (c) 2016-2017 BIMK Group. You are free to use the PlatEMO for
% research purposes. All publications which use this platform or any code
% in the platform should acknowledge the use of "PlatEMO" and reference "Ye
% Tian, Ran Cheng, Xingyi Zhang, and Yaochu Jin, PlatEMO: A MATLAB Platform
% for Evolutionary Multi-Objective Optimization [Educational Forum], IEEE
% Computational Intelligence Magazine, 2017, 12(4): 73-87".
%--------------------------------------------------------------------------
    
    %% É¾³ýÖØ¸´½â
    Population = RemoveDup(Population);
    
    %% Non-dominated sorting
    n = length(Population);
    [FrontNo,MaxFNo] = NDSort([Population.objs]',n);
    
    [~,Next] = sort(FrontNo,'ascend');
    
    Population = Population(Next);
    
    if length(Population) > N
        Population = Population(1:N);
    end

end