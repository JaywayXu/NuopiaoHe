function Arc = MergeArc(arc1,arc2,arc3)
global FunObj
Arc = [];
if nargin == 2
    for i=1:length(arc1)
        for j=1:length(arc2)
            path.decs = [arc1(i).decs arc2(j).decs(2:end)];
            Arc = [Arc path];
        end
    end
elseif nargin == 3
    for i=1:length(arc1)
        for j=1:length(arc2)
            for k=1:length(arc3)
                path.decs = [arc1(i).decs arc2(j).decs(2:end) arc3(k).decs(2:end)];
                Arc = [Arc path];
            end
        end
    end
end
Arc = FunObj(Arc,1);