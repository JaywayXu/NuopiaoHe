function Offspring = GA(Population)

global start_point end_point FunObj
% GA - Genetic operators for real, binary, and permutation based encodings.
Offspring = Population;
N = length(Population);
for i=1:N
    population1 = Population(i).decs(2:end-1);
    pick_ind = randperm(N,N);
    for j=1:N
       if pick_ind(j)==i
           continue;
       end
       population2 = Population(pick_ind(j)).decs(2:end-1);
       common = intersect(population1,population2);
       
       if numel(common) > 0 %½»²æ
           x = common(randi([1,numel(common)],1));
           pos1 = find(population1==x);
           pos2 = find(population2==x);
           tmp2 = population2(pos2:end);
           population1 = [population1(1:pos1-1) tmp2];
           break;
       end
       
    end

    Offspring(i).decs = [start_point population1 end_point];
end

Offspring = FunObj(Offspring);
