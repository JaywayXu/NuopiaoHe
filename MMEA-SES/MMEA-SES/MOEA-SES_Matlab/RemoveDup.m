function Population = RemoveDup(Population)
n = length(Population);
del = [];
len = zeros(1,n);
for i=1:n
    len(i) = length(Population(i).decs);
end
for i=1:n-1
    if find(i==del)
        continue;
    end
    for j=i+1:n
        if len(i)==len(j)
            if all(Population(i).decs==Population(j).decs)
                del = [del j];
            end
        end
    end
end
Population(del) = [];